package ru.spliterash.rps.server.game.exceptions;

import ru.spliterash.rps.server.exceptions.RPSException;

public class ItemNotFoundException extends RPSException {
    public ItemNotFoundException() {
        super("Предмет не найден");
    }
}
