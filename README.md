# JPA Meta Model
This is a simple spring boot application that can search and sort an entity with pagination.

## Dependencies used
* Spring Data JPA
* Spring Boot Starter Validation
* Liquibase
* PostgreSQL JDBC Driver
* Lombok
* ModelMapper
* Hibernate Jpamodelgen

## Prerequisites
1. To run this project, you need to install;
* Java 8
* IDE like InteliJ idea
* pgAdmin
2. Create schema called "library"


## Features
* Search
* Sort
* Pagination

## Sample cURL

* View all data
```
curl --location --request GET 'http://localhost:8081/api/v1/books?
code=1&name=for&author=Gayan&start=0&limit=5&dataTable=true&draw=3'
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
