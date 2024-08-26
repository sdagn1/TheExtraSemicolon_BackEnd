# Java DropWizard Flyway Starter

Database Migration - Local
---

1. Add your SQL script to `resources.db.migration` directory
2. Add the following lines to your ~/.zshrc file:

```
export FLYWAY_URL="jdbc:mysql://YOUR_DB_HOST/YOUR_DB_NAME"
export FLYWAY_USER="YOUR_DB_USERNAME"
export FLYWAY_PASSWORD="YOUR_DB_PASSWORD"
export FLYWAY_BASELINE_ON_MIGRATE=true
```

3. Reload your terminal session if required:

```
. ~/.zshrc
```

4. Run Flyway command through Maven:

```
mvn flyway:migrate
```

Database Migration - Production
---

1. Add following secrets to your Github repo:

```
DB_USERNAME - the prod db username
DB_PASSWORD - the prod db password
DB_HOST - the prod db host
DB_NAME - the prod db name
```

2. Raise a pull request with your script in the `resources.db.migration` directory
3. After approvals, merge pull request; this will trigger the migration action to run in Github
4. Ensure migration successfully runs against prod database

How to start the test application
---

1. Set the following environment variables:
    1. DB_USERNAME
    2. DB_PASSWORD
    3. DB_HOST
    4. DB_NAME
1. Run `mvn clean install` to build your application
1. You can start application via:
    1. Terminal: `java -jar target/java-swagger-flyway-starter-org.kainos.ea.jar server config.yml`
    2. IDE: Edit run configuration -> Add `server` to program arguments -> Run
1. To check that your application is running enter url `http://localhost:8080/api/test`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

How to Run in Docker
---

Locally:

1. ```docker build --build-arg DB_VAR=${DB_VAR} --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t myapp:0.1 .```
2. ```docker run -p 8080:8080 myapp:0.1```

On AWS:

1. ```docker build --build-arg DB_VAR=${DB_VAR} --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t 5tmqdqfjni.eu-west-1.awsapprunner.com/employee_demo:the_extra_semicolon_be``` .
2. ```docker run -p 8080:8080 5tmqdqfjni.eu-west-1.awsapprunner.com/employee_demo:the_extra_semicolon_be```