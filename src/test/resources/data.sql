-- This file allows us to load static data into the test database before tests are run.

-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
-- Encrypted using https://www.javainuse.com/onlineBcrypt
CREATE TABLE IF NOT EXISTS local_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255),
    email_verified BOOLEAN
);

INSERT INTO local_user (email, first_name, last_name, password, username, email_verified)
    VALUES
    ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$hBn5gu6cGelJNiE6DDsaBOmZgyumCSzVwrOK/37FWgJ6aLIdZSSI2', 'UserA', true),
    ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$TlYbg57fqOy/1LJjispkjuSIvFJXbh3fy0J9fvHnCpuntZOITAjVG', 'UserB', false);

CREATE TABLE IF NOT EXISTS address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_line_1 VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES local_user(id)
);

INSERT INTO address (address_line_1, city, country, user_id)
    VALUES
    ('123 Tester Hill', 'Testerton', 'England', 1),
    ('312 Spring Boot', 'Hibernate', 'England', 2);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    short_desc TEXT,
    long_desc TEXT,
    price DECIMAL(10, 2)
);

INSERT INTO product (name, short_desc, long_desc, price)
    VALUES
    ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50),
    ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56),
    ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74),
    ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69),
    ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    stock INT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

INSERT INTO inventory (product_id, stock)
    VALUES
    (1, 5),
    (2, 8),
    (3, 12),
    (4, 73),
    (5, 2);

CREATE TABLE IF NOT EXISTS web_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id),
    FOREIGN KEY (user_id) REFERENCES local_user(id)
);

INSERT INTO web_order (address_id, user_id)
    VALUES
    (1, 1),
    (1, 1),
    (1, 1),
    (2, 2),
    (2, 2);

CREATE TABLE IF NOT EXISTS web_order_quantities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES web_order(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

INSERT INTO web_order_quantities (order_id, product_id, quantity)
    VALUES
    (1, 1, 5),
    (1, 2, 5),
    (2, 3, 5),
    (2, 2, 5),
    (2, 5, 5),
    (3, 3, 5),
    (4, 4, 5),
    (4, 2, 5),
    (5, 3, 5),
    (5, 1, 5);
