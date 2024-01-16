DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product
(
    identifier BIGINT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    price      DECIMAL      NOT NULL
);

DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user"
(
    identifier     BIGINT PRIMARY KEY,
    login          VARCHAR(255) NOT NULL,
    password       VARCHAR(255) NOT NULL,
    authentication BOOLEAN      NOT NULL
);