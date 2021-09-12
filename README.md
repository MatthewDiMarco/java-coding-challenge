# java-coding-challenge
Simple demonstration of a Spring Boot RESTful API with an SQL database.

### Instructions

Please ensure your system has jdk-11 or later installed, then run the following:

```./gradlew build```

```./gradlew bootRun```

### API

The server should now be running on localhost, port 8080. The endpoints are described below:

- ```http://localhost:8080/api/names```
  - GET: returns a list of all names
  - POST: adds a list of names and postcodes from the request body (see test-data.json)
  

- ```http://localhost:8080/api/names/{nameId}```
  - GET: return a name and it's postcode by ID


- ```http://localhost:8080/api/names/{lowerPostcode}/{upperPostcode}```
  - GET: returns a list of names and char count containing postcodes within a range

### H2 Database

For easy access to the SQL database, go to:

- ```http://localhost:8080/api/h2```

Ensure the console is configured accordingly before connecting:

- ```Driver Class:``` ```org.h2.Driver```
- ```JDBC URL:``` ```jdbc:h2:file:./h2/java-code-challenge```
- ```User Name:``` ```username```
- ```Password:``` ```password```

