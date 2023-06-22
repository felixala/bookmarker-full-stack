### Prerequisites
- JDK 1.17 or later
- Maven 3 or later
- Postgres

### Technologies
- Spring Boot DevTools
- Spring Boot Actuator
- Spring Configuration Processor
- Spring Data JPA
- Maven
- Testcontainers
- Lombok
- PostgresSQL
- Flywaydb
- H2 Database
- Validation

### Rest API Guidelines
```
GET     /api/bookmarks                  ->     get all
GET     /api/bookmarks/{id}             ->     get by id
GET     /api/bookmarks?query=k&page=2   ->     search
POST    /api/bookmarks                  ->     create
PUT     /api/bookmarks                  ->     replace by id
PATCH   /api/bookmarks                  ->     partial update by id
DELETE  /api/bookmarks                  ->     delete by id
```