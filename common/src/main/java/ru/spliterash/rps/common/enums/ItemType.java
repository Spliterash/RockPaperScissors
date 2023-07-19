package ru.spliterash.rps.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum ItemType {
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors");
    private static final ItemType[] values = values();


    private final String code;

    @Nullable
    public static ItemType ofCode(@Nullable String code) {
        if (code == null)
            return null;

        for (ItemType value : values) {
            if (Objects.equals(value.code, code))
                return value;
        }

        return null;
    }
}
