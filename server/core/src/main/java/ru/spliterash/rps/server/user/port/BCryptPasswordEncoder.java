package ru.spliterash.rps.server.user.port;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    @Override
    public boolean verify(String hash, String str) {
        return BCrypt.checkpw(str, hash);
    }
}
