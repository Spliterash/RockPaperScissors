package ru.spliterash.rps.server.game.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.server.game.entity.GameEntity;
import ru.spliterash.rps.server.game.entity.GameRoundEntity;
import ru.spliterash.rps.server.game.game.Game;
import ru.spliterash.rps.server.game.repository.GameRepository;
import ru.spliterash.rps.server.game.repository.GameRoundRepository;
import ru.spliterash.rps.server.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class GameService {
    private final ScheduledExecutorService executorService;


    private final ItemService itemService;
    private final GameRepository gameRepository;
    private final GameRoundRepository gameRoundRepository;

    @Nullable
    public GameEntity findUnfinishedGame(User user) {
        return gameRepository.findNotFinishedGame(user.getId());
    }

    public Game createGame(User user, GameEntity gameEntity) {
        List<GameRoundEntity> rounds = gameRoundRepository.findRoundsByGame(gameEntity.getId());

        return createGame(user, gameEntity, rounds);
    }

    public Game createNewGame(User user) {
        GameEntity gameEntity = gameRepository.create(user.getId());

        return createGame(user, gameEntity, new ArrayList<>(3));
    }

    private Game createGame(User user, GameEntity entity, List<GameRoundEntity> rounds) {
        return new Game(itemService.all(), executorService, user, entity, rounds, gameRepository, gameRoundRepository);
    }

}
