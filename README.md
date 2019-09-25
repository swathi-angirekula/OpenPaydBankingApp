# OpenPayd Banking Application 
 
 Project Description 
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

Model specification
A client should have the following details:
•	Name
•	Surname
•	Primary address
o	Address line 1 - Address line 2 - City – Country
•	Secondary address
o	Address line 1 - Address line 2 - City - Country
 
An account should have the following details:
•	Type (CURRENT or SAVINGS) • Balance
•	Balance status (DR or CR) • Date created

A transaction should have the following details:
•	Debit account
•	Credit account
•	Amount
•	Message
•	Date created

Further instructions
Make sure that the code you send is functional and does not have syntax errors. Complete all Java docs. Structure your project using Maven. Compile a Readme.txt which should contain step by step instructions of how to resource the database, build the project and deploy the application into an

Apache Tomcat container
The project should be developed using a Spring MVC infrastructure. Hibernate over Spring Data JPA should be used to persist and retrieve the data. The underlying data source should be backed up by a PostgreSQL database.

Additional notes

When effecting a transfer between two accounts, the necessary checks should be performed to ensure that the requested transfer is valid. It is also required that transfers are executed atomically, preventing concurrent transfers from interfering. A transfer is made up of two parts: a debit (DR) operation and a credit (CR) operation. Therefore, such a transfer should be stored as a transaction identifying the account that was debited and the account that was credited. Note that an account balance status can either be in debit (DR) or in credit (CR). An account with a debit balance will be increased by a debit operation and decreased by a credit operation. Conversely an account with a credit balance will be increased by a credit operation and decreased by a debit operation. Therefore, account balances can shift their balances from DR to CR and vice versa, depending on the transfer.

# How to Compile
mvn clean install 


Swagger : http://localhost:8080/swagger-ui.html#/
