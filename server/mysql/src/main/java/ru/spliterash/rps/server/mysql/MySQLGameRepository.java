package ru.spliterash.rps.server.mysql;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.server.game.entity.GameEntity;
import ru.spliterash.rps.server.game.repository.GameRepository;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MySQLGameRepository implements GameRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public @Nullable GameEntity findNotFinishedGame(String userId) {
        String result = jdbcTemplate.queryForStream(
                "SELECT id FROM games where user_id = :id and result is null",
                Map.of("id", Integer.parseInt(userId)),
                (rs, rowNum) -> rs.getString(1)
        )
                .findFirst()
                .orElse(null);
        if (result == null)
            return null;

        return GameEntity.builder()
                .id(result)
                .userId(userId)
                .build();
    }

    @Override
    public GameEntity create(String userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO games (user_id,result) values (:id,null)", new MapSqlParameterSource(Map.of("id", userId)), keyHolder);
        int id = keyHolder.getKey().intValue();

        return GameEntity.builder()
                .id(String.valueOf(id))
                .userId(userId)
                .build();
    }

    @Override
    public void setResult(String id, GameResult result) {
        jdbcTemplate.update("UPDATE games set result = :result where id = :id", Map.of(
                "result", result.name(),
                "id", id
        ));
    }
}
