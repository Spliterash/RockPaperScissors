package ru.spliterash.rps.server.user.exceptions;

import ru.spliterash.rps.server.exceptions.RPSException;

public class InvalidLoginException extends RPSException {
    public InvalidLoginException(){
        super("Некорректный логин или пароль");
    }
}
