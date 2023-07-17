USE movie_recommendation;

CREATE TABLE User(
    id INT AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    PRIMARY KEY(id)
);

