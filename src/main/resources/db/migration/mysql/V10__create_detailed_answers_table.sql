CREATE TABLE detailed_answers (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    answer_id MEDIUMINT UNSIGNED NOT NULL,
    answer_summary_id MEDIUMINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (answer_id) REFERENCES answers(id),
    FOREIGN KEY (answer_summary_id) REFERENCES answers_summary(id)
);

