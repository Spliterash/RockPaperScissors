package ru.spliterash.rps.server.kryonet;

import com.esotericsoftware.kryonet.Connection;
import lombok.RequiredArgsConstructor;
import ru.spliterash.rps.common.packets.ClientboundPacket;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.server.session.port.Transport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class KryonetTransport implements Transport {
    private final Connection connection;
    private final List<Consumer<ServerboundPacket>> commandCallbacks = new ArrayList<>(2);
    private final List<Runnable> disconnectCallbacks = new ArrayList<>(2);

    public void receivedPacket(ServerboundPacket object) {
        for (Consumer<ServerboundPacket> commandCallback : commandCallbacks) {
            commandCallback.accept(object);
        }
    }

    public void onDisconnect() {
        for (Runnable disconnectCallback : disconnectCallbacks) {
            disconnectCallback.run();
        }
    }

    @Override
    public void sendPacket(ClientboundPacket command) {
        connection.sendTCP(command);
    }

    @Override
    public void addCommandCallback(Consumer<ServerboundPacket> onCommand) {
        commandCallbacks.add(onCommand);
    }

    @Override
    public void addDisconnectCallback(Runnable runnable) {
        disconnectCallbacks.add(runnable);
    }

    @Override
    public void disconnect() {
        connection.close();
    }

    @Override
    public boolean isAlive() {
        return connection.isConnected();
    }
}
