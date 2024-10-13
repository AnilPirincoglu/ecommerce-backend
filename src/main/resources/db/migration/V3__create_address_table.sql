CREATE TABLE address
(
    id           BIGSERIAL PRIMARY KEY,
    address_type VARCHAR(6)   NOT NULL CHECK ( address_type IN ('HOME', 'OFFICE', 'OTHER') ),
    address_line VARCHAR(255) NOT NULL,
    street       VARCHAR(50)  NOT NULL,
    district     VARCHAR(50)  NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    postal_code  VARCHAR(5)   NOT NULL,
    user_id      BIGINT       NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);


COMMENT ON TABLE address IS 'Stores information about user addresses';
COMMENT ON COLUMN address.id IS 'Unique identifier for the address';
COMMENT ON COLUMN address.address_type IS 'Type of address, can be HOME, OFFICE, or OTHER';
COMMENT ON COLUMN address.address_line IS 'The address line';
COMMENT ON COLUMN address.street IS 'Street name';
COMMENT ON COLUMN address.district IS 'District name';
COMMENT ON COLUMN address.city IS 'City name';
COMMENT ON COLUMN address.postal_code IS 'Postal code of the address';
COMMENT ON COLUMN address.user_id IS 'Reference to the user ID from application_user';
COMMENT ON COLUMN address.created_at IS 'Timestamp when the record was created';
COMMENT ON COLUMN address.updated_at IS 'Timestamp when the record was last updated';

