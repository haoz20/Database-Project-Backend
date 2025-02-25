-- users Table
CREATE TABLE users
(
    users_id SERIAL PRIMARY KEY,
    name     VARCHAR(100)        NOT NULL,
    email    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(50)         NOT NULL
);

-- Customer Table
CREATE TABLE customer
(
    customer_id         SERIAL PRIMARY KEY,
    studiosphere_points INT DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES users (users_id) ON DELETE CASCADE
);

-- Photographer Table
CREATE TABLE photographer
(
    photographer_id      SERIAL PRIMARY KEY,
    speciality           VARCHAR(50),
    category             VARCHAR(255),
    portfolio            TEXT,
    available_to_work_in VARCHAR(255),
    rating               DECIMAL(2, 1) CHECK (rating >= 0 AND rating <= 5),
    FOREIGN KEY (photographer_id) REFERENCES users (users_id) ON DELETE CASCADE
);

-- Admin Table
CREATE TABLE admin
(
    admin_id   SERIAL PRIMARY KEY,
    permission VARCHAR(50),
    FOREIGN KEY (admin_id) REFERENCES users (users_id) ON DELETE CASCADE
);

-- Availability Table
CREATE TABLE availability
(
    photographer_id INT  NOT NULL,
    available_date  DATE NOT NULL,
    status          VARCHAR(50) DEFAULT 'available',
    PRIMARY KEY (photographer_id, available_date),
    FOREIGN KEY (photographer_id) REFERENCES photographer (photographer_id) ON DELETE CASCADE
);

CREATE TABLE booking
(
    booking_id      SERIAL PRIMARY KEY,
    customer_id     INT          NOT NULL,
    photographer_id INT          NOT NULL,
    event_date      DATE         NOT NULL,
    event_location  VARCHAR(255) NOT NULL,
    booking_status  VARCHAR(50) DEFAULT 'Pending',
    speciality      VARCHAR(50)  NOT NULL,
    category        VARCHAR(100) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (photographer_id) REFERENCES photographer (photographer_id) ON DELETE CASCADE,
    CONSTRAINT unique_booking UNIQUE (customer_id, photographer_id, event_date)
);


-- Review Table
CREATE TABLE review
(
    booking_id  INT                              NOT NULL,
    customer_id INT                              NOT NULL,
    feedback    TEXT                             NOT NULL,
    rate        INT CHECK (rate BETWEEN 1 AND 5) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking (booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

-- Issue Table
CREATE TABLE issue
(
    issue_id          SERIAL PRIMARY KEY,
    reported_by       INT         NOT NULL,
    assigned_to       INT,
    description       TEXT        NOT NULL,
    issue_type        VARCHAR(50) NOT NULL,      -- Changed from ENUM to VARCHAR
    issue_status      VARCHAR(50) DEFAULT 'New', -- Changed from ENUM to VARCHAR
    resolution_detail TEXT,
    reported_at       DATE,
    FOREIGN KEY (reported_by) REFERENCES customer (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES admin (admin_id) ON DELETE SET NULL
);

-- Insert Users
INSERT INTO users (name, email, password, role)
VALUES ('John Doe', 'john.doe@example.com', 'hashed_password', 'CUSTOMER'),
       ('Jane Photographer', 'jane.photographer@example.com', 'hashed_password', 'PHOTOGRAPHER'),
       ('Alice Admin', 'alice.admin@example.com', 'hashed_password', 'ADMIN');

-- Insert into Customer table (for user with id = 1)
INSERT INTO customer (customer_id, studiosphere_points)
VALUES (1, 0);

-- Insert into Photographer table (for user with id = 2)
INSERT INTO photographer (photographer_id, speciality, category, portfolio, available_to_work_in, rating)
VALUES (2, 'PHOTOGRAPHY', 'Wedding,Portrait', 'http://portfolio.example.com', 'Bangkok,Phuket', 4.5);

-- Insert into Admin table (for user with id = 3)
INSERT INTO admin (admin_id, permission)
VALUES (3, 'ALL');

-- Insert into Availability table for photographer (id = 2)
INSERT INTO availability (photographer_id, available_date, status)
VALUES (2, '2025-03-20', 'available');

-- Insert Bookings
-- Booking 1: Customer 1 books Photographer 2, status "Booked"
INSERT INTO booking (customer_id, photographer_id, event_date, event_location, booking_status, speciality, category)
VALUES (1, 2, '2025-04-10', 'Bangkok', 'Booked', 'PHOTOGRAPHY', 'Wedding');

-- Booking 2: Customer 1 books Photographer 2, status "Pending"
INSERT INTO booking (customer_id, photographer_id, event_date, event_location, booking_status, speciality, category)
VALUES (1, 2, '2025-04-15', 'Phuket', 'Pending', 'PHOTOGRAPHY', 'Portrait');

-- Insert Review for Booking 1
INSERT INTO review (booking_id, customer_id, feedback, rate)
VALUES (1, 1, 'Great work!', 5);

-- Insert Issue
-- Issue reported by Customer 1 (customer_id = 1) assigned to Admin 3 (admin_id = 3)
INSERT INTO issue (reported_by, assigned_to, description, issue_type, issue_status, resolution_detail, reported_at)
VALUES (1, 3, 'Payment issue encountered during checkout.', 'Payment', 'New', NULL, '2025-03-10');

