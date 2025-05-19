#!/bin/bash
# Script to run tests for Product Catalog Microservice

echo "Running tests for Product Catalog Microservice..."

# Function to display help
show_help() {
    echo "Usage: ./run_tests.sh [OPTIONS]"
    echo "Options:"
    echo "  -a, --all       Run all tests (unit and integration)"
    echo "  -u, --unit      Run only unit tests"
    echo "  -i, --int       Run only integration tests"
    echo "  -c, --coverage  Generate test coverage report"
    echo "  -h, --help      Display this help message"
    echo ""
    echo "Examples:"
    echo "  ./run_tests.sh -a        # Run all tests"
    echo "  ./run_tests.sh -u -c     # Run unit tests with coverage report"
}

# Default values
run_unit=false
run_integration=false
generate_coverage=false

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        -a|--all)
            run_unit=true
            run_integration=true
            shift
            ;;
        -u|--unit)
            run_unit=true
            shift
            ;;
        -i|--int)
            run_integration=true
            shift
            ;;
        -c|--coverage)
            generate_coverage=true
            shift
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

# If no options specified, show help
if [[ "$run_unit" == "false" && "$run_integration" == "false" ]]; then
    echo "No test type specified."
    show_help
    exit 1
fi

# Run unit tests
if [[ "$run_unit" == "true" ]]; then
    echo "Running unit tests..."
    if [[ "$generate_coverage" == "true" ]]; then
        mvn test -Dtest="*Test" jacoco:report
    else
        mvn test -Dtest="*Test"
    fi
    
    # Check if tests passed
    if [[ $? -ne 0 ]]; then
        echo "Unit tests failed!"
        exit 1
    else
        echo "Unit tests passed!"
    fi
fi

# Run integration tests
if [[ "$run_integration" == "true" ]]; then
    echo "Running integration tests..."
    if [[ "$generate_coverage" == "true" ]]; then
        mvn test -Dtest="*IntegrationTest" jacoco:report
    else
        mvn test -Dtest="*IntegrationTest"
    fi
    
    # Check if tests passed
    if [[ $? -ne 0 ]]; then
        echo "Integration tests failed!"
        exit 1
    else
        echo "Integration tests passed!"
    fi
fi

# Display coverage report if generated
if [[ "$generate_coverage" == "true" ]]; then
    echo "Test coverage report generated at: target/site/jacoco/index.html"
    
    # Try to open the coverage report in the default browser
    if command -v xdg-open >/dev/null 2>&1; then
        xdg-open target/site/jacoco/index.html
    elif command -v open >/dev/null 2>&1; then
        open target/site/jacoco/index.html
    elif command -v start >/dev/null 2>&1; then
        start target/site/jacoco/index.html
    else
        echo "Please open the coverage report manually."
    fi
fi

echo "Test execution complete!"
