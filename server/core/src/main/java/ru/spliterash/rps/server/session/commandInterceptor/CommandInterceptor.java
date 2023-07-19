package ru.spliterash.rps.server.session.commandInterceptor;

import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.server.session.Session;

public interface CommandInterceptor {
    default int priority() {
        return 0;
    }

    /**
     * @return Стоит ли продолжать выполнение
     */
    boolean onPacket(Session session, ServerboundPacket packet);
}
