package ru.spliterash.rps.server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {
    private String id;
    private String login;
    private String passwordHash;

    private Instant lastLogin;
    private Instant registerAt;
}