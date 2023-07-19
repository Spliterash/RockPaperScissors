package ru.spliterash.rps.server.session.commandInterceptor;

import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.common.packets.serverbound.ServerboundLogOutPacket;
import ru.spliterash.rps.server.session.Session;
import ru.spliterash.rps.server.stages.AuthStage;

@Component
public class LogoutInterceptor implements CommandInterceptor {
    @Override
    public boolean onPacket(Session session, ServerboundPacket packet) {
        if (!(packet instanceof ServerboundLogOutPacket) || session.getStage() instanceof AuthStage)
            return true;

        session.setUser(null);
        session.setStage(AuthStage.class);
        return false;
    }
}
