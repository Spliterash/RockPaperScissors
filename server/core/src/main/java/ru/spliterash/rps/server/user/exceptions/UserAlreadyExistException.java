package ru.spliterash.rps.server.user.exceptions;

import ru.spliterash.rps.server.exceptions.RPSException;

public class UserAlreadyExistException extends RPSException {
    public UserAlreadyExistException() {
        super("Пользователь уже существует");
    }
}
