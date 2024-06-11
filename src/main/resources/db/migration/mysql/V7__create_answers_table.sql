CREATE TABLE answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    question_id MEDIUMINT UNSIGNED NOT NULL,
    is_correct BOOLEAN DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

INSERT INTO answers (question_id, is_correct) VALUES
    (1, 1);

