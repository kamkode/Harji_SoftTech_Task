# Product Catalog API Testing Guide

This guide provides comprehensive instructions for testing the Product Catalog Microservice API. It includes detailed examples for all endpoints, expected responses, and troubleshooting tips.

## Overview

The Product Catalog API allows you to:
- Create new products
- Update existing products
- Retrieve products by ID
- List all products

When products are created or updated, the service publishes events that can be consumed by other services.

## Prerequisites

- The Product Catalog service is running on `http://localhost:8080`
- You have access to a terminal (for curl commands) or Postman

## Testing Methods

You can test the API using one of these methods:

1. **Curl Commands**: Use the commands provided below in a terminal
2. **Postman**: Import the provided Postman collection
3. **Swagger UI**: Access the interactive documentation at `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

The Product Catalog API provides the following endpoints:

- `POST /products` - Create a new product
- `GET /products` - List all products
- `GET /products/{id}` - Get a product by ID
- `PUT /products/{id}` - Update an existing product

## Curl Commands for Testing

### 1. Create Products

#### Create a Smartphone
```bash
curl -X POST "http://localhost:8080/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X1",
    "description": "Latest smartphone with advanced features",
    "category": "Electronics",
    "price": 799.99,
    "availableStock": 50
  }'
```

#### Create a Laptop
```bash
curl -X POST "http://localhost:8080/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "UltraBook Pro",
    "description": "Lightweight laptop with 16GB RAM and 512GB SSD",
    "category": "Computers",
    "price": 1299.99,
    "availableStock": 25
  }'
```

#### Create Headphones
```bash
curl -X POST "http://localhost:8080/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Noise-Cancelling Headphones",
    "description": "Premium wireless headphones with active noise cancellation",
    "category": "Audio",
    "price": 249.99,
    "availableStock": 100
  }'
```

#### Create a Smart Watch
```bash
curl -X POST "http://localhost:8080/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smart Watch Series 5",
    "description": "Fitness tracker with heart rate monitoring and GPS",
    "category": "Wearables",
    "price": 349.99,
    "availableStock": 75
  }'
```

#### Create a Camera
```bash
curl -X POST "http://localhost:8080/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Digital SLR Camera",
    "description": "Professional camera with 24MP sensor and 4K video",
    "category": "Photography",
    "price": 899.99,
    "availableStock": 15
  }'
```

### 2. Get All Products
```bash
curl -X GET "http://localhost:8080/products"
```

### 3. Get Product by ID
```bash
# Replace {id} with the actual product ID from a previous create response
curl -X GET "http://localhost:8080/products/{id}"
```

### 4. Update a Product
```bash
# Replace {id} with the actual product ID from a previous create response
curl -X PUT "http://localhost:8080/products/{id}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X1 Pro",
    "description": "Updated version with improved camera",
    "category": "Electronics",
    "price": 899.99,
    "availableStock": 30
  }'
```

## Testing with Postman

### Import the Collection

1. Open Postman
2. Click on "Import" in the top left
3. Select "Raw text"
4. Copy and paste the following JSON:

```json
{
  "info": {
    "name": "Product Catalog API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Create Smartphone",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Smartphone X1\",\n  \"description\": \"Latest smartphone with advanced features\",\n  \"category\": \"Electronics\",\n  \"price\": 799.99,\n  \"availableStock\": 50\n}"
        }
      }
    },
    {
      "name": "Create Laptop",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"UltraBook Pro\",\n  \"description\": \"Lightweight laptop with 16GB RAM and 512GB SSD\",\n  \"category\": \"Computers\",\n  \"price\": 1299.99,\n  \"availableStock\": 25\n}"
        }
      }
    },
    {
      "name": "Create Headphones",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Noise-Cancelling Headphones\",\n  \"description\": \"Premium wireless headphones with active noise cancellation\",\n  \"category\": \"Audio\",\n  \"price\": 249.99,\n  \"availableStock\": 100\n}"
        }
      }
    },
    {
      "name": "Get All Products",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products"]
        }
      }
    },
    {
      "name": "Get Product by ID",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/products/{{productId}}",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products", "{{productId}}"]
        }
      }
    },
    {
      "name": "Update Product",
      "request": {
        "method": "PUT",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/products/{{productId}}",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products", "{{productId}}"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Smartphone X1 Pro\",\n  \"description\": \"Updated version with improved camera\",\n  \"category\": \"Electronics\",\n  \"price\": 899.99,\n  \"availableStock\": 30\n}"
        }
      }
    }
  ]
}
```

5. Click "Import"

### Using the Collection

1. After creating a product, copy the returned product ID (UUID)
2. In Postman, click on "Variables" at the collection level
3. Add a variable named "productId" and paste the UUID as the value
4. Save the collection
5. Now you can run the "Get Product by ID" and "Update Product" requests

## Testing with Swagger UI

1. Open your browser and navigate to: `http://localhost:8080/swagger-ui/index.html`
2. You'll see all the available endpoints with documentation
3. Click on an endpoint to expand it
4. Click "Try it out"
5. Fill in the required parameters
6. Click "Execute"
7. View the response

## Expected Responses

### Create Product (POST /products)
```json
{
  "productId": "a UUID value",
  "name": "Smartphone X1",
  "description": "Latest smartphone with advanced features",
  "category": "Electronics",
  "price": 799.99,
  "availableStock": 50,
  "lastUpdated": "2025-05-19T12:34:56.789Z"
}
```

### Get All Products (GET /products)
```json
[
  {
    "productId": "a UUID value",
    "name": "Smartphone X1",
    "description": "Latest smartphone with advanced features",
    "category": "Electronics",
    "price": 799.99,
    "availableStock": 50,
    "lastUpdated": "2025-05-19T12:34:56.789Z"
  },
  {
    "productId": "another UUID value",
    "name": "UltraBook Pro",
    "description": "Lightweight laptop with 16GB RAM and 512GB SSD",
    "category": "Computers",
    "price": 1299.99,
    "availableStock": 25,
    "lastUpdated": "2025-05-19T12:35:56.789Z"
  }
]
```

### Get Product by ID (GET /products/{id})
```json
{
  "productId": "the requested UUID",
  "name": "Smartphone X1",
  "description": "Latest smartphone with advanced features",
  "category": "Electronics",
  "price": 799.99,
  "availableStock": 50,
  "lastUpdated": "2025-05-19T12:34:56.789Z"
}
```

### Update Product (PUT /products/{id})
```json
{
  "productId": "the updated UUID",
  "name": "Smartphone X1 Pro",
  "description": "Updated version with improved camera",
  "category": "Electronics",
  "price": 899.99,
  "availableStock": 30,
  "lastUpdated": "2025-05-19T12:40:56.789Z"
}
```

## Troubleshooting

If you encounter any issues:

1. Ensure the application is running (check http://localhost:8080/actuator/health)
2. Verify that PostgreSQL is running and accessible
3. Check the application logs for any error messages
4. Make sure you're using the correct product ID in the URL for GET and PUT requests
