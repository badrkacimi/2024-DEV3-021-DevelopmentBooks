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

Available books Ids range: 1-5:

```
Id 1 [Clean Code (Robert Martin, 2008)]
Id 2 [The Clean Coder (Robert Martin, 2011)]
Id 3 [Clean Architecture (Robert Martin, 2017)]
Id 4 [Test Driven Development by Example (Kent Beck, 2003)]
Id 5 [Working Effectively With Legacy Code (Michael C. Feathers, 2004)]
```


# Visualize & interact with the API's endpoints :

URL: http://localhost:8080/swagger-ui/index.html