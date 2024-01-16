DROP DATABASE IF EXISTS spring_service;

CREATE DATABASE spring_service;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    identifier BIGSERIAL PRIMARY KEY,
    email VARCHAR NOT NULL
);