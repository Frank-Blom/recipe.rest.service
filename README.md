# Recipe rest service

Java REST Application for managing your favourite recipes.

## Installation

This app is a Spring Boot application using Maven. To run this you can build a jar file from the command line:

```bash
git clone https://github.com/Frank-Blom/recipe.rest.service.git
cd recipe.rest.service
./mvnw package
java -jar target/*.jar
```

Or run it from Maven with the Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run
```

You can try out the out with the help of swagger here: http://localhost:8080/swagger-ui.html

## Database configuration

This app uses a MySQL connection for data persistency. To use the app with a MySQL db you can configure the connection in the application.properties file. Create an empty schema with a uesr that has acces to it and fill these in in the application.properties file.
