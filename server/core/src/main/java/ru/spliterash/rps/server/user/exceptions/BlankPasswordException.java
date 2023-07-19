package ru.spliterash.rps.server.user.exceptions;

import ru.spliterash.rps.server.exceptions.RPSException;

public class BlankPasswordException extends RPSException {
    public BlankPasswordException() {
        super("Пароль не может быть пустым");
    }
}
