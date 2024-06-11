CREATE TABLE users (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    indeks INTEGER UNSIGNED NOT NULL,
    username VARCHAR(30),
    password VARCHAR(1024),
    name VARCHAR(50),
    surname VARCHAR(50),
    enabled BOOLEAN DEFAULT 1,
    role VARCHAR(50),
    PRIMARY KEY (id)
);

INSERT INTO users (indeks, username, password, role, name, surname) VALUES
   (1, 'admin', 'password', 'ADMIN', 'Tomasz', 'Słapiński'),
   (169618, 'tom', '123', 'USER', 'Tom', 'S');

