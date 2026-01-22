# Bank microservices (Eureka + Feign + Resilience4j)

This repository contains three independent Spring Boot microservices:

- eureka-server — service registry (port 8761)
- bank-service — backend service (port 8081)
- bank-client — client using OpenFeign + Resilience4j circuit breaker (port 8080)

Run order:
1. Start eureka-server
2. Start bank-service
3. Start bank-client

Test:
- GET http://localhost:8080/bank-info — client calls bank-service via Eureka + Feign
- Stop bank-service and call again to observe circuit-breaker fallback.

Notes:
- Java 17
- Spring Boot 3.x, Spring Cloud 2022.x