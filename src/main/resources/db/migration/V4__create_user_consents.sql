CREATE TABLE user_consents
(
    id            UUID NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id       UUID NOT NULL,
    has_consented BOOLEAN,
    CONSTRAINT pk_user_consents PRIMARY KEY (id)
);

ALTER TABLE user_consents
    ADD CONSTRAINT uc_user_consents_user UNIQUE (user_id);

ALTER TABLE user_consents
    ADD CONSTRAINT FK_USER_CONSENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);