package ru.spliterash.rps.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.server.user.entity.User;
import ru.spliterash.rps.server.user.exceptions.BlankPasswordException;
import ru.spliterash.rps.server.user.exceptions.InvalidLoginException;
import ru.spliterash.rps.server.user.exceptions.UserAlreadyExistException;
import ru.spliterash.rps.server.user.port.PasswordEncoder;
import ru.spliterash.rps.server.user.repository.UserRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User signIn(String login, String password) {
        User user = repository.findUserByLogin(login);
        if (user == null)
            throw new InvalidLoginException();

        boolean validPassword = passwordEncoder.verify(user.getPasswordHash(), password);

        if (!validPassword)
            throw new InvalidLoginException();
        repository.refreshLastLogin(user.getId());
        user.setLastLogin(Instant.now());

        return user;
    }

    public User signUp(String login, String password) {
        if (password.isBlank())
            throw new BlankPasswordException();

        boolean exist = repository.existByLogin(login);
        if (exist)
            throw new UserAlreadyExistException();

        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(passwordEncoder.encode(password));

        user = repository.createUser(user);

        return user;
    }
}
