CREATE TABLE users_tests (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT UNSIGNED NOT NULL,
    test_id MEDIUMINT UNSIGNED NOT NULL,
    point_amount INT DEFAULT 0,
    begin_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (test_id) REFERENCES tests(id)
);

INSERT INTO users_tests (user_id, test_id, point_amount) VALUES
    (1, 1, 0);

