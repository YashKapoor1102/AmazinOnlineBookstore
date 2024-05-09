# AmazinOnlineBookstore
SYSC4806 Final Project that uses Spring Framework and H2 Persistent Database 

## Project Description 
Amazin Bookstore is an online web application that serves as a comprehensive platform for bookstore owners and users. It provides bookstore owners with robust tools for managing their inventory, while also providing users with a seamless experience to browse and purchase books. Additionally, the application offers advanced features such as book recommendations and flexible filtering options

## Installation Instructions
1. Make sure you have Maven and Java installed on your system.
2. Clone the git repository https://github.com/TheDana1/AmazinOnlineBookstore.git 

## Usage
1. Run _main_ AmazinOnlineBookstore\src\main\java\amazin\bookstore\AccessingDataJpaApplication.java
2. To set the bookstore owner (optional):
 ```
Command prompt on Windows:
setx ADMIN_USERNAME "your_admin_username"
setx ADMIN_PASSWORD "your_admin_password"


JAR file MUST be ran from command prompt as well
Use this command after setting env vars: java -jar AmazinOnlineBookstore-0.0.1-SNAPSHOT.jar

Or you can go to Run -> Edit Configurations and set the env vars there
like this: ADMIN_USERNAME=username;ADMIN_PASSWORD=password

Terminal on MacOS:
export ADMIN_USERNAME="your_admin_username"
export ADMIN_PASSWORD="your_admin_username"

Run these two commands before running the JAR file
```
3. Access localhost at http://localhost:8080/books and http://localhost:8080/user/register for now
4. Users can register/log in, browse, search, and filter through books.

## Development Roadmap

### Milestone 1 : Early prototype

Users are able to log in/register and browse/search/filter through books.  

#### Completed Components:
* Book Entity
* Book Controller
* Book Unit Tests
* User Entity
* User Controller
* User Unit Tests

### Milestone 2 : Alpha Release

In this implementation, users have the capability to add books to their shopping cart, remove books from their shopping cart, purchase them, view recommendations, and filter books by price. Additionally, flyway, a database migration tool, was implemented to enhance the inventory management system.

#### Completed Components:
* Shopping Cart
* Shopping Cart Controller
* Checkout functionality 
* Shopping Cart Unit Tests
* User Unit Tests editing
* Book Unit Test Editing
* Inventory
* Inventory Controller
* Recommendations Entity
* Recommendations Controller

### Milestone 3 : Final demo
For the final version of the project, all minor details like error handling and webpage appearance were improved.
Users are now able to select the quantity of books that they would like to purchase and, before checkout, view the total cost in their cart.
Purchasing a book multiple times and viewing previously purchased books, as well as recommendations via a button is also permitted. 

#### Completed Components:
* Upgraded Error Handling for shoppingCart Functionality
* Upgraded User Registration
* Improved Checkout Functionality
* Updated Recommendations function
* Updated Unit Tests


## UML Diagram of Project 
![UML drawio (1)](https://github.com/TheDana1/AmazinOnlineBookstore/assets/78821595/6f6d85d9-5729-4c46-9209-7b5306fe991b)


## Database Schema of Project
![Milestone3Schema](https://github.com/TheDana1/AmazinOnlineBookstore/assets/44479056/0a93038b-8902-42fb-a0e1-7a3a7a847ac1)




## Known issues
Currently, there are no known issues.

## License
