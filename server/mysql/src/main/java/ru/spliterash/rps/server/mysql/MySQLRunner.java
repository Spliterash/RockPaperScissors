package ru.spliterash.rps.server.mysql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MySQLRunner implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private final Resource resourceFile;

    public MySQLRunner(JdbcTemplate jdbcTemplate, @Value("classpath:MySQL/init.sql") Resource resourceFile) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceFile = resourceFile;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String initScript = resourceFile.getContentAsString(StandardCharsets.UTF_8);
        String[] lines = initScript.split(";");

        for (String line : lines) {
            line = line.trim();
            if (line.isBlank())
                continue;

            jdbcTemplate.update(line);
        }
    }
}
