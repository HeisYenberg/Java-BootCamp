DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    identifier BIGINT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    password   varchar(255) NOT NULL
);