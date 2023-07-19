package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundMoveWarningPacket(int leftSeconds) implements ClientboundPacket {
}
