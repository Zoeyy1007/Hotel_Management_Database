# Hotel Management Database System (CS166 group project)

A relational database application designed to manage hotel operations, including guest registration, room assignments, and maintenance tracking. This project integrates a **Java** backend with a **PostgreSQL** database via **JDBC**.

## Technical Stack

* **Language**: Java
* **Database**: PostgreSQL
* **API**: JDBC (Java Database Connectivity)
* **Build Tool**: Bash scripts for compilation and execution

## Core Features & Contributions

This project was developed as a collaborative effort. My primary responsibilities focused on the backend logic and data integrity for cases 1-7:

* **Data Entry Modules**: Authored the logic for `addCustomer`, `addRoom`, and `addRepair` functionalities.
* **Input Validation**: Implemented robust regex-based validation (e.g., ensuring IDs contain only digits) and date format checking to prevent database errors.
* **Secure Query Execution**: Utilized SQL statements to interact with the database, including error handling for "Record Not Found" scenarios.
* **System Integration**: Developed the main application loop and user-interface menu (Switch/Case logic) to coordinate between user input and database updates.

## Team Contributions

* **Groupmate (Lyda)**: Implemented performance optimizations through **SQL Indexing** and managed the `Staff` table operations.
* **Shared**: Database schema design and relationship mapping.

## Installation and Setup

1. **Prerequisites**: Ensure you have a running **PostgreSQL** server and the `pg73jdbc3.jar` driver.
2. **Build the Project**:
```bash
cd Phase3/java
chmod +x compile.sh
./compile.sh

```


3. **Run the Application**:
Execute the generated `DBProject` class and provide your database credentials when prompted.

