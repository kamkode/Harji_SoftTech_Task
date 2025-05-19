@echo off
REM Setup script for Product Catalog Microservice development environment on Windows

echo Setting up development environment for Product Catalog Microservice...

REM Check if Java is installed
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java is not installed. Please install Java 17 or higher.
    exit /b 1
) else (
    for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
        set JAVA_VERSION=%%g
    )
    echo Java is installed: %JAVA_VERSION%
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven is not installed. Please install Maven 3.6 or higher.
    exit /b 1
) else (
    for /f "tokens=3" %%g in ('mvn --version ^| findstr /i "Apache Maven"') do (
        set MVN_VERSION=%%g
    )
    echo Maven is installed: %MVN_VERSION%
)

REM Check if Docker is installed
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Warning: Docker is not installed. Docker is recommended for containerized deployment.
) else (
    for /f "tokens=3" %%g in ('docker --version') do (
        set DOCKER_VERSION=%%g
    )
    echo Docker is installed: %DOCKER_VERSION%
)

REM Check if Docker Compose is installed
docker-compose --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Warning: Docker Compose is not installed. Docker Compose is recommended for containerized deployment.
) else (
    for /f "tokens=3" %%g in ('docker-compose --version') do (
        set DOCKER_COMPOSE_VERSION=%%g
    )
    echo Docker Compose is installed: %DOCKER_COMPOSE_VERSION%
)

REM Check if PostgreSQL is running
set PGPASSWORD=postgres
psql -h localhost -U postgres -d postgres -c "SELECT 1" >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Warning: PostgreSQL is not running or not accessible.
    
    REM Ask if user wants to start PostgreSQL with Docker
    set /p START_POSTGRES="Do you want to start PostgreSQL using Docker? (y/n) "
    if /i "%START_POSTGRES%"=="y" (
        echo Starting PostgreSQL with Docker...
        docker run -d --name postgres -p 5432:5432 -e POSTGRES_DB=product_catalog -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:15-alpine
        echo PostgreSQL started. Database: product_catalog, User: postgres, Password: postgres
    )
) else (
    echo PostgreSQL is running on localhost:5432
)

REM Build the project
echo Building the project...
call mvn clean install -DskipTests

REM Ask if user wants to run the application
set /p RUN_APP="Do you want to run the application now? (y/n) "
if /i "%RUN_APP%"=="y" (
    echo Starting the application...
    call mvn spring-boot:run
)

echo Development environment setup complete!
