# Person Management API

## Requisitos
- Java 11+
- Maven 3+
- MySQL

## Configuração do Banco de Dados
1. Atualize `src/main/resources/application.properties` com suas credenciais do MySQL.

2. Crie um banco de dados MySQL caso a criação automática do banco não funcionar:
    ```sql
    CREATE DATABASE your_database_name;
    ```

## Executando a Aplicação
1. Compile e rode o projeto:
    ```bash
    mvn spring-boot:run
    ```

2. A API estará disponível em `http://localhost:8080 e se quiser pela documentação do swagger: http://localhost:8080/swagger-ui/index.html#/`.

# Endpoints
## Person
- `GET /api/persons/get-all`
- `GET /api/persons/{id}`
- `POST /api/persons/create`
- `PUT /api/persons/update`
- `DELETE /api/persons/{id}`

## Establishment
- `GET /api/establishments/get-all`
- `GET /api/establishments/{id}`
- `PUT /api/establishments/update`
- `POST /api/establishments/create`
- `DELETE /api/establishments/{id}`

## Role
- `GET /api/roles/{id}`
- `GET /api/roles/get-all`
- `PUT /api/roles/update`
- `POST /api/roles/create`
- `DELETE /api/roles/{id}`
