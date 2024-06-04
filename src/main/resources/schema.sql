CREATE TABLE customers (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        customer_id VARCHAR(255) NOT NULL UNIQUE,
        first_name VARCHAR(255) NOT NULL,
        last_name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        phone_number VARCHAR(255) NOT NULL
);