#!/bin/bash
# Setup script for Product Catalog Microservice development environment

echo "Setting up development environment for Product Catalog Microservice..."

# Check if Java is installed
if command -v java >/dev/null 2>&1; then
    java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java is installed: $java_version"
    
    # Check Java version
    if [[ "$java_version" < "17" ]]; then
        echo "Warning: Java version should be 17 or higher. Please upgrade Java."
    fi
else
    echo "Error: Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if command -v mvn >/dev/null 2>&1; then
    mvn_version=$(mvn --version | awk 'NR==1 {print $3}')
    echo "Maven is installed: $mvn_version"
else
    echo "Error: Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if Docker is installed
if command -v docker >/dev/null 2>&1; then
    docker_version=$(docker --version | awk '{print $3}' | sed 's/,//')
    echo "Docker is installed: $docker_version"
else
    echo "Warning: Docker is not installed. Docker is recommended for containerized deployment."
fi

# Check if Docker Compose is installed
if command -v docker-compose >/dev/null 2>&1; then
    docker_compose_version=$(docker-compose --version | awk '{print $3}' | sed 's/,//')
    echo "Docker Compose is installed: $docker_compose_version"
else
    echo "Warning: Docker Compose is not installed. Docker Compose is recommended for containerized deployment."
fi

# Check if PostgreSQL is running locally
if command -v pg_isready >/dev/null 2>&1; then
    if pg_isready -h localhost -p 5432 >/dev/null 2>&1; then
        echo "PostgreSQL is running on localhost:5432"
    else
        echo "Warning: PostgreSQL is not running on localhost:5432"
        
        # Ask if user wants to start PostgreSQL with Docker
        read -p "Do you want to start PostgreSQL using Docker? (y/n) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            echo "Starting PostgreSQL with Docker..."
            docker run -d --name postgres -p 5432:5432 -e POSTGRES_DB=product_catalog -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:15-alpine
            echo "PostgreSQL started. Database: product_catalog, User: postgres, Password: postgres"
        fi
    fi
else
    echo "Warning: PostgreSQL client tools not found. Cannot check if PostgreSQL is running."
    
    # Ask if user wants to start PostgreSQL with Docker
    read -p "Do you want to start PostgreSQL using Docker? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo "Starting PostgreSQL with Docker..."
        docker run -d --name postgres -p 5432:5432 -e POSTGRES_DB=product_catalog -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:15-alpine
        echo "PostgreSQL started. Database: product_catalog, User: postgres, Password: postgres"
    fi
fi

# Build the project
echo "Building the project..."
mvn clean install -DskipTests

# Ask if user wants to run the application
read -p "Do you want to run the application now? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "Starting the application..."
    mvn spring-boot:run
fi

echo "Development environment setup complete!"
