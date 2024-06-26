CREATE TABLE groups_tests (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    group_id MEDIUMINT UNSIGNED NOT NULL,
    test_id MEDIUMINT UNSIGNED NOT NULL,
    begin_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resit BOOLEAN DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (test_id) REFERENCES tests(id)
);

INSERT INTO groups_tests (group_id, test_id, begin_date, end_date) VALUES
    (1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY)),
    (2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY)),
    (2, 2, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY));

