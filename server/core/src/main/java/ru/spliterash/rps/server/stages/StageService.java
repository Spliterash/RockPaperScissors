package ru.spliterash.rps.server.stages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.enums.StageType;
import ru.spliterash.rps.server.session.stage.Stage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StageService {
    private final List<Stage<?>> stages;

    public <T extends Stage<?>> T getByClass(Class<T> clazz) {
        //noinspection unchecked
        return (T) stages
                .stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Не удалось найти этап с классом " + clazz.getSimpleName()));
    }

    public Stage<?> getByType(StageType type) {
        return stages
                .stream()
                .filter(s -> s.type() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Не удалось найти этап с именем " + type));
    }
}
