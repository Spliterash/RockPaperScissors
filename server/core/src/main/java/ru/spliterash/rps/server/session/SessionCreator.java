package ru.spliterash.rps.server.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.server.session.commandInterceptor.CommandInterceptor;
import ru.spliterash.rps.server.session.port.Transport;
import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.server.stages.AuthStage;
import ru.spliterash.rps.server.stages.StageService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionCreator {
    private final List<CommandInterceptor> interceptors;
    private final StageService stageService;

    public Session createSession(Transport transport) {
        Session session = new Session(
                stageService,
                transport,
                stageService.getByClass(AuthStage.class),
                null
        );

        for (CommandInterceptor interceptor : interceptors) {
            session.addGlobalInterceptor(interceptor);
        }

        return session;
    }
}
