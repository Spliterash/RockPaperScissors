package ru.spliterash.rps.server.game.items;

import ru.spliterash.rps.common.enums.ItemType;

public interface GameItem {
    boolean beat(GameItem anotherItem);

    ItemType type();
}
