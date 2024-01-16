DROP DATABASE IF EXISTS sockets;

CREATE DATABASE sockets;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    identifier BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    password   varchar(255) NOT NULL
);