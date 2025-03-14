# Customer Service

Microservice part of Gym Management System, this service contains endpoints to register new customers, assign clubs and
validate emails in case there is a user who is already using it.

## Running Locally

### **Prerequisites**
To run the application locally, ensure you have the following:

- **MySQL** – Installed and running.
- **Java 17+** – Ensure you have Java Development Kit (JDK) installed.
- **Maven** – To build and run the project.
- **Docker (optional)** – If you prefer running MySQL in a container.
- **Discovery Service** – This microservice relies on a service discovery component. Ensure it is running before starting this service.
- **Membership Service** – This microservice relies on a membership service, since it is used to generate the UUID 
to create the customer. However, this logic is handled by the **orchestrator-service** so you must not call directly
this service or any other.

## Setup Instructions

### 1. Start the Discovery Service
Before running this service, you need to start the Discovery Service to enable service registration and discovery.

```sh
cd path/to/discovery-service
mvn spring-boot:run
```

### 2. Configure MySQL Database
If MySQL is installed locally, create a database:

```sql
CREATE DATABASE gymcustomerdb;
```
You can also use Docker to run MySQL in a container for a more isolated setup:

```bash
docker run --name gym-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=gym_management -p 3306:3306 -d mysql:8
```
This command will run a MySQL container with the database **gymcustomerdb** created and available on port 3306.

### 3. Configure Application Properties
Make sure the application’s database connection is correctly configured. Open src/main/resources/application.properties (or application.yml) and ensure the following configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gymcustomerdb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

If you're using Docker for MySQL, ensure the host is localhost or the IP address of your Docker container.

#### Important ####

The **DB_USERNAME** and **DB_PASSWORD** environment variables must be configured before running the application.
These values can be securely stored in:

- GitHub Secrets for secure CI/CD integration.
- Environment Variables in your development environment, such as IntelliJ IDEA or your operating system.


### 4. Start the Customer Service
Once the Discovery Service is up and running, navigate to the directory of the customer microservice:

```bash
cd path/to/customer-service
```
Run the following command to build and start the application:

```bash
mvn spring-boot:run
```
This will start the Customer Service, which should now be available on http://localhost:8083 (or any configured port).

### **Customer Service API Endpoints**

- **GET** `/api/customers`  
  Retrieve a list of all customers available.
- **GET** `/api/customers/{id}`  
  Get detailed information of a specific customer by its unique ID.
- **POST** `/api/customers/register`  
  Register a new customer.
- **POST** `/api/customers/assign-club`  
  Assigns a club to a new customers depending on the club name.
- **GET** `/api/customers/validate-email`  
  Validate the existence of an email, there can not be more than one user with the same email.

### **Troubleshooting**
1. Ensure that the Discovery Service is running before starting the Customer service.
2. Check that MySQL is correctly configured and the **gymcustomerdb** database exists.
3. If running MySQL via Docker, verify that the container is up and accessible.