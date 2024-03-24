CREATE TABLE user_links
(
    user_id     BIGINT NOT NULL,
    link_id     BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by TEXT NOT NULL,
    PRIMARY KEY (user_id, link_id),
    FOREIGN KEY (user_id) REFERENCES tg_users(id),
    FOREIGN KEY (link_id) REFERENCES links(id)
);
