CREATE TABLE questions (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    test_id MEDIUMINT UNSIGNED NOT NULL,
    question VARCHAR(1000),
    image VARCHAR(300),
    point_amount INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES tests(id)
);

INSERT INTO questions(test_id, question, image, point_amount) VALUES
    (1, 'Czy jablko jest zielone?', '', 1),
    (2, 'Czy jablko jest czerwone?', '', 2),
    (2, 'Czy jablko jest rozowe?', '', 3);

