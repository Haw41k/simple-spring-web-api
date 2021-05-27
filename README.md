# This is a simple web API implementation using Spring-Boot.

This is an application for working with customers and their orders.

The standard set of HTTP queries is used:

1. GET
2. POST
3. PUT
4. DELETE

JSON is used for data exchange.

* http://localhost:8080/api/persons/ - GET, POST
* http://localhost:8080/api/persons/{person-id} - GET, PUT, DELETE


* http://localhost:8080/api/orders/ - GET, POST
* http://localhost:8080/api/orders/{order-id} - GET, PUT, DELETE
* http://localhost:8080/api/orders/?person-id={person-id} - GET

H2 in memory DB is used for data storage. The database is not stored on the local
storage device and after the application is finished, the database will be deleted.
When the application is launched, demo data is loaded into the database.

For "persons" creation/editing, required:

* "telephone" - not null, unique
* "inn" - unique

For "orders" creation/editing, required:

* "personId" - not null

To test the work of the web API, there is a set of 18 tests.

use:

* `./mvnw spring-boot:run` - for run application
* `./mvnw clean test` - for tests
* `./mvnw clean package` - for compile application to JAR file
