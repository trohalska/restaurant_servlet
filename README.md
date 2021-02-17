# Restaurant Servlet App

### Description:

There are roles: Customer, Manager.

The customer (authorized user) makes orders from the menu (the catalog of dishes).
He can filter the list of dishes by categories
and has the opportunity look through the catalog with sorting:
- by the name of the dish;
- by price;
- by category.

The customer, within one order, can order several identical dishes.

The manager manages orders: after receiving a new order, sends it for cooking.
After cooking, the manager transfers  order for delivery.
After delivery and receipt of payment, the manager transfers the status of the order to "done".

### Implementation:
- Front-End - HTML/CSS/JavaScript/AngularJS
- Back-end - Java 8, Maven, SQL

### Technologies:
- Java EE (JSP, JDBC, Servlets)
- Apache Tomcat 7
- PastgreSQL

### Usage:
```
1. git clone https://github.com/trohalska/restaurant_servlet
2. apply maven wrapper: mvn -N io.takari:maven:wrapper
3. run server: ./mvnw tomcat7:run
4. open website: http://localhost:8080/pixelizator
```