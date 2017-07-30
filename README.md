Stock Tracker Demo
==================

Write Me!

## How to start server

Requires maven:

```bash
mvn clean sprint-boot:run
```

Alternatively, you can build the package and run the jar directly:

```
mvn clean package
java -jar target/stock-tracker-service-0.1.0.jar 
```

## How to start the web UI

The REST API only has CORS configured for local web browsers served from port 9000. 
Launch one service using the jar method above and another service listening on port 9000:

```
mvn sprint-boot:run -Dserver.port=9000
```

Now navigate to localhost:9000, you should be seeing a standard Bootstrap web UI.

##Todo:

* Security:Basic Auth or OAuth
* Pagination?
* Possibly add in HATEOAS
* Clean up Readme
* Controller needs a test
* Display of prices needs a consistent format
* Web Site needs dedicated grunt build
* ~~Implement to the API specification~~
* ~~Split data layer from API pojos~~
* ~~Fix display of Timestamp on return json~~
* ~~Handle concurrent access of the dao layer, especially Update~~
