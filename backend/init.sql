CREATE DATABASE IF NOT EXISTS UserDB;
CREATE DATABASE IF NOT EXISTS ProductDB;
CREATE DATABASE IF NOT EXISTS MaintenanceDB;
CREATE DATABASE IF NOT EXISTS PurchaseOrderDB;
CREATE DATABASE IF NOT EXISTS CompanyDB;
CREATE DATABASE IF NOT EXISTS StockDB;
CREATE DATABASE IF NOT EXISTS StockTransferDB;
CREATE DATABASE IF NOT EXISTS AssetDB;
CREATE DATABASE IF NOT EXISTS GrnDB;
CREATE DATABASE IF NOT EXISTS WarehouseDB;
CREATE DATABASE IF NOT EXISTS SupplierDB;


-- USE MaintenanceDB;
-- CREATE TABLE IF NOT EXISTS maintenance_status (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     name VARCHAR(20) NOT NULL,
--     description VARCHAR(255)
-- );
-- INSERT IGNORE INTO maintenance_status (id, name, description) VALUES
-- (1, 'PENDING', 'Maintenance is scheduled but not started'),
-- (2, 'IN_PROGRESS', 'Maintenance work is currently in progress'),
-- (3, 'COMPLETED', 'Maintenance has been completed'),
-- (4, 'CANCELLED', 'Maintenance has been cancelled');
