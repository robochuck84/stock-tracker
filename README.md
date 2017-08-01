Stock Tracker Demo
==================

A simple service that provides CRUD (minus the delete) operations over stocks.

## API

* <strong>GET /api/stocks</strong> - retrieves the entire list of stocks currently managed by the service.
* <strong>GET /api/stocks/{id}</strong> - retrieve a single stock with the identifier {id}
* <strong>PUT /api/stocks/{id}</strong> - update a single stock with identifier {id}, the body should be a JSON document with a key for the currentPrice
   * Example   ```{"currentPrice":123.32}```
* <strong>POST /api/stocks</strong> - create a new stock, the body should be a JSON document with keys for name and current price
   * Example ```{"name":"FOO","currentPrice":12.32}```


## How to start server

Requires [Maven](https://maven.apache.org/):

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

## Future Improvements

* Need to add handling for multiple currencies.
* Multi-lingual support
* Security with OAuth.
* Pagination on the GET stocks api call, currently returns everything which could grow to an unmanageable size.
* Add HATEOAS controls for better discoverability.
* Web Site needs dedicated grunt build.
  * Currently can't run website as a standalone entity
  * Script files are passed in the clear, needs minification
* Refactor web to use React+Redux
  * Current simple javascript/jQuery approach doesn't evolve gracefully. 

