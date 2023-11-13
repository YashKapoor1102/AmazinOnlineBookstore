# AmazinOnlineBookstore
SYSC4806 Final Project that uses Spring Framework and H2 Database

# Project Description 
Amazin Bookstore is an online web application that serves as a comprehensive platform for bookstore owners and users. It provides bookstore owners with robust tools for managing their inventory, while also providing users with a seamless experience to browse and purchase books. Additionally, the application offers advanced features such as book recommendations and flexible filtering options

# Installation Instructions
1. Make sure you have Maven and Java installed on your system.
2. Clone the git repository https://github.com/TheDana1/AmazinOnlineBookstore.git 

# Usage
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
3. Users can register/log in, browse, search, and filter through books.

# Development Roadmap

## Milestone 1 : Early prototype

Users are currently able to log in/register and browse/search/filter through books.  

### Completed Components:
* Book Entity
* Book Controller
* Book Unit Tests
* User Entity
* User Controller
* User Unit Tests

## Milestone 2 : Alpha Release

In the upcoming implementation, users will have the capability to add books to their shopping cart, purchase, and view recommendations. Additionally, a database migration tool will be implemented to enhance the inventory management system.

### Upcoming Implementations:
* Database Migration Tool
* Inventory
* Inventory Controller
* Shopping Cart
* Shopping Cart Controller
* Checkout
* Checkout Controller
* Recommendations Entity
* Recommendations Controller

## Milestone 3 : Final demo

# UML Diagram of Project 
![image](https://github.com/TheDana1/AmazinOnlineBookstore/assets/89320833/7754e635-0b51-4f18-9ce6-c71367ade790)



# Database Schema of Project
![DatabaseFile](https://github.com/TheDana1/AmazinOnlineBookstore/assets/44479056/d4e0b6ff-afa4-4d5f-ba45-cebcc389c191)


# Known issues
Currently, there are no known issues.

# License
