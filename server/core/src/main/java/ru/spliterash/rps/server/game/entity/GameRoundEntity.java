package ru.spliterash.rps.server.game.entity;

import lombok.*;
import org.jetbrains.annotations.Nullable;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.common.enums.ItemType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRoundEntity {
    private String id;
    private String gameId;
    private int timer;
    @Nullable
    private ItemType botMove;
    @Nullable
    private ItemType playerMove;
    private GameResult result;
}
