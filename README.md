# Portifólio Spring Boot + React - E-shop
E-shop é um sistema de e-commerce. O projeto adota uma abordagem orientada a eventos (event-driven), utilizando SNS e SQS para comunicação interna entre diferentes domínios e Amazon SES para disparo de e-mails.

## Funcionalidades
- Cadastro e autenticação de usuários.
- Gerenciamento de produtos, estoque e pedidos.
- Carrinho de compras
- Processamento de pedidos com mock de pagamento.
- Envio automático de e-mails de notificação

## Tecnologias Utilizadas

- **Spring + Maven**: Framework para construção da aplicação e gerenciador de dependências e build.
- **React + Typescript**: Para criação das páginas web e tipagem de dados.
- **JPA/Hibernate**: Para comunicação com o banco de dados.
- **Spring Security/JWT**: Para segurança das rotas, criação de cookies e tokens.
- **PostgreSQL**: Banco de dados para desenvolvimento.
- **Docker**: Para containerização do app e portabilidade para ambientes cloud.

## Pré-requisitos

- Java 17
- Node.js (versão 18 ou superior)
- Docker e Docker Compose

## Instalação

1. Clone o repositório para o seu ambiente local:
    ```bash
    git clone https://github.com/freitas022/e-shop.git
    ```

2. Navegue até a pasta do projeto:
    ```bash
    cd e-shop/
    
    # Passo 1: iniciar o app + banco de dados + localstack com docker-compose
    cd e-shop-backend/
   
    docker-compose up -d
   
    # Passo 2: executar o script para criação de filas, tópicos e verificação de e-mail no localstack
    # 
    ./setup_aws_resources.sh
   
    # Passo 3: instalar o gerenciador de pacotes Yarn
    npm install -g yarn
   
    # Passo 4: iniciar o frontend, entre na pasta frontend:
    cd e-shop-frontend/
    yarn
    yarn dev
    ```
Acesse a documentação da API através desse link [Swagger Doc](http://localhost:8080/swagger-ui/index.html)

Acesse o frontend através desse link [E-shop](http://localhost:5173/)