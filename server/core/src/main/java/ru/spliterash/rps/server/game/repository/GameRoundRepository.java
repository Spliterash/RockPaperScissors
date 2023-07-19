package ru.spliterash.rps.server.game.repository;

import ru.spliterash.rps.server.game.entity.GameRoundEntity;

import java.util.List;

public interface GameRoundRepository {
    GameRoundEntity create(String gameId);

    void update(GameRoundEntity entity);

    List<GameRoundEntity> findRoundsByGame(String gameId);
}
