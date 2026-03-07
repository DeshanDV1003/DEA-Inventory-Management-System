# DEA Inventory Management System

A full-stack **Microservice Inventory Management System** built with Spring Boot and React, containerized with Docker, and deployed on AWS EC2 with CI/CD automation.

## Architecture

```
                         ┌──────────────┐
                         │   Frontend   │
                         │  React/Vite  │
                         │   :3001      │
                         └──────┬───────┘
                                │
          ┌─────────────────────┼─────────────────────┐
          │                     │                      │
    ┌─────┴─────┐     ┌────────┴───────┐    ┌────────┴────────┐
    │  Backend   │     │   Backend      │    │   Backend       │
    │ Services   │     │   Services     │    │   Services      │
    │ (11 APIs)  │     │   (Feign)      │    │   (JWT Auth)    │
    └─────┬─────┘     └────────┬───────┘    └────────┬────────┘
          │                     │                      │
          └─────────────────────┼──────────────────────┘
                                │
                         ┌──────┴───────┐
                         │    MySQL     │
                         │   :3307      │
                         └──────────────┘
```

## Tech Stack

| Layer      | Technology                                      |
|------------|------------------------------------------------|
| Frontend   | React 19, Vite 7, React Router, Axios, Recharts |
| Backend    | Spring Boot 3, Java 17, Spring Security, JPA   |
| Auth       | JWT (JSON Web Tokens)                           |
| Database   | MySQL 8.0 (one DB per service)                  |
| IPC        | OpenFeign (inter-service communication)         |
| Containers | Docker, Docker Compose                          |
| CI/CD      | GitHub Actions (smart change detection)         |
| Cloud      | AWS EC2                                         |

## Microservices

| Service                | Internal Port | External Port | Database         | API Base Path                |
|------------------------|:------------:|:-------------:|------------------|------------------------------|
| User Service           | 8071         | 8072          | UserDB           | `/api/v1/users`              |
| Company Service        | 8021         | 8022          | CompanyDB        | `/api/v1/companies`          |
| Product Service        | 8081         | 8082          | ProductDB        | `/api/v1/products`           |
| Warehouse Service      | 9011         | 9012          | WarehouseDB      | `/api/v1/warehouses`         |
| Supplier Service       | 9021         | 9022          | SupplierDB       | `/api/v1/suppliers`          |
| Purchase Order Service | 8061         | 8062          | PurchaseOrderDB  | `/api/v1/purchase-orders`    |
| GRN Service            | 8031         | 8032          | GrnDB            | `/api/v1/grns`               |
| Stock Service          | 8041         | 8042          | StockDB          | `/api/v1/stocks`             |
| Stock Transfer Service | 8051         | 8052          | StockTransferDB  | `/api/v1/stock-transfers`    |
| Asset Service          | 8011         | 8012          | AssetDB          | `/api/v1/assets`             |
| Maintenance Service    | 8091         | 8092          | MaintenanceDB    | `/api/v1/maintenances`       |
| Frontend               | 3000         | 3001          | -                | -                            |

## Inter-Service Communication

Services communicate via **OpenFeign** clients with JWT token forwarding:

- **GRN Service** → Warehouse Service (validate warehouse), Stock Service (adjust stock)
- **Stock Service** → Company Service, Product Service, Warehouse Service (validate entities)
- **Supplier Service** → Product Service, Purchase Order Service, GRN Service, Company Service

## Project Structure

```
DEA-Inventory-Management-System/
├── .github/
│   └── workflows/
│       └── deploy.yml          # CI/CD pipeline
├── backend/
│   ├── docker-compose.yml      # All services orchestration
│   ├── init.sql                # Database initialization
│   ├── user-service/
│   ├── company-service/
│   ├── product-service/
│   ├── warehouse-service/
│   ├── supplier-service/
│   ├── purchase-order-service/
│   ├── grn-service/
│   ├── stock-service/
│   ├── stock-transfer-service/
│   ├── asset-service/
│   └── maintenance-service/
├── frontend/
│   ├── src/
│   │   ├── pages/              # React page components
│   │   ├── components/         # Shared components (Sidebar)
│   │   └── services/           # API layer (Axios)
│   └── Dockerfile
└── README.md
```

