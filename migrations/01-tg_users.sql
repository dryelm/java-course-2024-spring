CREATE TABLE tg_users
(
    id BIGINT NOT NULL,

    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by  TEXT NOT NULL,

    PRIMARY KEY (id)
);
