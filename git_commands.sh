#!/bin/bash
# Git commands for pushing the Product Catalog Microservice

# Initialize git repository (if not already initialized)
git init

# Add all files to staging
git add .

# Create initial commit with project structure
git commit -m "Initial commit: Project structure and configuration"

# Add core domain entities
git add src/main/java/com/harji/productcatalog/domain/
git commit -m "Add domain entities for product catalog"

# Add DTOs for data transfer
git add src/main/java/com/harji/productcatalog/dto/
git commit -m "Add DTOs for API request/response handling"

# Add repositories for data access
git add src/main/java/com/harji/productcatalog/repository/
git commit -m "Add repositories for PostgreSQL data persistence"

# Add service interfaces and implementations
git add src/main/java/com/harji/productcatalog/service/
git commit -m "Add service layer with business logic and event publishing"

# Add controllers for REST API
git add src/main/java/com/harji/productcatalog/controller/
git commit -m "Add REST controllers for product management API"

# Add exception handling
git add src/main/java/com/harji/productcatalog/exception/
git commit -m "Add global exception handling for consistent error responses"

# Add configuration classes
git add src/main/java/com/harji/productcatalog/config/
git commit -m "Add configuration for Pub/Sub, OpenAPI, and application settings"

# Add tests
git add src/test/
git commit -m "Add unit and integration tests for controllers and services"

# Add Docker configuration
git add Dockerfile docker-compose.yml
git commit -m "Add Docker configuration for containerized deployment"

# Add documentation
git add README.md API_TESTING_GUIDE.md
git commit -m "Add comprehensive documentation and API testing guide"

# Add main application class
git add src/main/java/com/harji/productcatalog/ProductCatalogApplication.java
git commit -m "Add main application class"

# Set the remote repository URL (replace with your actual repository URL)
git remote add origin https://github.com/your-username/product-catalog-service.git

# Push to the remote repository
git push -u origin master

echo "Project successfully pushed to repository with proper commit history."
