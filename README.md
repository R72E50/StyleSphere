# StyleSphere E-Commerce Project

**StyleSphere** is an e-commerce application built using the Spring Boot framework. This project leverages various technologies for building a robust and scalable online store. Below is the information about the project, its dependencies, and how to get started.

---

## Project Overview

- **Name**: StyleSphere
- **Description**: E-Commerce project for Spring Boot
- **Version**: 0.0.1-SNAPSHOT
- **Group**: com.example
- **Parent**: spring-boot-starter-parent version 3.4.1

---

## Prerequisites

Before you begin, ensure that you have the following installed on your system:

- Java 22 or higher
- Maven 3.8+
- MySQL database (or H2 for testing)
- An email service (for testing, GreenMail is included)

---

## Dependencies

This project includes several key dependencies:

### Spring Boot Dependencies
- `spring-boot-starter-actuator`: For monitoring and management of the application.
- `spring-boot-starter-data-jdbc`, `spring-boot-starter-data-jpa`, and `spring-boot-starter-jdbc`: For database interaction.
- `spring-boot-starter-mail`: For email integration.
- `spring-boot-starter-security`: For security features.
- `spring-boot-starter-web`: For creating web applications, including RESTful APIs.
- `spring-boot-starter-websocket`: For WebSocket support.

### Other Dependencies
- `java-jwt`: For JWT authentication and authorization.
- `lombok`: For reducing boilerplate code (getter, setter, etc.).
- `mysql-connector-j`: MySQL database driver.
- `h2`: H2 in-memory database (for testing).
- `greenmail-junit5`: For email service testing.
- `spring-security-test`: For testing Spring Security configurations.

---

## Getting Started

Follow these steps to get the project up and running:

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/StyleSphere.git
cd StyleSphere
