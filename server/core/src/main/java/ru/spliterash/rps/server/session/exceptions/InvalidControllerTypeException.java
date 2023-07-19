package ru.spliterash.rps.server.session.exceptions;

import ru.spliterash.rps.server.exceptions.RPSException;

public class InvalidControllerTypeException extends RPSException {
    public InvalidControllerTypeException() {
        super("Неизвестный тип контроллера");
    }
}
