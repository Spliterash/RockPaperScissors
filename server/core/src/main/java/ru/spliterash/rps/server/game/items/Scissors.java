package ru.spliterash.rps.server.game.items;

import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.ItemType;

@Component
public class Scissors implements GameItem {
    @Override
    public boolean beat(GameItem anotherItem) {
        return anotherItem instanceof Paper;
    }

    @Override
    public ItemType type() {
        return ItemType.SCISSORS;
    }
}
