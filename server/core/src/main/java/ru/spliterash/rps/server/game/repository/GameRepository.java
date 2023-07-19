package ru.spliterash.rps.server.game.repository;

import org.jetbrains.annotations.Nullable;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.server.game.entity.GameEntity;

public interface GameRepository {
    @Nullable
    GameEntity findNotFinishedGame(String userId);

    GameEntity create(String userId);

    void setResult(String id, GameResult result);
}
