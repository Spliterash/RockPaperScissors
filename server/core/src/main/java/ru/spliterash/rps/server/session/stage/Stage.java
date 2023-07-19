package ru.spliterash.rps.server.session.stage;

import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.common.packets.clientbound.ClientboundStageChangePacket;
import ru.spliterash.rps.server.session.Session;

public interface Stage<T> {
    /**
     * Сессия переключилась на этот контроллер
     */
    default void onEnter(Session session, T data) {
        session.sendPacket(new ClientboundStageChangePacket(type()));
    }

    /**
     * Пользователь покинул контроллер
     */
    default void onLeave(Session session, LeaveReason reason) {
    }

    /**
     * Пришла команда от клиента
     */
    void processPacket(ServerboundPacket packet, Session session);
    StageType type();

}
