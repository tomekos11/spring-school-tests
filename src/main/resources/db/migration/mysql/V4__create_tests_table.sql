CREATE TABLE tests (
       id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
       name VARCHAR(50),
       alias VARCHAR(100),
       PRIMARY KEY (id)
);

INSERT INTO tests (name, alias) VALUES
    ('kolokwium 1', 'kolokwium 1 - informatyka 3 rok');
