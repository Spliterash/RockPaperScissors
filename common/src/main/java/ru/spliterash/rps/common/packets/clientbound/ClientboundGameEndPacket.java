package ru.spliterash.rps.common.packets.clientbound;

import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.common.packets.ClientboundPacket;

public record ClientboundGameEndPacket(GameResult result, int humanWins, int botWins) implements ClientboundPacket {
}
