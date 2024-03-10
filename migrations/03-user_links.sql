CREATE TABLE user_links
(
    user_id     BIGINT NOT NULL,
    link_id     BIGINT NOT NULL,
    PRIMARY KEY (user_id, link_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (link_id) REFERENCES links(id)
);
