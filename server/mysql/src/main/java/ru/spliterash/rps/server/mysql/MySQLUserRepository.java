package ru.spliterash.rps.server.mysql;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.server.user.entity.User;
import ru.spliterash.rps.server.user.repository.UserRepository;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class MySQLUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    @Override
    public boolean existByLogin(String login) {
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users where login = :login", Map.of("login", login), Integer.class) > 0;
    }

    @Override
    public @Nullable User findUserById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users where id = :id", Map.of("id", id), rowMapper);
    }

    @Override
    public @Nullable User findUserByLogin(String login) {
        return jdbcTemplate.queryForStream("SELECT * FROM users where login = :login", Map.of("login", login), rowMapper)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                "INSERT INTO users (login, passwordHash)\n" +
                        "values (:login, :passwordHash)",
                new MapSqlParameterSource()
                        .addValue("login", user.getLogin())
                        .addValue("passwordHash", user.getPasswordHash()),
                holder
        );

        user.setId(String.valueOf(holder.getKey().intValue()));

        return user;
    }

    @Override
    public void refreshLastLogin(String id) {
        jdbcTemplate.update("UPDATE users set last_login = CURRENT_TIMESTAMP where id = :id", Map.of("id", id));
    }
}
