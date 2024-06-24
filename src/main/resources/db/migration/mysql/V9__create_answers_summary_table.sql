CREATE TABLE answers_summary (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_test_id MEDIUMINT UNSIGNED NOT NULL,
    question_id MEDIUMINT UNSIGNED NOT NULL,
    is_correct BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (user_test_id) REFERENCES users_tests(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

