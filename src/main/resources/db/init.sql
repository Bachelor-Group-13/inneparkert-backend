CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    license_plate VARCHAR(20),
    second_license_plate VARCHAR(20),
    phone_number VARCHAR(20),
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS reservations (
    id SERIAL PRIMARY KEY,
    spot_number VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    reservation_date DATE NOT NULL,
    license_plate VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (spot_number, reservation_date)
)