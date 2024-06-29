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
    (1, 'Czy java-pro-tests to najlepszy projekt?', '', 1),
    (1, 'Czy java jest najlepszym językiem świata?', '', 1),
    (1, 'Czy dzisiaj otrzymam ocenę ze zdjęcia?', 'data/questions/question3-312321321312312312.png', 3),
    (1, 'Czy kod na zdjęciu jest poprawny?', 'data/questions/question4-9432949329.png', 1),
    (2, 'Czy jablko jest czerwone?', '', 2),
    (2, 'Czy jablko jest rozowe?', '', 3);

