package ru.spliterash.rps.server.game.entity;

import lombok.*;
import org.jetbrains.annotations.Nullable;
import ru.spliterash.rps.common.enums.GameResult;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {
    private String id;
    private String userId;
    /**
     * Если RESULT null, то значит что игра ещё не законченна
     */
    @Nullable
    private GameResult result;
}
