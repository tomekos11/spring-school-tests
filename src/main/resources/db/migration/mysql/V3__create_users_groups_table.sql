CREATE TABLE users_groups (
    id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT UNSIGNED NOT NULL,
    group_id MEDIUMINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

INSERT INTO users_groups (user_id, group_id) VALUES
   (1,1);

