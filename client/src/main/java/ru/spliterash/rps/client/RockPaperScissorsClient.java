package ru.spliterash.rps.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ru.spliterash.rps.common.enums.ItemType;
import ru.spliterash.rps.common.kryo.KryoInitializer;
import ru.spliterash.rps.common.packets.ClientboundPacket;
import ru.spliterash.rps.common.packets.clientbound.*;
import ru.spliterash.rps.common.packets.serverbound.*;

import java.io.IOException;
import java.util.Scanner;

public class RockPaperScissorsClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        KryoInitializer.configureKryo(client.getKryo());
        client.addListener(new ClientListener());
        client.start();

        client.connect(5000, "localhost", 1047);
        Scanner scanner = new Scanner(System.in);

        while (client.isConnected()) {
            try {
                String[] line = scanner.nextLine().split("=");
                if (line.length < 1)
                    throw new IllegalStateException("Команда не может быть пустой");
                String command = line[0].toLowerCase();
                switch (command) {
                    case "signup" -> {
                        commandCheck(line, 3, "signup=<login>=<password>");
                        String login = line[1];
                        String password = line[2];
                        client.sendTCP(new ServerboundSignUpPacket(login, password));
                    }
                    case "signin" -> {
                        commandCheck(line, 3, "signin=<login>=<password>");
                        String login = line[1];
                        String password = line[2];
                        client.sendTCP(new ServerboundSignInPacket(login, password));
                    }
                    case "logout" -> {
                        commandCheck(line, 1, "logout");
                        client.sendTCP(new ServerboundLogOutPacket());
                    }
                    case "start" -> {
                        commandCheck(line, 1, "start");
                        client.sendTCP(new ServerboundStartPacket());
                    }
                    case "rock" -> {
                        commandCheck(line, 1, "rock");
                        client.sendTCP(new ServerboundMovePacket(ItemType.ROCK));
                    }
                    case "paper" -> {
                        commandCheck(line, 1, "paper");
                        client.sendTCP(new ServerboundMovePacket(ItemType.PAPER));
                    }
                    case "scissors" -> {
                        commandCheck(line, 1, "scissors");
                        client.sendTCP(new ServerboundMovePacket(ItemType.SCISSORS));
                    }
                    case "timer" -> {
                        commandCheck(line, 1, "timer");
                        client.sendTCP(new ServerboundAskTimerPacket());
                    }

                    default -> throw new IllegalStateException("Команда " + command + " не найдена");
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void commandCheck(Object[] arr, int length, String usage) {
        if (arr.length != length)
            throw new IllegalStateException("Использование: " + usage);
    }

    private static class ClientListener extends Listener {
        @Override
        public void received(Connection connection, Object o) {
            if (!(o instanceof ClientboundPacket)) return;

            if (o instanceof ClientboundGameEndPacket packet) {
                System.out.println("Игра завершенна, результат: " + packet.result().name() + ", со счётом " + packet.humanWins() + ":" + packet.botWins());
            } else if (o instanceof ClientboundMoveWarningPacket packet) {
                System.out.println("До конца хода осталось " + packet.leftSeconds() + " с.");
            } else if (o instanceof ClientboundRoundEndPacket packet) {
                System.out.println("Раунд завершён. Ход игрока - " + packet.userItem().name() + ", ход бота - " + packet.botItem().name() + ". Результат: " + packet.result().name());
            } else if (o instanceof ClientboundRoundStartPacket packet) {
                System.out.println("Раунд " + packet.roundNumber() + " начался");
            } else if (o instanceof ClientboundServerErrorPacket packet) {
                System.out.println("Ошибка: " + packet.message());
            } else if (o instanceof ClientboundSignInSuccessPacket) {
                System.out.println("Успешная авторизация!");
            } else if (o instanceof ClientboundSignUpSuccessPacket) {
                System.out.println("Успешная регистрация! Перезайдите на сервер");
            } else if (o instanceof ClientboundStageChangePacket packet) {
                switch (packet.stage()) {
                    case AUTH -> System.out.println("""
                                Пожалуйста войдите в крестики нолики. Доступные команды
                                Авторизация: signin=<login>=<password>
                                Регистрация: signup=<login>=<password>
                            """);
                    case MENU -> System.out.println("Добро пожаловать в меню. Напишите start чтобы начать игру");
                    case GAME -> System.out.println("Вы вошли в игру");
                }
            } else if (o instanceof ClientboundUserSkipMovePacket) {
                System.out.println("Ты пропустил ход, и проиграл раунд");
            }
        }

        @Override
        public void disconnected(Connection connection) {
            System.out.println("Вы отключены от сервера");
            System.exit(0);
        }
    }
}
