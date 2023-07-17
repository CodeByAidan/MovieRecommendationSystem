SELECT 'Running init.sql';

CREATE DATABASE IF NOT EXISTS movie_recommendation CHARACTER SET utf8mb4 COLLATE
    utf8mb4_unicode_ci;

USE movie_recommendation;

CREATE TABLE IF NOT EXISTS user
(
    id       INT auto_increment,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movies
(
    id       INT auto_increment PRIMARY KEY,
    title    VARCHAR(200) NOT NULL,
    genre    VARCHAR(100),
    director VARCHAR(100),
    rating   FLOAT DEFAULT 0
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ratings
(
    id       INT auto_increment PRIMARY KEY,
    movie_id INT,
    user_id  INT,
    rating   INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci; 