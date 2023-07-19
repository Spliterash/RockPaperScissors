package ru.spliterash.rps.server.session.port;

import ru.spliterash.rps.common.packets.ClientboundPacket;
import ru.spliterash.rps.common.packets.ServerboundPacket;

import java.util.function.Consumer;

public interface Transport {
    void sendPacket(ClientboundPacket command);

    void addCommandCallback(Consumer<ServerboundPacket> onCommand);
    void addDisconnectCallback(Runnable runnable);

    void disconnect();

    boolean isAlive();
}
