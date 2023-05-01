# Money Transfer App

This is a simple money-transfer microservice that allows users to transfer money between accounts. It is built using Spring Boot and Spring Data JDBC.
It exposes two public endpoints:
- GET /accounts/{id} - returns the account with the given id
- POST /accounts/transfer - transfers money between two accounts

## Setting up
The database powering the application is MySQL. It is configured to run in a docker container.  
The schema includes two tables: `account` and `transfer`.  
An initialization script is located in the `docker` directory, along with the docker-compose config.
It creates the two aforementioned tables and two test accounts that are used in integration tests.

To run the mysql container, execute from the root dir of the project:  
`docker-compose -f docker/docker-compose.yml up -d`

To connect to the database from the host:  
`mysql -uroot -p -h localhost -P 3306 --protocol=tcp`  
The password is defined in the docker-compose file.

To bring the container down, discarding all data:  
`docker-compose -f docker/docker-compose.yml down --remove-orphans`

Once the DB has been initialized successfully, the app can be run from the IDE or from the command line using maven:  
`mvn spring-boot:run`

Some example HTTP requests that can be accepted from the app:
```
GET http://localhost:8080/account/1
```

```
POST http://localhost:8080/transfer
Content-Type: application/json
{
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "amount": "1000.00",
  "currency": "JPY"
}
```

## Testing
The app includes unit and integration tests, using JUnit and Cucumber respectively.
To run the unit tests, execute from the root dir of the project:  
`mvn clean test`

To run the integration tests, make sure the MySQL container is up and running and execute:  
```
mvn exec:java \
    -Dexec.classpathScope=test \
    -Dexec.mainClass=io.cucumber.core.cli.Main \
    -Dexec.args="src/test/resources/Feature --glue com.agileactors.moneytransfer.cucumber"
```
In order to facilitate the testing process, a small shell script has been added to the project.
It runs the DB container, builds the app, runs the integration tests and brings down the container.
To run it, execute from the root dir of the project:  
`./build.sh`

## Dependencies
Except for the Spring Boot dependencies that came from Spring Initializr:
```
spring-boot-starter-web
spring-boot-starter-data-jdbc
spring-boot-starter-test
```
the app uses the following libraries:
```
mysql-connector-j
hibernate-validator
cucumber
```

## Shortcomings
The app is only able to read an account from the DB and transfer money between two accounts.  
It does not expose any endpoints for creating/deleting accounts or depositing/withdrawing money for a single account.  
For testing purposes the accounts get pre-populated with money using jdbc.

The app mocks the currency conversion service with a util class, so it uses hard-coded exchange rates.

The app includes a RestControllerAdvice that handles exceptions and returns a predefined error response.
However, the RestControllerAdvice handles only the `Exception` superclass and returns the thrown exception's message and an HTTP status of 409.  
For the business exceptions that are defined in the Acceptance criteria this is enough,
but in some cases the exception returned to the client might leak internal information.  
This should be improved by adding more specific local Exception classes and exception handlers.  
Along with the exception message, a uuid string is returned which can be used to narrow the log investigation should the need arise.

Another shortcoming is that the database password is hardcoded in the docker-compose file and application.properties.  
The id field of the two tables is created as an int, which should hold enough values for the purposes of the present implementation.

The code has been written with readability and maintainability in mind, but there are no javadocs.
