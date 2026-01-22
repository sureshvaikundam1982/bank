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

Prerequisites
- Java 17 (or compatible JDK 17)
- Maven 3.6+ (or use your IDE built-in run)
- Git
- Ports 8761, 8081 and 8080 free on localhost

1) Clone the repository
```bash
git clone https://github.com/sureshvaikundam1982/bank.git
cd bank
```

2) Run with Maven (development, fast)
You can run each service in its own terminal.

Start Eureka Server
```bash
cd eureka-server
# Option A: run from Maven directly (dev)
mvn spring-boot:run
# OR Option B: build and run the jar
# mvn clean package -DskipTests
# java -jar target/eureka-server-0.0.1-SNAPSHOT.jar
```

Start Bank Service
```bash
# new terminal
cd bank-service
mvn spring-boot:run
# OR build and run jar:
# mvn clean package -DskipTests
# java -jar target/bank-service-0.0.1-SNAPSHOT.jar
```

Start Bank Client
```bash
# new terminal
cd bank-client
mvn spring-boot:run
# OR build and run jar:
# mvn clean package -DskipTests
# java -jar target/bank-client-0.0.1-SNAPSHOT.jar
```

3) Run with jar files (alternative)
- If you prefer packages first:
```bash
# from repo root
mvn -pl eureka-server -am clean package -DskipTests
mvn -pl bank-service -am clean package -DskipTests
mvn -pl bank-client -am clean package -DskipTests

# then run jars (adjust filenames if version changed)
java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar
java -jar bank-service/target/bank-service-0.0.1-SNAPSHOT.jar
java -jar bank-client/target/bank-client-0.0.1-SNAPSHOT.jar
```

4) Verify the services
- Eureka dashboard: http://localhost:8761
  - After starting Eureka, open the dashboard.
  - Once bank-service and bank-client are running they will show as registered instances.

- Direct bank-service endpoint:
  - http://localhost:8081/info
  - Response should be: Welcome to Bank Service (local config)

- Client endpoint (via Feign + Eureka + Resilience4j):
  - http://localhost:8080/bank-info
  - Response should be the same message returned by bank-service.

5) Test Resilience / Circuit-breaker fallback
- With all services running:
  - GET http://localhost:8080/bank-info → returns bank-service message.

- To trigger fallback:
  1. Stop the bank-service process (Ctrl+C in its terminal).
  2. Immediately call: GET http://localhost:8080/bank-info
  3. You should receive the fallback response:
     - Example: "Fallback: bank service unavailable (FeignException or ConnectException…)"
- Note: Resilience4j circuit-breaker settings in bank-client/application.yml use default slidingWindow and threshold; you may need multiple failed calls to open the circuit depending on config.

6) Useful commands and tips
- Change server port when running:
  - Pass as command-line property:
    mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8090"
  - Or with jar:
    java -jar target/app.jar --server.port=8090

- View logs
  - Use the terminal where each service is running. Search logs for registration messages:
    - e.g., bank-service logs: "Registered instance ..." or "STARTED"
  - tail logs: tail -f path/to/logfile (if you configure a file)

- If a client cannot find a service:
  1. Check Eureka dashboard (http://localhost:8761) to confirm registration name is `bank-service`.
  2. Check bank-service logs for any startup errors.
  3. Ensure eureka.client.serviceUrl.defaultZone in application.yml points to http://localhost:8761/eureka
  4. Verify network port availability.

- Increase Feign/HTTP client logging (debug)
  - Add to bank-client src/main/resources/application.yml (temporarily):
    logging:
      level:
        org.springframework.cloud.openfeign: DEBUG
        feign: DEBUG

7) Running in an IDE
- Import the three Maven projects into IntelliJ/Eclipse as separate projects or as modules.
- Run the main class in each:
  - eureka-server: com.example.eureka.EurekaServerApplication
  - bank-service: com.example.bankservice.BankServiceApplication
  - bank-client: com.example.bankclient.BankClientApplication

8) Docker / docker-compose (optional)
- I can create Dockerfiles and a docker-compose.yml to run all three containers and bring them up with docker-compose up -d. Tell me if you want the docker-compose and I’ll add it.

9) Next actions I can do for you
- Add Dockerfiles + docker-compose
- Convert to a multi-module Maven project
- Add GitHub Actions for CI
- Add simple unit tests or integration tests
- Add TLS or security between services

If you want the Docker/docker-compose files or a single script to run all services locally, tell me and I’ll provide them.