## Frontend Pages

| Page           | Description                                      |
|----------------|--------------------------------------------------|
| Login          | JWT authentication for admin users               |
| Dashboard      | Overview with charts (Recharts)                  |
| Company        | Manage companies                                 |
| Warehouse      | Manage warehouses per company                    |
| Suppliers      | Manage suppliers                                 |
| Products       | CRUD products with optional SKU                  |
| Purchase Orders| Create and manage purchase orders                |
| GRN            | Goods Received Notes - receive stock from POs    |
| Stock          | View and manage inventory levels                 |
| Stock Transfer | Transfer stock between warehouses                |
| Assets         | Track company assets                             |
| Maintenance    | Schedule and track asset maintenance             |

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Git

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/DeshanDV1003/DEA-Inventory-Management-System.git
   cd DEA-Inventory-Management-System
   ```

2. **Update frontend environment**

   Edit `frontend/.env` with your host IP (use `localhost` for local dev):
   ```env
   VITE_USER_SERVICE=http://localhost:8072
   VITE_PRODUCT_SERVICE=http://localhost:8082
   VITE_COMPANY_SERVICE=http://localhost:8022
   VITE_WAREHOUSE_SERVICE=http://localhost:9012
   VITE_SUPPLIER_SERVICE=http://localhost:9022
   VITE_PO_SERVICE=http://localhost:8062
   VITE_GRN_SERVICE=http://localhost:8032
   VITE_STOCK_SERVICE=http://localhost:8042
   VITE_STOCK_TRANSFER_SERVICE=http://localhost:8052
   VITE_ASSET_SERVICE=http://localhost:8012
   VITE_MAINTENANCE_SERVICE=http://localhost:8092
   ```

3. **Start all services**
   ```bash
   cd backend
   docker compose up -d --build
   ```

4. **Access the application**
   - Frontend: http://localhost:3001
   - MySQL: `localhost:3307` (user: `root`, password: `root`)

### Start a Single Service

```bash
cd backend
docker compose up -d --build <service-name>
```

Example:
```bash
docker compose up -d --build grn-service stock-service
```

## CI/CD Pipeline

The project uses **GitHub Actions** with smart change detection. Only modified services are rebuilt on deployment.

### How it works

1. Push to `dev` branch triggers the pipeline
2. Pipeline detects which service folders changed
3. SSH into EC2, pulls latest code
4. Only rebuilds the affected Docker containers
5. If `docker-compose.yml` changes, all services are rebuilt

### Required GitHub Secrets

| Secret       | Description                    |
|-------------|-------------------------------|
| `EC2_HOST`  | EC2 public IP address          |
| `EC2_USER`  | SSH username (e.g., `ec2-user`)|
| `EC2_SSH_KEY`| Private SSH key for EC2       |

## Database

Each microservice has its own MySQL database (Database-per-Service pattern):

```
MySQL 8.0 (single instance, multiple databases)
├── UserDB
├── CompanyDB
├── ProductDB
├── WarehouseDB
├── SupplierDB
├── PurchaseOrderDB
├── GrnDB
├── StockDB
├── StockTransferDB
├── AssetDB
└── MaintenanceDB
```

Tables are auto-created by Hibernate (`ddl-auto: update`).

### Connect via MySQL Workbench

- Host: `<EC2_IP>` or `localhost`
- Port: `3307`
- Username: `root`
- Password: `root`

## API Authentication

All endpoints (except login/register) require a JWT token in the `Authorization` header:

```
Authorization: Bearer <jwt_token>
```

Tokens are obtained via the User Service login endpoint.

## Key Design Decisions

- **Database-per-Service**: Each microservice owns its database for loose coupling
- **Feign with JWT Forwarding**: `FeignConfig` interceptors forward auth tokens between services
- **No Foreign Keys Across Services**: Only IDs are stored; validation happens via Feign calls
- **GRN Auto-Stock**: Creating a GRN automatically adjusts stock levels; cancelling reverses them
- **Smart CI/CD**: Only changed services are rebuilt to minimize deployment time

## Team

DEA Group - University Project
