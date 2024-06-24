CREATE TABLE users_tests (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT UNSIGNED NOT NULL,
    test_id MEDIUMINT UNSIGNED NOT NULL,
    group_test_id MEDIUMINT UNSIGNED NOT NULL,
    begin_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (test_id) REFERENCES tests(id),
    FOREIGN KEY (group_test_id) REFERENCES groups_tests(id)
);

INSERT INTO users_tests (user_id, test_id, group_test_id) VALUES
    (1, 1, 1);

