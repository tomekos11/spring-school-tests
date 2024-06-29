CREATE TABLE answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    question_id MEDIUMINT UNSIGNED NOT NULL,
    answer VARCHAR(500),
    is_correct BOOLEAN DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

INSERT INTO answers (question_id, answer, is_correct) VALUES
    (1, "TAK", 1),
    (1, "NIE", 0),
    (2, "NIE WIEM", 0),
    (2, "TAK", 1),
    (2, "NIE", 0),
    (3, "TAK", 1),
    (3, "TAK", 1),
    (3, "JASNE, ŻE TAK", 1),
    (4, "Jest błąd w lini 3", 0),
    (4, "Jest błąd w lini 5", 0),
    (4, "Tak", 1),
    (5, "XD", 0),
    (5, "XP", 0),
    (5, "XC", 1),
    (6, "lol", 0),
    (6, "urugwaj", 1);


