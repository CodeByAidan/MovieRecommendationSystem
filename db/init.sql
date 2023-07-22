SELECT 'Running init.sql';

CREATE DATABASE IF NOT EXISTS movie_recommendation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE movie_recommendation;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT auto_increment,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movies
(
    id       BIGINT auto_increment PRIMARY KEY,
    title    VARCHAR(200) NOT NULL
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS genres
(
    id     BIGINT auto_increment PRIMARY KEY,
    name   VARCHAR(255)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movies_genres
(
    movie_id  BIGINT,
    genre_id  BIGINT,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (genre_id) REFERENCES genres(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS genome_tags
(
    id     BIGINT auto_increment PRIMARY KEY,
    tag    VARCHAR(255)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS genome_scores
(
    id          BIGINT auto_increment PRIMARY KEY,
    movie_id    BIGINT,
    genome_tag_id BIGINT,
    score       DOUBLE,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (genome_tag_id) REFERENCES genome_tags(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movie_records
(
    id          BIGINT auto_increment PRIMARY KEY,
    timestamp   DATETIME,
    user_id     BIGINT,
    movie_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ratings
(
    id          BIGINT auto_increment PRIMARY KEY,
    score       INT,
    user_id     BIGINT,
    movie_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS reviews
(
    id          BIGINT auto_increment PRIMARY KEY,
    text        TEXT,
    user_id     BIGINT,
    movie_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tags
(
    id          BIGINT auto_increment PRIMARY KEY,
    name        VARCHAR(255),
    user_id     BIGINT,
    movie_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS users_favorite_movies
(
    user_id     BIGINT,
    movie_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
