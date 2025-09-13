-- Create rooms table
CREATE TABLE rooms (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       price_per_night DECIMAL(10,2) NOT NULL,
                       is_available BOOLEAN NOT NULL,
                       owner_id BINARY(16),   -- matches users.id
                       CONSTRAINT fk_room_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Create bookings table
CREATE TABLE bookings (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          room_id BIGINT NOT NULL,
                          user_id BINARY(16) NOT NULL,   -- matches users.id
                          check_in_date DATE NOT NULL,
                          check_out_date DATE NOT NULL,
                          CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES rooms(id),
                          CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Indexes for performance
CREATE INDEX idx_booking_room ON bookings(room_id);
CREATE INDEX idx_booking_checkin ON bookings(check_in_date);
CREATE INDEX idx_booking_checkout ON bookings(check_out_date);
CREATE INDEX idx_booking_room_dates
    ON bookings(room_id, check_in_date, check_out_date);
