package ru.spliterash.rps.common.enums;

import org.jetbrains.annotations.Nullable;

public enum GameResult {
    WIN, LOSE, DRAW;

    public static @Nullable GameResult valueOfNullable(@Nullable String name) {
        if (name == null)
            return null;
        return valueOf(name);
    }
}
