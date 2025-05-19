-- Create test database if it doesn't exist
CREATE DATABASE IF NOT EXISTS product_catalog_test;

-- Connect to the test database
\c product_catalog_test;

-- Create schema if needed
-- CREATE SCHEMA IF NOT EXISTS public;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE product_catalog_test TO postgres;
