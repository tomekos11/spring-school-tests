CREATE TABLE groups (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(30) NOT NULL,
    year INT UNSIGNED NOT NULL,
    season VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO groups (name, type, year, season) VALUES
    ('3EF-DI-AA', 'lab', 2024, 'summer'),
    ('2EF-DI-AA', 'lab', 2024, 'summer');
