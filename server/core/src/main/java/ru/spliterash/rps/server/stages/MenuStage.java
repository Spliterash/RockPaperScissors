package ru.spliterash.rps.server.stages;

import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.common.packets.serverbound.ServerboundStartPacket;
import ru.spliterash.rps.server.exceptions.WrongPacketException;
import ru.spliterash.rps.server.session.Session;
import ru.spliterash.rps.server.session.stage.NoDataStage;

@Component
public class MenuStage extends NoDataStage {
    @Override
    public void processPacket(ServerboundPacket packet, Session session) {
        if (!(packet instanceof ServerboundStartPacket)) throw new WrongPacketException();

        session.setStage(StageType.GAME, null);
    }

    @Override
    public StageType type() {
        return StageType.MENU;
    }
}
