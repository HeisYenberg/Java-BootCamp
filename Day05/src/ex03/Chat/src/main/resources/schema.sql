DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

DROP TABLE IF EXISTS chatrooms CASCADE;

CREATE TABLE chatrooms
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR NOT NULL,
    owner BIGINT REFERENCES users (id)
);

DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE messages
(
    id        BIGSERIAL PRIMARY KEY,
    author    BIGINT REFERENCES users (id),
    room      BIGINT REFERENCES chatrooms (id),
    message   TEXT      NOT NULL,
    date_time TIMESTAMP NOT NULL
);