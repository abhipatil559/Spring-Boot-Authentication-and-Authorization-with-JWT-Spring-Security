
# Spring JWT Authorization API

## Overview
This project is a RESTful API built with Spring Boot 3.3.2, focusing on JWT-based authorization and authentication. It manages user roles (BIDDER and APPROVER) to secure endpoints, providing a robust and scalable solution for managing bidding data.

## Features
- **JWT-based Authentication**: Secure token-based authentication using JWTs.
- **Role-Based Access Control**: Endpoints secured for BIDDER and APPROVER roles.
- **CRUD Operations**: Full create, read, update, and delete operations for bidding data.

## Technologies Used
- **Spring Boot 3.3.2**
- **Spring Security**
- **JWT (JSON Web Token)**
- **H2 Database** (or your preferred database)

## Endpoints

### 1. **POST /login**
Authenticate users and issue a JWT token.
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "yourpassword"
  }
  ```
- **Success Response:**
  ```json
  {
    "jwt": "your_jwt_token",
    "status": 200
  }
  ```

### 2. **POST /bidding/add**
Add a new bidding record (accessible only by BIDDER).
- **Request Body:**
  ```json
  {
    "biddingId": 2608,
    "bidAmount": 14000000.0,
    "yearsToComplete": 2.6
  }
  ```
- **Success Response:**
  ```json
  {
    "id": 1,
    "biddingId": 2608,
    "projectName": "Metro Phase V 2024",
    "bidAmount": 1.4E7,
    "yearsToComplete": 2.6,
    "dateOfBidding": "07/07/2023",
    "status": "pending",
    "bidderId": 1
  }
  ```

### 3. **GET /bidding/list**
Retrieve all bidding records above a certain bid amount (accessible by both roles).
- **Request Param:**
    - `bidAmount`: The minimum bid amount to filter results.

### 4. **PATCH /bidding/update/{id}**
Update the status of a bidding (accessible only by APPROVER).
- **Request Body:**
  ```json
  {
    "status": "approved"
  }
  ```
- **Success Response:**
  ```json
  {
    "id": 1,
    "biddingId": 2608,
    "projectName": "Metro Phase V 2024",
    "bidAmount": 1.4E7,
    "yearsToComplete": 2.6,
    "dateOfBidding": "07/07/2023",
    "status": "approved",
    "bidderId": 1
  }
  ```

### 5. **DELETE /bidding/delete/{id}**
Delete a bidding record (accessible by APPROVER or BIDDER who created it).
- **Success Response:**
  ```
  "deleted successfully"
  ```

## Setup Instructions

### Prerequisites
- JDK 17+
- Maven
- IDE (e.g., IntelliJ IDEA or Eclipse)

### Running the Application
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/spring-jwt-authorization.git
   cd spring-jwt-authorization
   ```

2. **Install Dependencies:**
   ```bash
   mvn install
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```



### Testing
Run the tests using Maven:
```bash
mvn clean test
```

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.


