# Projeto Spring Boot - E-shop
Este é um projeto desenvolvido com Spring Boot para fornecer uma API REST simples. Ele inclui endpoints para realizar operações CRUD (Create, Read, Update, Delete) em um recurso fictício. A API oferece suporte aos métodos HTTP GET, POST, PUT e DELETE.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para construção da aplicação.
- **Spring Web**: Para criação da API REST.
- **JPA/Hibernate**: Para interação com o banco de dados.
- **Spring Security/JWT**: Para segurança das rotas e criação de tokens.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Maven**: Gerenciador de dependências e build.

## Instalação

1. Clone o repositório para o seu ambiente local:
    ```bash
    git clone https://github.com/freitas022/e-shop.git
    ```

2. Acesse a pasta do projeto:
    ```bash
    cd e-shop
    ```

3. Compile e execute o projeto com o Maven:
    ```bash
    mvn spring-boot:run
    ```

4. A aplicação estará disponível em `(http://localhost:8080/swagger-ui/index.html)`.

## Endpoints da API

Abaixo estão os detalhes dos endpoints da API.

### 1. GET `localhost:8080/products?pageNumber=0&pageSize=10&direction=ASC&orderBy=name`

Recupera todos os itens.

- **Método**: GET
- **Descrição**: Retorna uma lista paginada (filtros de ordenação, quantidade de itens por página e nome do campo) de todos os itens cadastrados.
- **Resposta Exemplo**:
  ```json
  [
    {
      "id": 1,
      "name": "Item 1",
      "description": "Descrição do Item 1",
      "price": 99.99,
      "imgUrl": "" //futuramente com Amazon S3,
      "stockQuantity": "50"
    },
    {
      "id": 2,
      "name": "Item 2",
      "description": "Descrição do Item 2",
      "price": 19.99,
      "imgUrl": "" //futuramente com Amazon S3,
      "stockQuantity": "50"
    }
  ]
