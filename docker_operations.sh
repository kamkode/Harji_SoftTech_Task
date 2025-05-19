#!/bin/bash
# Script for Docker operations with Product Catalog Microservice

# Function to display help
show_help() {
    echo "Usage: ./docker_operations.sh [COMMAND]"
    echo "Commands:"
    echo "  build       Build Docker image"
    echo "  start       Start all services with Docker Compose"
    echo "  stop        Stop all services"
    echo "  restart     Restart all services"
    echo "  logs        Show logs from all services"
    echo "  ps          Show running containers"
    echo "  clean       Remove all containers and volumes"
    echo "  help        Display this help message"
    echo ""
    echo "Examples:"
    echo "  ./docker_operations.sh build"
    echo "  ./docker_operations.sh start"
    echo "  ./docker_operations.sh logs"
}

# Check if Docker is installed
if ! command -v docker >/dev/null 2>&1; then
    echo "Error: Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose >/dev/null 2>&1; then
    echo "Error: Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

# Parse command
if [[ $# -eq 0 ]]; then
    show_help
    exit 1
fi

case "$1" in
    build)
        echo "Building Docker image..."
        docker build -t product-catalog-service .
        echo "Docker image built successfully."
        ;;
    start)
        echo "Starting services with Docker Compose..."
        docker-compose up -d
        echo "Services started. The API is available at http://localhost:8080"
        ;;
    stop)
        echo "Stopping services..."
        docker-compose down
        echo "Services stopped."
        ;;
    restart)
        echo "Restarting services..."
        docker-compose down
        docker-compose up -d
        echo "Services restarted. The API is available at http://localhost:8080"
        ;;
    logs)
        echo "Showing logs from all services..."
        docker-compose logs -f
        ;;
    ps)
        echo "Showing running containers..."
        docker-compose ps
        ;;
    clean)
        echo "Removing all containers and volumes..."
        docker-compose down -v
        echo "Containers and volumes removed."
        ;;
    help)
        show_help
        ;;
    *)
        echo "Unknown command: $1"
        show_help
        exit 1
        ;;
esac

exit 0
