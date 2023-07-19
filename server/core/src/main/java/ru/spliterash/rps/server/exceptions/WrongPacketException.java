package ru.spliterash.rps.server.exceptions;

public class WrongPacketException extends RPSException {
    public WrongPacketException() {
        super("Отправлен неправильный пакет");
    }
}
