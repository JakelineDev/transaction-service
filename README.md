# Transaction Service

This service is resposible for registrating financial transactions and publish events to RABBITMQ.

## Endpoints

- `POST /api/transactions`: Cria uma nova transação
- `POST /api/transactions`: Creates a new transaction. Accepts a JSON request body conforming to the TransactionRequestDTO.
- `GET /api/transactions/{id}`: Retrieves a specific transaction by its unique ID.
- `GET /api/transactions/user/{userId}`: Retrieves all transactions associated with a given user ID.
- `PUT /api/transactions/{id}`: Updates an existing transaction identified by its ID. Accepts a JSON request body conforming to the TransactionRequestDTO.
- `DELETE /api/transactions/{id}`: Deletes a transaction identified by its ID.

## Build
mvn clean package -DskipTests

### Docker
```bash
docker build -t transaction-service .
docker run -p 8082:8082 --network microservices transaction-service
