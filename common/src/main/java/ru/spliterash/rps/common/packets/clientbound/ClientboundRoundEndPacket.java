package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.common.enums.ItemType;
import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundRoundEndPacket(ItemType userItem, ItemType botItem, GameResult result) implements ClientboundPacket {
}
