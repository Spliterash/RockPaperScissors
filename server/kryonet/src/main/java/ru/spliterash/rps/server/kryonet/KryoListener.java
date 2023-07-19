package ru.spliterash.rps.server.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.server.session.SessionCreator;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KryoListener extends Listener {
    private final SessionCreator sessionCreator;
    private final Map<Connection, KryonetTransport> connections = new HashMap<>();

    @Override
    public void connected(Connection connection) {
        KryonetTransport kryonetTransport = new KryonetTransport(connection);

        connections.put(connection, kryonetTransport);
        sessionCreator.createSession(kryonetTransport);
    }

    @Override
    public void received(Connection connection, Object o) {
        if (!(o instanceof ServerboundPacket packet))
            return;

        KryonetTransport transport = connections.get(connection);
        transport.receivedPacket(packet);
    }

    @Override
    public void disconnected(Connection connection) {
        KryonetTransport transport = connections.remove(connection);
        transport.onDisconnect();
    }
}
