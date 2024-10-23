CREATE TABLE phone
(
    id           BIGSERIAL PRIMARY KEY,
    phone_type   VARCHAR(10) NOT NULL CHECK (phone_type IN ('HOME', 'WORK', 'MOBILE')),
    phone_number VARCHAR(13) NOT NULL UNIQUE,
    user_id      BIGINT      NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);


COMMENT ON TABLE phone IS 'Stores phone numbers associated with users, including type and timestamps.';
COMMENT ON COLUMN phone.id IS 'Unique identifier for the phone number';
COMMENT ON COLUMN phone.phone_type IS 'Type of phone number, can be HOME, WORK, or MOBILE.';
COMMENT ON COLUMN phone.phone_number IS 'Unique phone number';
COMMENT ON COLUMN phone.user_id IS 'Foreign key referencing the application_user table (id). Represents the user to whom the phone number belongs.';
COMMENT ON COLUMN phone.created_at IS 'Timestamp when the record was created';
COMMENT ON COLUMN phone.updated_at IS 'Timestamp when the record was last updated';