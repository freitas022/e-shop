### E-SHOP COMMERCE

E-commerce project developed with the aim of exercising programming logic, data structure, orm, as well as unit and integration tests.

### Tools

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

### Start the database (need docker installed)
I will soon make the app available in docker-compose.yml
```bash
docker compose up -d
```
### Start the project with maven
```bash
mvn spring-boot:run #to run dev profile need start postgres
```

### Start the frontend
```bash
#install dependencies
yarn install
#run
yarn dev
```
---
### Endpoints

#### Authentication

- POST **/sign-in**  - Authenticate user and return JWT token.
- POST **/sign-up** - Create a new user.
---
#### Products

- GET **/products** - Retrieve a list of products.
- GET **/products/{id}** - Retrieve a product with the ID.
---
#### Categories

- GET **/categories** - Retrieve a list of categories.
- GET **/categories/{id}** - Retrieve a category with the ID.
---
#### Orders

- POST **/orders** - Create a new order.
- GET **/orders** - Retrieve a list of orders (ROLE_ADMIN only).
- GET **/orders/{id}** - Retrieve an order with the ID (Authenticated only).
---
#### Users

- GET **/users/{id}** - Retrieve a user with the ID.
- GET **/users** - Retrieve a list of users.
- PUT **/users/{id}** - Update an existing user.
- POST **/users** - Create a new user.

---
Swagger doc:

```
localhost:8080/swagger-ui.html
```