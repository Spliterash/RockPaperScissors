package ru.spliterash.rps.server.game.items;

import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.ItemType;

@Component
public class Rock implements GameItem {

    @Override
    public boolean beat(GameItem anotherItem) {
        return anotherItem instanceof Scissors;
    }

    @Override
    public ItemType type() {
        return ItemType.ROCK;
    }
}
