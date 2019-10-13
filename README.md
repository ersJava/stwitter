# Stwitter

<img src="https://github.com/ersJava/stwitter/blob/master/Screen%20Shot%202019-10-02%20at%209.57.04%20PM.png" alt="Stwitter store mock up">


## How It Works

A social media service for a company intranet for users to create post and make comments on other posts.

Please see YAML for API documentation. 

UML Diagram included for application set up.

## Project Details

Config server: https://github.com/ersJava/Stwitter-config-server

This application implements REST microservices, queue producers and consumers, and using Feign web service clients.

The Post service and Comment service are typical Spring Boot REST web service with Controller, DAO (utilizing Jdbc template and prepared statements), and Service Layer components. The Stwitter Service is the edge service with which end user applications (web and mobile) interact. Exceptions are handled through Controller Advice and return proper HTTP status codes and data when exception occur.

### Technologies Used
* Java
* Spring Boot
* Feign
* Eureka Server
* MySQL
* RabbitMQ
* Cache
* Mockito


![UML Diagram](StwitterUMLDiagram.jpg) 
