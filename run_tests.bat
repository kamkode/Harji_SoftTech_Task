@echo off
REM Script to run tests for Product Catalog Microservice on Windows

echo Running tests for Product Catalog Microservice...

REM Function to display help
:show_help
echo Usage: run_tests.bat [OPTIONS]
echo Options:
echo   -a, --all       Run all tests (unit and integration)
echo   -u, --unit      Run only unit tests
echo   -i, --int       Run only integration tests
echo   -c, --coverage  Generate test coverage report
echo   -h, --help      Display this help message
echo.
echo Examples:
echo   run_tests.bat -a        # Run all tests
echo   run_tests.bat -u -c     # Run unit tests with coverage report
goto :eof

REM Default values
set run_unit=false
set run_integration=false
set generate_coverage=false

REM Parse command line arguments
:parse_args
if "%~1"=="" goto check_args
if "%~1"=="-a" (
    set run_unit=true
    set run_integration=true
    shift
    goto parse_args
)
if "%~1"=="--all" (
    set run_unit=true
    set run_integration=true
    shift
    goto parse_args
)
if "%~1"=="-u" (
    set run_unit=true
    shift
    goto parse_args
)
if "%~1"=="--unit" (
    set run_unit=true
    shift
    goto parse_args
)
if "%~1"=="-i" (
    set run_integration=true
    shift
    goto parse_args
)
if "%~1"=="--int" (
    set run_integration=true
    shift
    goto parse_args
)
if "%~1"=="-c" (
    set generate_coverage=true
    shift
    goto parse_args
)
if "%~1"=="--coverage" (
    set generate_coverage=true
    shift
    goto parse_args
)
if "%~1"=="-h" (
    call :show_help
    exit /b 0
)
if "%~1"=="--help" (
    call :show_help
    exit /b 0
)
echo Unknown option: %~1
call :show_help
exit /b 1

:check_args
REM If no options specified, show help
if "%run_unit%"=="false" (
    if "%run_integration%"=="false" (
        echo No test type specified.
        call :show_help
        exit /b 1
    )
)

REM Run unit tests
if "%run_unit%"=="true" (
    echo Running unit tests...
    if "%generate_coverage%"=="true" (
        call mvn test -Dtest="*Test" jacoco:report
    ) else (
        call mvn test -Dtest="*Test"
    )
    
    REM Check if tests passed
    if %ERRORLEVEL% NEQ 0 (
        echo Unit tests failed!
        exit /b 1
    ) else (
        echo Unit tests passed!
    )
)

REM Run integration tests
if "%run_integration%"=="true" (
    echo Running integration tests...
    if "%generate_coverage%"=="true" (
        call mvn test -Dtest="*IntegrationTest" jacoco:report
    ) else (
        call mvn test -Dtest="*IntegrationTest"
    )
    
    REM Check if tests passed
    if %ERRORLEVEL% NEQ 0 (
        echo Integration tests failed!
        exit /b 1
    ) else (
        echo Integration tests passed!
    )
)

REM Display coverage report if generated
if "%generate_coverage%"=="true" (
    echo Test coverage report generated at: target/site/jacoco/index.html
    
    REM Try to open the coverage report in the default browser
    start "" "target\site\jacoco\index.html"
)

echo Test execution complete!
exit /b 0
