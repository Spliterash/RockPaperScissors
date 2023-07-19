package ru.spliterash.rps.server.stages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.common.packets.ServerboundPacket;
import ru.spliterash.rps.common.packets.clientbound.ClientboundSignInSuccessPacket;
import ru.spliterash.rps.common.packets.clientbound.ClientboundSignUpSuccessPacket;
import ru.spliterash.rps.common.packets.serverbound.ServerboundSignInPacket;
import ru.spliterash.rps.common.packets.serverbound.ServerboundSignUpPacket;
import ru.spliterash.rps.server.exceptions.WrongPacketException;
import ru.spliterash.rps.server.game.entity.GameEntity;
import ru.spliterash.rps.server.game.service.GameService;
import ru.spliterash.rps.server.session.Session;
import ru.spliterash.rps.server.session.stage.NoDataStage;
import ru.spliterash.rps.server.user.entity.User;
import ru.spliterash.rps.server.user.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthStage extends NoDataStage {
    private final UserService userService;
    private final GameService gameService;


    @Override
    public void processPacket(ServerboundPacket packet, Session session) {
        if (packet instanceof ServerboundSignUpPacket signUpPacket) {
            session.setDestroyMarker();
            userService.signUp(signUpPacket.login(), signUpPacket.password());
            session.sendPacket(new ClientboundSignUpSuccessPacket());
        } else if (packet instanceof ServerboundSignInPacket signInPacket) {
            User user = userService.signIn(signInPacket.login(), signInPacket.password());
            session.setUser(user);
            session.sendPacket(new ClientboundSignInSuccessPacket());
            GameEntity gameEntity = gameService.findUnfinishedGame(user);
            if (gameEntity != null)
                session.setStage(GameStage.class, gameEntity);
            else
                session.setStage(MenuStage.class);
        } else {
            throw new WrongPacketException();
        }
    }

    @Override
    public StageType type() {
        return StageType.AUTH;
    }
}
