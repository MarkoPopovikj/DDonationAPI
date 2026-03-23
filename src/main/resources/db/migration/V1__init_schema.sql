CREATE TABLE items
(
    id                 UUID         NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    original_file_name VARCHAR(255),
    file_url           VARCHAR(255) NOT NULL,
    file_size_bytes    BIGINT,
    mime_type          VARCHAR(100),
    language           VARCHAR(255),
    category           VARCHAR(50)  NOT NULL,
    CONSTRAINT pk_items PRIMARY KEY (id)
);