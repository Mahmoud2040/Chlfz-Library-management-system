# Chlfz-Library-management-system

## Project Description
The Library Management System API is built using Spring Boot. This system allows librarians to manage books, patrons, and borrowing records efficiently.

## Details
### Entities
- **Book**: Includes attributes like ID, title, author, publication year, ISBN, etc.
- **Patron**: Contains details like ID, name, contact information, etc.
- **Borrowing Record**: Tracks the association between books and patrons, including borrowing and return dates.

### API Endpoints
#### Book Management Endpoints:
- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by ID.
- **POST /api/books**: Add a new book to the library.
- **PUT /api/books/{id}**: Update an existing book's information.
- **DELETE /api/books/{id}**: Remove a book from the library.

#### Patron Management Endpoints:
- **GET /api/patrons**: Retrieve a list of all patrons.
- **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
- **POST /api/patrons**: Add a new patron to the system.
- **PUT /api/patrons/{id}**: Update an existing patron's information.
- **DELETE /api/patrons/{id}**: Remove a patron from the system.

#### Borrowing Endpoints:
- **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

### Data Storage
- Uses PostgreSQL database to persist book, patron, and borrowing record details.
- Proper relationships are set up between entities (e.g., one-to-many between books and borrowing records).

### Validation and Error Handling
- Implements input validation for API requests (e.g., validating required fields, data formats, etc.).
- Handles exceptions gracefully and returns appropriate HTTP status codes and error messages.

### Docker Compose
- Utilizes Docker Compose to set up the development environment.
- PostgreSQL database container is used with the following settings:
  - **Image**: PostgreSQL latest version
  - **Port Mapping**: Host port 5432 to container port 5432

### Security
-  basic authentication is implemented to protect the API endpoints.

### Aspects 
- Logging is implemented using Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics of certain operations like book additions, updates, and patron transactions.

### Caching
- Currently, caching has not been added to the system. However, we plan to implement it soon to enhance performance.
- We will utilize Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information.
- Redis will be used as the caching provider to ensure efficient and fast access to cached data.

### Transaction Management
- Implements declarative transaction management using Spring's @Transactional annotation to ensure data integrity during critical operations.

### Testing
- Unit tests are written to validate the functionality of API endpoints.
- Testing frameworks used: JUnit, Mockito, and SpringBootTest.

## Getting Started

### Prerequisites
- Java 21
- Maven
- Docker
- Docker Compose

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Mahmoud2040/Chlfz-Library-management-system.git
    cd Chlfz-Library-management-system
    ```

2. Start the development environment using Docker Compose:
    ```sh
    docker-compose up -d
    ```

3. Build and run the application:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

### API Documentation
#### Swagger URL : /swagger-ui/index.html#/

#### Book Management Endpoints
1. **Get All Books**
   - **URL**: `/api/books`
   - **Method**: GET

2. **Get Book by ID**
   - **URL**: `/api/books/{id}`
   - **Method**: GET

3. **Add a Book**
   - **URL**: `/api/books`
   - **Method**: POST

4. **Update a Book**
   - **URL**: `/api/books/{id}`
   - **Method**: PUT

5. **Delete a Book**
   - **URL**: `/api/books/{id}`
   - **Method**: DELETE

#### Patron Management Endpoints
1. **Get All Patrons**
   - **URL**: `/api/patrons`
   - **Method**: GET

2. **Get Patron by ID**
   - **URL**: `/api/patrons/{id}`
   - **Method**: GET

3. **Add a Patron**
   - **URL**: `/api/patrons`
   - **Method**: POST

4. **Update a Patron**
   - **URL**: `/api/patrons/{id}`
   - **Method**: PUT

5. **Delete a Patron**
   - **URL**: `/api/patrons/{id}`
   - **Method**: DELETE

#### Borrowing Endpoints
1. **Borrow a Book**
   - **URL**: `/api/borrow/{bookId}/patron/{patronId}`
   - **Method**: POST

2. **Return a Book**
   - **URL**: `/api/return/{bookId}/patron/{patronId}`
   - **Method**: PUT

3. **Get All Borrow Records**
- **URL**: `/api/getBorrowRecords`
   - **Method**: Get

### Exception Handling
Validation errors, record not found errors, and conflict errors are managed with detailed messages and appropriate HTTP status codes.
```
