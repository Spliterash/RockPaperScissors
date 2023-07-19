package ru.spliterash.rps.server.mysql;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.common.enums.ItemType;
import ru.spliterash.rps.server.game.entity.GameRoundEntity;
import ru.spliterash.rps.server.game.repository.GameRoundRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MySQLGameRoundRepository implements GameRoundRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public GameRoundEntity create(String gameId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO rounds (game_id)\n" +
                        "VALUES (:game_id)",
                new MapSqlParameterSource()
                        .addValue("game_id", gameId),
                holder
        );


        String id = Objects.toString(holder.getKey().intValue());

        return GameRoundEntity.builder()
                .id(id)
                .gameId(gameId)
                .timer(0)
                .build();
    }

    @Override
    public void update(GameRoundEntity entity) {
        ItemType botMove = entity.getBotMove();
        ItemType playerMove = entity.getPlayerMove();
        GameResult result = entity.getResult();
        jdbcTemplate.update(
                "UPDATE rounds SET bot_move = :bot_move, player_move = :player_move, result = :result, timer = :timer where id = :id",
                new MapSqlParameterSource()
                        .addValue("id", entity.getId())
                        .addValue("bot_move", botMove != null ? botMove.code() : null)
                        .addValue("player_move", playerMove != null ? playerMove.code() : null)
                        .addValue("result", result != null ? result.name() : null)
                        .addValue("timer", entity.getTimer())
        );
    }

    @Override
    public List<GameRoundEntity> findRoundsByGame(String gameId) {
        return jdbcTemplate.query("SELECT * FROM rounds where game_id = :id", Map.of("id", gameId), (row, num) -> GameRoundEntity.builder()
                .id(row.getString("id"))
                .gameId(gameId)
                .timer(row.getInt("timer"))
                .botMove(ItemType.ofCode(row.getString("bot_move")))
                .playerMove(ItemType.ofCode(row.getString("player_move")))
                .result(GameResult.valueOfNullable(row.getString("result")))
                .build());
    }
}
