# Microservices Project

This project demonstrates building robust microservices management using Spring Cloud Gateway, brokers, 
circuit breakers, service discovery, tracing, and monitoring. It includes services for product management, 
order processing, inventory management, and notification handling, all secured with Keycloak.
The architecture leverages asynchronous and synchronous communication, service discovery, and distributed tracing to 
ensure scalability, reliability, and observability.

### Key Components:
- **API Gateway:** Handles authentication, rate limiting, and routing to backend services.
- **Product Service:** Manages product-related operations.
- **Order Service:** Handles order processing with a circuit breaker for resilience.
- **Inventory Service:** Manages inventory with synchronous communication to the Order Service.
- **Notification Service:** Sends notifications using asynchronous communication via Kafka.
- **Service Discovery:** Enables dynamic service registration and discovery.
- **Distributed Tracing System:** Provides observability across microservices.

## Prerequisites

- Docker
- Docker Compose

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/HabtamuTesfie/microservices.git
cd microservices
docker-compose up -d
```
**Run the following command to check the status of each container.**
```sh
docker-compose ps 
```

### Keycloak Setup
 **Access Keycloak**

- URL: [http://localhost:8080](http://localhost:8080)
- Default credentials:
    - Username: `admin`
    - Password: `admin`
- **Create a Realm**
    1. Go to the top left of the dashboard and hover over "Master".
    2. Click "Add realm".
    3. Name the realm `spring-boot-microservices-realm`.
    4. Click "Create".

- **Create a Client**
    1. Navigate to "Realm Settings" > "Clients".
    2. Click "Create".
    3. Enter `spring-cloud-client` as the Client ID.
    4. Click "Save".

- **Configure the Client**
    - Change "Access Type" to **Confidential**.
    - Disable **Standard Flow Enabled** and **Direct Access Grants Enabled**.
    - Enable **Service Accounts Enabled**.
    - Click "Save".

- **Get Client Secret**
    1. Go to the "Credentials" tab.
    2. Copy the Secret for future use in OAuth2 requests.

**Postman Configuration**

- **Common Configuration**
    1. Open Postman and create a new request.
    2. Set the request type according to the endpoint (GET, POST, etc.).

- **Configure Authorization**
    1. Click on the Authorization tab.
    2. Select **OAuth 2.0** from the Type dropdown.
    3. Set the "Add authorization data to" field to **Request Headers**.

- **Configure the OAuth 2.0 Token**
    - Fill in the following details:
        - Token Name: **sample**
        - Grant Type: **Client Credentials**
        - Access Token URL: `http://keycloak:8080/realms/spring-boot-microservices-realm/protocol/openid-connect/token`
        - Client ID: `spring-cloud-client`
        - Client Secret: Use the previously saved secret
        - Scope: **openid offline_access**
        - Client Authentication: Send as **Basic Auth header**.
    - Click on "Get New Access Token".
    - Click on "Proceed".
    - Click on "Use Token".

**Endpoints**

1. **Create Product**
    - **Method:** POST
    - **URL:** `http://localhost:8181/api/product`
    - **Body:**
      ```json
      {
        "name": "Product A",
        "description": "Product A description",
        "price": 1000000
      }
      ```

2. **Get Product**
    - **Method:** GET
    - **URL:** `http://localhost:8181/api/product/{id}`

3. **Create Order**
    - **Method:** POST
    - **URL:** `http://localhost:8181/api/order`
    - **Body:**
      ```json
      {
        "productId": "1",
        "quantity": 2,
        "price": 2000000
      }
      ```

4. **Get Order**
    - **Method:** GET
    - **URL:** `http://localhost:8181/api/order/{id}`

**Documentation**

For detailed documentation, including the local development procedure, please visit [Implementing Robust Microservices](https://scalable-microservices-implementation.blogspot.com/2024/06/implementing-robust-microservices.html).

