DROP DATABASE IF EXISTS sockets;

CREATE DATABASE sockets;

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    identifier BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    password   varchar(255) NOT NULL
);

DROP TABLE IF EXISTS chatrooms CASCADE;

CREATE TABLE chatrooms
(
    identifier BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    owner      BIGINT REFERENCES users (identifier)
);

DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE messages
(
    identifier BIGSERIAL PRIMARY KEY,
    sender     BIGINT REFERENCES users (identifier),
    room       BIGINT REFERENCES chatrooms (identifier),
    text       TEXT      NOT NULL,
    date_time  TIMESTAMP NOT NULL
);