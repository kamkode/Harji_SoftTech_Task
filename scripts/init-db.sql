-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS product_catalog;

-- Connect to the database
\c product_catalog;

-- Create schema if needed
-- CREATE SCHEMA IF NOT EXISTS public;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE product_catalog TO postgres;
