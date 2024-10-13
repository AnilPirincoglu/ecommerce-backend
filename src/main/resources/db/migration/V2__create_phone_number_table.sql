CREATE TABLE phone_number
(
    id           BIGSERIAL PRIMARY KEY,
    phone_type   VARCHAR(10) NOT NULL CHECK (phone_type IN ('HOME', 'WORK', 'MOBILE')),
    phone_number VARCHAR(13) NOT NULL UNIQUE,
    user_id      BIGINT      NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);


COMMENT ON TABLE phone_number IS 'Stores phone numbers associated with users, including type and timestamps.';
COMMENT ON COLUMN phone_number.id IS 'Unique identifier for the phone number';
COMMENT ON COLUMN phone_number.phone_type IS 'Type of phone number, can be HOME, WORK, or MOBILE.';
COMMENT ON COLUMN phone_number.phone_number IS 'Unique phone number';
COMMENT ON COLUMN phone_number.user_id IS 'Foreign key referencing the application_user table (id). Represents the user to whom the phone number belongs.';
COMMENT ON COLUMN phone_number.created_at IS 'Timestamp when the record was created';
COMMENT ON COLUMN phone_number.updated_at IS 'Timestamp when the record was last updated';