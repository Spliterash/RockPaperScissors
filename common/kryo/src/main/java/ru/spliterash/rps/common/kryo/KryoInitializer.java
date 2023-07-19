package ru.spliterash.rps.common.kryo;


import com.esotericsoftware.kryo.Kryo;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.common.enums.ItemType;
import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.common.packets.clientbound.*;
import ru.spliterash.rps.common.packets.serverbound.*;

public class KryoInitializer {
    public static void configureKryo(Kryo kryo) {
        // Clientbound
        kryo.register(ClientboundGameEndPacket.class);
        kryo.register(ClientboundMoveWarningPacket.class);
        kryo.register(ClientboundRoundEndPacket.class);
        kryo.register(ClientboundRoundStartPacket.class);
        kryo.register(ClientboundServerErrorPacket.class);
        kryo.register(ClientboundSignInSuccessPacket.class);
        kryo.register(ClientboundSignUpSuccessPacket.class);
        kryo.register(ClientboundStageChangePacket.class);
        kryo.register(ClientboundUserSkipMovePacket.class);
        // Serverbound
        kryo.register(ServerboundLogOutPacket.class);
        kryo.register(ServerboundMovePacket.class);
        kryo.register(ServerboundSignInPacket.class);
        kryo.register(ServerboundSignUpPacket.class);
        kryo.register(ServerboundStartPacket.class);
        kryo.register(ServerboundAskTimerPacket.class);
        // Enums
        kryo.register(GameResult.class);
        kryo.register(ItemType.class);
        kryo.register(StageType.class);
    }
}
