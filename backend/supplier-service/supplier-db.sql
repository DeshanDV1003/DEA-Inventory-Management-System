CREATE DATABASE IF NOT EXISTS supplier_db;
USE supplier_db;

CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    status VARCHAR(50),
    created_by VARCHAR(100),
    created_date DATETIME,
    updated_by VARCHAR(100),
    updated_date DATETIME
);
