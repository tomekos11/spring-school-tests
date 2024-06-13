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
    (2, "ROSJA", 0),
    (2, "KOT", 0),
    (3, "Polska", 0),
    (3, "Kazachstan", 0),
    (3, "Rosja", 0),
    (4, "XD", 0),
    (4, "XP", 0),
    (4, "XC", 1),
    (5, "lol", 0),
    (5, "urugwaj", 1);


