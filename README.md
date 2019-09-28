# OpenPayd Banking Application 
 
 ## Project Description 
 RESTful service that offers the following functions:
1.	Create a new client
2.	List all clients
3.	Get client details by ID
4.	Create a new client account
5.	Effect a transfer between 2 accounts
6.	List client accounts
7.	List account transactions

Calling the “create” service methods should return enough information for the generated entity to be retrieved at a later stage.

Data transported to and from the RESTful service should be done using JSON. Errors should be handled gracefully by returning the appropriate HTTP status codes.

To aid our (and your) testing it is required that you use Postman REST client (a Chrome extension) as a means to create test scenarios to consume the service. We would then need such test scenarios to be exported and submitted together with your solution.

## Model specification
### A client should have the following details:
•	Name
•	Surname
•	Primary address
o	Address line 1 - Address line 2 - City – Country
•	Secondary address
o	Address line 1 - Address line 2 - City - Country
 
### An account should have the following details:
•	Type (CURRENT or SAVINGS) • Balance
•	Balance status (DR or CR) • Date created

### A transaction should have the following details:
•	Debit account
•	Credit account
•	Amount
•	Message
•	Date created


## Additional notes

When effecting a transfer between two accounts, the necessary checks should be performed to ensure that the requested transfer is valid. It is also required that transfers are executed atomically, preventing concurrent transfers from interfering. A transfer is made up of two parts: a debit (DR) operation and a credit (CR) operation. Therefore, such a transfer should be stored as a transaction identifying the account that was debited and the account that was credited. Note that an account balance status can either be in debit (DR) or in credit (CR). An account with a debit balance will be increased by a debit operation and decreased by a credit operation. Conversely an account with a credit balance will be increased by a credit operation and decreased by a debit operation. Therefore, account balances can shift their balances from DR to CR and vice versa, depending on the transfer.


## How to start

    mvn clean install
    
To run the app execute:

    mvn exec:java
or

    java -jar /target/<filename>.jar

Refer console for port details 

## To Test: 

This Application is updated with Unit tests which tests multiple scenarios. 

## Create Request example :

### Create Account Request : 

{
  "accountType": "CURRENT",
  "balance": 0
}

Balance Type is automatically assumed as Credit for new accounts. 

### Create Client Request : 

    {
        "name": "",
        "surname": "",
        "primaryAddress": {
            "addressLine1": "",
            "addressLine2": "",
            "city": "",
            "country": ""
        },
        "secondaryAddress": {
            "addressLine1": "",
            "addressLine2": "",
            "city": "",
            "country": ""
        }
    }
	
###   Create Transaction Request : 

{
  "amount": 0,
  "creditAccountId": 0,
  "debitAccountId": 0,
  "message": "string"
}

## Lombok for Eclipse 
This application is using Lombok library for the model classes. The same might show errors in eclipse. Make sure that lombok is properly configured in eclipse.
Refer https://www.journaldev.com/18124/java-project-lombok


## Prerequisite - DB configured

The application connects to Postgres DB running at localshost with port 5432. 
Username and password as shown below. 
Please update the sr/main/resources/application.properties as per the postgres configured in your environment to stary the application.

### Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username= postgres
spring.datasource.password= postgres

# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
