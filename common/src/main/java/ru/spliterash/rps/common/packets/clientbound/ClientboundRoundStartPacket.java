package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundRoundStartPacket(int roundNumber) implements ClientboundPacket {
}
