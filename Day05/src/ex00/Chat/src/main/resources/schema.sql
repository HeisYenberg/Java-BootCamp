CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE chatrooms
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR NOT NULL,
    owner BIGINT REFERENCES users (id)
);

CREATE TABLE messages
(
    id        BIGSERIAL PRIMARY KEY,
    author    BIGINT REFERENCES users (id),
    room      BIGINT REFERENCES chatrooms (id),
    message   TEXT      NOT NULL,
    date_time TIMESTAMP NOT NULL
);