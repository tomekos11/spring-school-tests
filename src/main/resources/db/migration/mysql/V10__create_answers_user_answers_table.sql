CREATE TABLE answers_user_answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    answer_id MEDIUMINT UNSIGNED NOT NULL,
    user_answer_id MEDIUMINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (answer_id) REFERENCES answers(id),
    FOREIGN KEY (user_answer_id) REFERENCES users_answers(id)
);

