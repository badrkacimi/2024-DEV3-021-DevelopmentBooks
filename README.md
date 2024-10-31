# BADR KACIMI

* Java 21 Spring boot 3.3.5.RELEASE.
* TDD approach
* Docker for containerize the application.
* H2 as in-memory database.
* Lombok to minimize the boilerplate code

# Build project

To build the project, run the maven command

```
mvn clean install
```

# Run the project

```
docker build -t bookstore:latest .
docker run -p 8080:8080 bookstore:latest 
```

# Postman api collection :

You can import all the apis in Postman:

```
book-store.postman_collection.json
```

# Visualize & interact with the API's endpoints :

URL: http://localhost:8080/swagger-ui/index.html

* Database implementation is excluded for simplicity, as it’s not necessary for the kata requirements
