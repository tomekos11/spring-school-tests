CREATE TABLE users_answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT UNSIGNED NOT NULL,
    question_id MEDIUMINT UNSIGNED NOT NULL,
    is_correct BOOLEAN DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

INSERT INTO users_answers (user_id, question_id, is_correct) VALUES
    (1, 1, 1);

