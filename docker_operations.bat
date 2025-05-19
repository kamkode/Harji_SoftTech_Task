@echo off
REM Script for Docker operations with Product Catalog Microservice on Windows

REM Function to display help
:show_help
echo Usage: docker_operations.bat [COMMAND]
echo Commands:
echo   build       Build Docker image
echo   start       Start all services with Docker Compose
echo   stop        Stop all services
echo   restart     Restart all services
echo   logs        Show logs from all services
echo   ps          Show running containers
echo   clean       Remove all containers and volumes
echo   help        Display this help message
echo.
echo Examples:
echo   docker_operations.bat build
echo   docker_operations.bat start
echo   docker_operations.bat logs
goto :eof

REM Check if Docker is installed
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Docker is not installed. Please install Docker first.
    exit /b 1
)

REM Check if Docker Compose is installed
docker-compose --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Docker Compose is not installed. Please install Docker Compose first.
    exit /b 1
)

REM Parse command
if "%~1"=="" (
    call :show_help
    exit /b 1
)

if "%~1"=="build" (
    echo Building Docker image...
    docker build -t product-catalog-service .
    echo Docker image built successfully.
    exit /b 0
)

if "%~1"=="start" (
    echo Starting services with Docker Compose...
    docker-compose up -d
    echo Services started. The API is available at http://localhost:8080
    exit /b 0
)

if "%~1"=="stop" (
    echo Stopping services...
    docker-compose down
    echo Services stopped.
    exit /b 0
)

if "%~1"=="restart" (
    echo Restarting services...
    docker-compose down
    docker-compose up -d
    echo Services restarted. The API is available at http://localhost:8080
    exit /b 0
)

if "%~1"=="logs" (
    echo Showing logs from all services...
    docker-compose logs -f
    exit /b 0
)

if "%~1"=="ps" (
    echo Showing running containers...
    docker-compose ps
    exit /b 0
)

if "%~1"=="clean" (
    echo Removing all containers and volumes...
    docker-compose down -v
    echo Containers and volumes removed.
    exit /b 0
)

if "%~1"=="help" (
    call :show_help
    exit /b 0
)

echo Unknown command: %~1
call :show_help
exit /b 1
