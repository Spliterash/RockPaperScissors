package ru.spliterash.rps.common.packets.serverbound;

import ru.spliterash.rps.common.enums.ItemType;
import ru.spliterash.rps.common.packets.ServerboundPacket;

public record ServerboundMovePacket(ItemType move) implements ServerboundPacket {
}
