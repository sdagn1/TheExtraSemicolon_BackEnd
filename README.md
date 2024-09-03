# The Extra Semicolon Backend Application Set Up

Database Migration - Test
---

1. Add your SQL script to `resources.db.migration` directory
2. Add the following lines to your ~/.zshrc file:

```
export DB_HOST="jdbc:mysql://YOUR_DB_HOST/YOUR_DB_NAME"
export DB_USERNAME="YOUR_DB_USERNAME"
export DB_PASSWORD="YOUR_DB_PASSWORD"
export DB_NAME="YOUR_DATABASE_NAME"

export FLYWAY_URL="jdbc:mysql://YOUR_DB_HOST/YOUR_DB_NAME"
export FLYWAY_USER="YOUR_DB_USERNAME"
export FLYWAY_PASSWORD="YOUR_DB_PASSWORD"
export FLYWAY_BASELINE_ON_MIGRATE=true
```

3. Reload your terminal session if required:

```
. ~/.zshrc
```

4. Run Flyway commands through Maven:

```
mvn flyway:clean
mvn flyway:migrate
```

Add Linter to your application
---
1. Copy sun_checks_modified.xml file to 'src/main/resources'
2. Add maven checkstyle plugin to your 'pom.xml' file
3. In your terminal run 'mvn checkstyle:check'
   ```
   mvn checkstyle:check
   ```
4. Install 'CheckStyle-IDEA' plugin in IntelliJ
5. To add your checkstyle configuration
   1. In IntelliJ settings,go to tools, then go to 'Editor' 
   2. Then 'CodeStyle'
   3. Select Settings icon next to 'Scheme' dropdown
   4. 'Import Scheme'
   5. Select Checkstyle configuration
   6. Add the xml file you added in step 1 then open.
6. Apply the configuration file
   1. In IntelliJ Settings go to Tools
   2. Then 'CheckStyle'
   3. Click + under Configuration File, make the description 'Sun Checks Modifed'
   4. Browse, then select the xml file -> next -> next -> finish
   5. Check the 'Sun Checks Modified' row -> Apply -> Ok
7. Linter should have been applied

How to start the test application
---

1. Set the following environment variables:
    1. DB_USERNAME
    2. DB_PASSWORD
    3. DB_HOST
    4. DB_NAME
2. Run `mvn clean install` to build your application
3. To check that your application is running enter url `http://localhost:8080/api/test`

How to run the Application locally
---
1. Run `TestApplication.main()`
2. Go to `http://localhost:8080`to check if it runs locally

How to run the application in Docker Locally and AWS
---
Run the following commands run the application to run the application in Docker. The dockerfiles have already been set up. The command below runs docker locally. Check that it is running at https://localhost:8080/api/test
```
docker build --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t myapp:0.1 .
docker run -p 8080:8080 myapp:0.1
```

The command below runs docker and connects to the AWS link.
```
docker build --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t 5tmqdqfjni.eu-west-1.awsapprunner.com/employee_demo:the_extra_semicolon_be .
docker run -p 8080:8080 5tmqdqfjni.eu-west-1.awsapprunner.com/employee_demo:the_extra_semicolon_be
```
Go to : https://5tmqdqfjni.eu-west-1.awsapprunner.com/swagger# to check that it is running.

To see the AWS link
---
Go to https://5tmqdqfjni.eu-west-1.awsapprunner.com/ + the API you want to view 
For example : https://5tmqdqfjni.eu-west-1.awsapprunner.com/api/test to view the test data

Run the Tests
---
1. To run the unit tests, run `mvn clean test`
2. To run the integration tests, run `mvn clean integration-test`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

How to Run in Docker
---

Locally:

1. ```docker build --build-arg DB_VAR=${DB_VAR} --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t myapp:0.1 .```
2. ```docker run -p 8080:8080 myapp:0.1```

On AWS:

1. ```docker build --build-arg DB_VAR=${DB_VAR} --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} -t 067502745215.dkr.ecr.eu-west-1.amazonaws.com/employee_demo:the_extra_semicolon_be .```
2. ```docker run -p 8080:8080 067502745215.dkr.ecr.eu-west-1.amazonaws.com/employee_demo:the_extra_semicolon_be```