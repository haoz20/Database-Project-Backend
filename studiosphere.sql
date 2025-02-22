-- users Table
CREATE TABLE users (
    users_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Customer Table
CREATE TABLE customer(
    customer_id SERIAL PRIMARY KEY,
    studiosphere_points INT DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES users(users_id) ON DELETE CASCADE
);

-- Photographer Table
CREATE TABLE photographer (
    photographer_id SERIAL PRIMARY KEY,
    speciality VARCHAR(50),
    category VARCHAR(255),
    portfolio TEXT,
	available_to_work_in VARCHAR(255),
    rating DECIMAL(2,1) CHECK (rating >= 0 AND rating <= 5),
    FOREIGN KEY (photographer_id) REFERENCES users(users_id) ON DELETE CASCADE
);

-- Admin Table
CREATE TABLE admin (
    admin_id SERIAL PRIMARY KEY,
    permission VARCHAR(50),
    FOREIGN KEY (admin_id) REFERENCES users(users_id) ON DELETE CASCADE
);

-- Availability Table
CREATE TABLE availability (
    photographer_id INT NOT NULL,
    available_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'available',
    PRIMARY KEY(photographer_id, available_date),
    FOREIGN KEY (photographer_id) REFERENCES photographer(photographer_id) ON DELETE CASCADE
);

-- Booking Table
CREATE TABLE booking (
    booking_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    photographer_id INT NOT NULL,
    event_date DATE NOT NULL,
    event_location VARCHAR(255) NOT NULL,
    booking_status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (photographer_id) REFERENCES photographer(photographer_id) ON DELETE CASCADE,
	CONSTRAINT unique_booking UNIQUE (customer_id, photographer_id, event_date)

);

-- Review Table
CREATE TABLE review (
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    feedback TEXT NOT NULL,
    rate INT CHECK (rate BETWEEN 1 AND 5) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE
);

-- Issue Table
CREATE TABLE issue (
    issue_id SERIAL PRIMARY KEY,
    reported_by INT NOT NULL,
    assigned_to INT,
    description TEXT NOT NULL,
    issue_type VARCHAR(50) NOT NULL,  -- Changed from ENUM to VARCHAR
    issue_status VARCHAR(50) DEFAULT 'New',  -- Changed from ENUM to VARCHAR
    resolution_detail TEXT,
    resolution_date DATE,
    FOREIGN KEY (reported_by) REFERENCES customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES admin(admin_id) ON DELETE SET NULL
);
