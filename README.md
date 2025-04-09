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
