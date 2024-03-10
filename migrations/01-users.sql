CREATE TABLE users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    user_tg_id   TEXT NOT NULL,

    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by  TEXT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (user_tg_id)
);
