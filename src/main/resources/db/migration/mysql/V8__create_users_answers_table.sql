CREATE TABLE users_answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT UNSIGNED NOT NULL,
    question_id MEDIUMINT UNSIGNED NOT NULL,
    is_correct BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

