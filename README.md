# product-catalog
Product catalog test api. Application is a backend api made in Java 17 with Springboot.

This app uses Basic Authentication, for simplicity it has one programmed user:
```
Username: user
Password: password
```

This user and password need to be sent in the authorization header of every call.

All routes in this API are documented with swagger, when server is running it can be seen at:
```
http://localhost:8080/swagger-ui/index.html
```
Replace localhost:8080 with the address the server is running.

## How to run

To run the app, you need to install gradle, and then run:
```
./gradlew build
```

When that concludes, run:
```
./gradlew bootRun
```

The server will start on port 8080 by default.

If you want to run the unit tests, you can use:
```
./gradlew test
```

## Adjusting CORS

CORS was set up to allow connections by localhost:3000, the default address that the frontend uses.
If needed, adjust the accepted address in the application.properties file by editing the line:
```
cors.allowed-origins=http://localhost:3000,http://example.com
```
It supports multiple addresses' comma separated.


## Data Storage

This application uses H2 to store data, for simplicity, so there is no need to set up a database.
