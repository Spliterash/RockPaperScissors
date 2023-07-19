package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundStageChangePacket(StageType stage) implements ClientboundPacket {
}
