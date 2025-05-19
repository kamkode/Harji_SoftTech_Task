# Product Catalog Microservice

A Spring Boot microservice for managing product information with event publishing capabilities. This service provides REST APIs for product management and publishes events when products are created or updated.

## Tech Stack

- Java 17 (compatible with Java 21)
- Spring Boot 3.2
- PostgreSQL for data persistence
- Event-driven architecture with GCP Pub/Sub (mock implementation)
- Maven for dependency management and build
- Docker & Docker Compose for containerization
- OpenAPI/Swagger for API documentation
- JUnit & Mockito for testing

## Features

- RESTful API for product management
- Event-driven architecture with multiple messaging options:
  - Default: Simple event logging
  - GCP Pub/Sub: Mock implementation
- Containerized deployment with Docker
- Comprehensive test coverage with unit and integration tests
- API documentation with Swagger/OpenAPI

## Getting Started

### Prerequisites

- Java 17 or higher (Java 21 recommended)
- Maven 3.6+
- Docker and Docker Compose (optional, for containerized setup)
- PostgreSQL 12+ (if running locally without Docker)
- Git

### Local Setup

1. **Clone the repository**

```bash
git clone https://github.com/your-username/product-catalog-service.git
cd product-catalog-service
```

2. **Configure PostgreSQL**

If running PostgreSQL locally, create a database named `product_catalog`:

```sql
CREATE DATABASE product_catalog;
```

Update the database configuration in `src/main/resources/application.properties` if needed.

3. **Run the application**

```bash
mvn spring-boot:run
```

The application will start on port 8080.

### Docker Setup

1. **Build and run with Docker Compose**

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database
- GCP Pub/Sub emulator
- Product Catalog service

2. **Access the application**

The application will be available at http://localhost:8080

## Event Publishing

The application supports multiple event publishing mechanisms:

### Default Mode

By default, the application logs events without publishing to an external message broker.

### GCP Pub/Sub Mode

To use the GCP Pub/Sub mock implementation:

1. Set the active profile to `pubsub`:
   ```properties
   spring.profiles.active=pubsub
   ```

2. When running with Docker Compose, the Pub/Sub emulator is automatically started and configured.



### Event Structure

All events follow this structure:

```json
{
  "eventType": "PRODUCT_CREATED" | "PRODUCT_UPDATED",
  "timestamp": "2025-04-23T12:00:00Z",
  "product": { ... }
}
```

## API Usage

### Swagger UI

Access the Swagger UI at: http://localhost:8080/swagger-ui.html

### API Endpoints

#### Create a Product

```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sample Product",
    "description": "This is a sample product",
    "category": "Electronics",
    "price": 99.99,
    "availableStock": 100
  }'
```

#### Update a Product

```bash
curl -X PUT http://localhost:8080/products/{productId} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Product",
    "description": "This is an updated product",
    "category": "Electronics",
    "price": 149.99,
    "availableStock": 50
  }'
```

#### Get a Product by ID

```bash
curl -X GET http://localhost:8080/products/{productId}
```

#### Get All Products

```bash
curl -X GET http://localhost:8080/products
```

## Testing

The project includes both unit tests and integration tests.

### Running Unit Tests

```bash
mvn test
```

### Running Integration Tests

```bash
mvn test -Dtest=*IntegrationTest
```

### Running All Tests

```bash
mvn verify
```

### Using the Test Script

You can also use the provided test script:

On Unix/Linux/Mac:

```bash
./run_tests.sh --all        # Run all tests
./run_tests.sh --unit       # Run only unit tests
./run_tests.sh --int        # Run only integration tests
./run_tests.sh --coverage   # Generate test coverage report
```

On Windows:

```batch
run_tests.bat -a            # Run all tests
run_tests.bat -u            # Run only unit tests
run_tests.bat -i            # Run only integration tests
run_tests.bat -c            # Generate test coverage report
```

## Utility Scripts

The project includes several utility scripts to help with development and deployment:

### Development Environment Setup

On Unix/Linux/Mac:

```bash
./setup_dev_environment.sh
```

On Windows:

```batch
setup_dev_environment.bat
```

This script checks your environment for required dependencies, helps set up PostgreSQL, and builds the project.

### Docker Operations

On Unix/Linux/Mac:

```bash
./docker_operations.sh build    # Build Docker image
./docker_operations.sh start    # Start all services with Docker Compose
./docker_operations.sh stop     # Stop all services
./docker_operations.sh logs     # Show logs from all services
./docker_operations.sh clean    # Remove all containers and volumes
```

On Windows:

```batch
docker_operations.bat build    # Build Docker image
docker_operations.bat start    # Start all services with Docker Compose
docker_operations.bat stop     # Stop all services
docker_operations.bat logs     # Show logs from all services
docker_operations.bat clean    # Remove all containers and volumes
```

### Git Operations

The `git_commands.sh` script provides a reference for how to commit and push the project with proper commit messages.

## Project Structure

```
src/
├── main/
│   ├── java/com/harji/productcatalog/
│   │   ├── config/              # Configuration classes
│   │   ├── controller/          # REST controllers
│   │   ├── domain/              # Domain entities
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Exception handling
│   │   ├── repository/          # Data repositories
│   │   ├── service/             # Business logic
│   │   │   └── impl/            # Service implementations
│   │   └── ProductCatalogApplication.java  # Main application class
│   └── resources/
│       ├── application.properties          # Main configuration
│       ├── application-pubsub.properties   # Pub/Sub profile config
│       └── application-test.properties     # Test configuration
├── test/
│   ├── java/com/harji/productcatalog/
│   │   ├── controller/          # Controller tests
│   │   └── service/             # Service tests
│   └── resources/
│       └── application-test.properties     # Test configuration
└── scripts/                     # Utility scripts
```

## Design Decisions

### Event-Driven Architecture

The service follows an event-driven architecture where product changes (creation/updates) trigger events. These events can be consumed by other services for various purposes like updating search indexes, sending notifications, or updating analytics.

### Clean Architecture

The project follows clean architecture principles with clear separation of concerns:
- **Controllers**: Handle HTTP requests and responses
- **Services**: Contain business logic
- **Repositories**: Manage data access
- **DTOs**: Transfer data between layers
- **Domain Entities**: Represent the core business objects

### Error Handling

The service includes a global exception handler that provides consistent error responses across all endpoints.

## License

This project is proprietary and confidential. Unauthorized copying, distribution, or use is strictly prohibited.
