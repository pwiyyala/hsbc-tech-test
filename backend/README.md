# HSBC Tech Test

## Prerequisites
To build the application, ensure you have the following prerequisites installed:
- Java 17 or higher
- Maven 3.8 or higher

## Build and Run the Application

1. Navigate to the backend directory:
`cd backend`

2. Build the application using Maven:
`mvn clean verify -U`

3. Running the App
To run the application, follow these steps:

Start the Spring Boot application:
`mvn spring-boot:run`

The application will be accessible at http://localhost:8080/api/v1/cities/y.

## Further Improvements
1. Adding Caching
   Implement caching using Spring Cache or a distributed caching solution like ECache. This will improve performance by 
   reducing redundant n/w calls.

2. Circuit Breaker
   Integrate a circuit breaker pattern using Resilience4j to handle failures gracefully and improve system resilience.

3. API Gateway
   Introduce an API Gateway like Spring Cloud Gateway to manage routing, security, and monitoring for microservices.

4. OAuth 2.0
   Enhance security by implementing OAuth 2.0 for authentication and authorization. Use Spring Security with OAuth 2.0 to secure endpoints and manage user access.

These improvements will enhance scalability, reliability, and security of the application.