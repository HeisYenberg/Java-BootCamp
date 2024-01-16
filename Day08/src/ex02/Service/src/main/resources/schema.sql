DROP DATABASE IF EXISTS spring_service;

CREATE DATABASE spring_service;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    identifier BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    password   varchar(255) NOT NULL
);