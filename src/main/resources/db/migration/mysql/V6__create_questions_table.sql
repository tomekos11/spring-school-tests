CREATE TABLE questions (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    test_id MEDIUMINT UNSIGNED NOT NULL,
    content VARCHAR(1000),
    image VARCHAR(300),
    point_amount INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES tests(id)
);

INSERT INTO questions(test_id, content, image, point_amount) VALUES
    (1, 'Czy jablko jest zielone?', '', 1),
    (1, 'Czy twoj ojciec jest pijany?', '', 1),
    (1, 'Czy woda ma smak?', '', 1),
    (2, 'Czy jablko jest czerwone?', '', 2),
    (2, 'Czy jablko jest rozowe?', '', 3);

