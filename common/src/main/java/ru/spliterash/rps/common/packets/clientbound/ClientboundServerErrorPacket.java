package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundServerErrorPacket(String message) implements ClientboundPacket {
}
