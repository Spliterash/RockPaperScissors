package ru.spliterash.rps.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication(scanBasePackages = "ru.spliterash.rps.server")
public class RockPaperScissorsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RockPaperScissorsServerApplication.class, args);
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(4);
    }
}
