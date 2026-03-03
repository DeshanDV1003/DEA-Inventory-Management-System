-- warehouse database creation script

CREATE DATABASE IF NOT EXISTS warehouse_db;
USE warehouse_db;

CREATE TABLE IF NOT EXISTS warehouses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    company_id INT NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    status VARCHAR(50),
    address VARCHAR(255),
    created_by VARCHAR(100),
    created_date DATETIME,
    updated_by VARCHAR(100),
    updated_date DATETIME
);
