DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product
(
    identifier BIGINT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    price      DECIMAL      NOT NULL
);