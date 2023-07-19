package ru.spliterash.rps.common.packets.serverbound;

import ru.spliterash.rps.common.packets.ServerboundPacket;

public record ServerboundSignInPacket(String login, String password) implements ServerboundPacket {
}
