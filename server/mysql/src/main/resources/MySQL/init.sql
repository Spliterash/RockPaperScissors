CREATE TABLE IF NOT EXISTS users
(
    id           INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login        VARCHAR(16) NOT NULL,
    passwordHash VARCHAR(64) NOT NULL,
    last_login   TIMESTAMP   NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    register_at  TIMESTAMP   NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    CONSTRAINT unq_users UNIQUE (login)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS games
(
    id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    result  VARCHAR(10),
    CONSTRAINT fk_games_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS rounds
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    game_id     INT NOT NULL,
    timer       INT NOT NULL DEFAULT 0,
    bot_move    VARCHAR(12),
    player_move VARCHAR(12),
    result      VARCHAR(10),
    CONSTRAINT fk_rounds_games FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) engine = InnoDB;
