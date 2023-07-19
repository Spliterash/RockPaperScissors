package ru.spliterash.rps.server.game.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.spliterash.rps.common.enums.GameResult;
import ru.spliterash.rps.server.game.entity.GameEntity;
import ru.spliterash.rps.server.game.entity.GameRoundEntity;
import ru.spliterash.rps.server.game.items.GameItem;
import ru.spliterash.rps.server.game.repository.GameRepository;
import ru.spliterash.rps.server.game.repository.GameRoundRepository;
import ru.spliterash.rps.server.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class Game {
    /**
     * Сколько длится раунд в секундах
     */
    private static final int ROUND_SECOND_LENGTH = 30;
    /**
     * Сколько всего раундов
     */
    private static final int ROUNDS_SIZE = 3;

    private final Random random = new Random();
    private final List<GameItem> botItems;
    private final ScheduledExecutorService executor;
    @Getter
    private final User user;
    private final GameEntity game;
    private final List<GameRoundEntity> rounds;
    private final GameRepository gameRepository;
    private final GameRoundRepository gameRoundRepository;
    private boolean started = false;
    private long roundShouldEndAt = 0;
    private final List<GameEventListener> listeners = new ArrayList<>();

    private final List<ScheduledFuture<?>> timers = new ArrayList<>();

    public void start() {
        if (started)
            return;
        started = true;
        if (rounds.isEmpty()) {
            startNewRound();
            return;
        }
        GameRoundEntity round = currentRound();

        if (round.getResult() != null) {
            if (rounds.size() < ROUNDS_SIZE)
                startNewRound();
            else
                sendResults();
        } else if (round.getTimer() >= ROUND_SECOND_LENGTH)
            sendResults();
        else
            startRound(round.getTimer());
    }

    public void addGameListener(GameEventListener listener) {
        listeners.add(listener);
    }

    private void clearTimers() {
        for (ScheduledFuture<?> future : timers) {
            future.cancel(false);
        }
        timers.clear();
    }

    public void sendWarning() {
        sendWarning(availableTime());
    }

    private void sendWarning(int left) {
        for (GameEventListener listener : listeners) {
            listener.moveWarning(left);
        }
    }

    private void createTimer(Runnable runnable, int delaySeconds) {
        ScheduledFuture<?> task = executor.schedule(runnable, delaySeconds, TimeUnit.SECONDS);
        timers.add(task);
    }

    private void createWarningTimer(int secondsBeforeEnd, int timeLeft) {
        int delay = timeLeft - secondsBeforeEnd;
        if (delay <= 0)
            return;
        createTimer(() -> sendWarning(secondsBeforeEnd), delay);
    }

    public GameItem pickItem() {
        int index = random.nextInt(botItems.size());

        return botItems.get(index);
    }

    private void startRound(int timer) {
        int timeLeft = ROUND_SECOND_LENGTH - timer;

        clearTimers();
        for (GameEventListener listener : listeners) {
            listener.roundStart(rounds.size());
        }
        sendWarning(timeLeft);
        createWarningTimers(timeLeft);

        createTimer(this::skip, timeLeft);
        roundShouldEndAt = System.currentTimeMillis() + (timeLeft * 1000L);
    }

    /**
     * Сколько времени осталось до конца хода игрока в секундах
     */
    public int availableTime() {
        long current = System.currentTimeMillis();
        return (int) ((roundShouldEndAt - current) / 1000L);
    }

    /**
     * Сколько времени прошло с начала игры
     */
    public int currentTimer() {
        return ROUND_SECOND_LENGTH - availableTime();
    }

    private void startNewRound() {
        GameRoundEntity round = gameRoundRepository.create(game.getId());
        rounds.add(round);
        startRound(0);
    }

    private void createWarningTimers(int timeLeft) {
        createWarningTimer(15, timeLeft);
        createWarningTimer(5, timeLeft);
        createWarningTimer(3, timeLeft);
        createWarningTimer(1, timeLeft);
    }

    private GameRoundEntity currentRound() {
        return rounds.get(rounds.size() - 1);
    }

    private void skip() {
        clearTimers();
        GameRoundEntity entity = currentRound();

        entity.setResult(GameResult.LOSE);

        for (GameEventListener listener : listeners) {
            listener.userSkipMove();
        }

        saveRoundAndGo(entity);
    }

    public void move(GameItem playerItem) {
        clearTimers();
        GameItem botItem = pickItem();

        GameRoundEntity entity = currentRound();

        entity.setTimer(currentTimer());
        entity.setPlayerMove(playerItem.type());
        entity.setBotMove(botItem.type());

        if (botItem.beat(playerItem))
            entity.setResult(GameResult.LOSE);
        else if (playerItem.beat(botItem))
            entity.setResult(GameResult.WIN);
        else
            entity.setResult(GameResult.DRAW);

        for (GameEventListener listener : listeners) {
            listener.roundEnd(playerItem, botItem, entity.getResult());
        }

        saveRoundAndGo(entity);
    }

    private void saveRoundAndGo(GameRoundEntity entity) {
        gameRoundRepository.update(entity);
        if (rounds.size() >= ROUNDS_SIZE)
            sendResults();
        else
            startNewRound();
    }

    private void sendResults() {
        int score = rounds
                .stream()
                .mapToInt(r -> {
                    if (r.getResult() == GameResult.WIN)
                        return 1;
                    else if (r.getResult() == GameResult.LOSE)
                        return -1;
                    else
                        return 0;
                })
                .sum();

        if (score > 0)
            game.setResult(GameResult.WIN);
        else if (score < 0)
            game.setResult(GameResult.LOSE);
        else
            game.setResult(GameResult.DRAW);

        gameRepository.setResult(game.getId(), game.getResult());

        for (GameEventListener listener : listeners) {
            listener.gameEnd(game, rounds, game.getResult());
        }
    }

    public void leave() {
        clearTimers();
        GameRoundEntity round = currentRound();
        round.setTimer(currentTimer());
        gameRoundRepository.update(round);
    }
}
