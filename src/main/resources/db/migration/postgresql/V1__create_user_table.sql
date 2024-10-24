CREATE TABLE _user
(
    id            BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email         VARCHAR(80) NOT NULL UNIQUE,
    gender        VARCHAR(6)  NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    date_of_birth DATE        NOT NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);


COMMENT ON TABLE _user IS 'Stores information about users';
COMMENT ON COLUMN _user.id IS 'Unique identifier for the user';
COMMENT ON COLUMN _user.first_name IS 'User''s first name';
COMMENT ON COLUMN _user.last_name IS 'User''s last name';
COMMENT ON COLUMN _user.email IS 'User''s email address (unique)';
COMMENT ON COLUMN _user.gender IS 'User''s gender, can be MALE or FEMALE';
COMMENT ON COLUMN _user.date_of_birth IS 'User''s date of birth';
COMMENT ON COLUMN _user.created_at IS 'Timestamp when the record was created';
COMMENT ON COLUMN _user.updated_at IS 'Timestamp when the record was last updated';