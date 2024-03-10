CREATE TABLE links
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    url        TEXT                     NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by TEXT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (url)
);
