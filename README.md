<h1 align="center">API REST de Clientes</h1>

<p align="center">
  CRUD de clientes desenvolvido com Java, Spring Boot e PostgreSQL.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot 4.0.6">
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven">
</p>

## Sobre o projeto

Esta API permite cadastrar, listar, atualizar e remover clientes de uma loja. Os dados são persistidos em PostgreSQL e a estrutura do banco é versionada com Flyway.

O projeto foi desenvolvido como atividade prática da disciplina de **Paradigmas de Programação Orientada a Objetos**, no curso de Ciência da Computação da **Uni-FACEF**.

## Funcionalidades

- Cadastro de clientes
- Listagem de todos os clientes
- Busca de cliente pelo ID
- Atualização de clientes pelo ID
- Remoção de clientes pelo ID
- Geração automática de identificadores pelo banco
- Persistência dos dados em PostgreSQL
- Versionamento do banco de dados com Flyway
- Validação de nome, e-mail e idade
- Respostas HTTP adequadas para cada operação
- Testes unitários, de validação e de integração

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- Jakarta Bean Validation
- OpenAPI 3 e Swagger UI
- JUnit 5

## Arquitetura

A aplicação está separada em camadas:

```text
src/main/java/dc/unifacef/memoria/
├── config/
│   └── OpenApiConfig.java
├── controller/
│   └── ClienteController.java
├── exception/
│   └── TratamentoGlobalExceptionHandler.java
├── model/
│   └── Cliente.java
├── repository/
│   └── ClienteRepository.java
├── service/
│   └── ClienteService.java
└── MemoriaApplication.java
```

- **Config:** define as informações exibidas na documentação OpenAPI.
- **Controller:** recebe as requisições HTTP e devolve as respostas.
- **Service:** concentra as regras de cadastro, busca, atualização e remoção.
- **Repository:** realiza a persistência dos clientes com Spring Data JPA.
- **Exception:** organiza as respostas dos erros de validação.
- **Model:** representa e valida os dados de um cliente.
- **Application:** inicializa a aplicação Spring Boot.

## Modelo de cliente

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "idade": 28
}
```

O campo `id` é gerado automaticamente pelo PostgreSQL durante o cadastro.

### Regras de validação

- `nome`: obrigatório e não pode conter apenas espaços
- `email`: obrigatório e deve possuir formato válido
- `idade`: obrigatória e deve estar entre 0 e 120

### Resposta para dados inválidos

Quando uma requisição contém dados inválidos, a API retorna `400 Bad Request` e informa os campos que precisam ser corrigidos:

```json
{
  "nome": "O nome é obrigatório",
  "email": "Informe um e-mail válido"
}
```

## Endpoints

URL base: `http://localhost:8080`

| Método | Endpoint | Descrição | Resposta |
|:--|:--|:--|:--|
| `GET` | `/clientes` | Lista todos os clientes | `200 OK` |
| `GET` | `/clientes/{id}` | Busca um cliente pelo ID | `200 OK` ou `404 Not Found` |
| `POST` | `/clientes` | Cadastra um cliente | `201 Created` ou `400 Bad Request` |
| `PUT` | `/clientes/{id}` | Atualiza um cliente | `200 OK`, `400 Bad Request` ou `404 Not Found` |
| `DELETE` | `/clientes/{id}` | Remove um cliente | `204 No Content` ou `404 Not Found` |

## Documentação interativa

Com a aplicação em execução, acesse:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI em JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **OpenAPI em YAML:** [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

O Swagger UI permite visualizar e testar todos os endpoints diretamente pelo navegador.

## Exemplos de uso

### Cadastrar um cliente

```http
POST /clientes
Content-Type: application/json
```

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "idade": 28
}
```

A API retorna `201 Created`, o cliente cadastrado e o endereço do novo recurso no cabeçalho `Location`.

### Listar clientes

```http
GET /clientes
```

Exemplo de resposta:

```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "idade": 28
  }
]
```

### Buscar um cliente pelo ID

```http
GET /clientes/1
```

A API retorna `200 OK` com os dados do cliente ou `404 Not Found` quando o ID não existe.

### Atualizar um cliente

```http
PUT /clientes/1
Content-Type: application/json
```

```json
{
  "nome": "João Souza",
  "email": "joao.souza@email.com",
  "idade": 29
}
```

### Remover um cliente

```http
DELETE /clientes/1
```

A API retorna `204 No Content` quando a remoção é concluída ou `404 Not Found` quando o ID não existe.

## Como executar

### Pré-requisitos

- Java 21
- Git

### Banco de dados

Com Docker instalado, inicie o PostgreSQL:

```bash
docker compose up -d
```

A configuração padrão cria o banco `clientes` na porta `5432`. É possível usar outro PostgreSQL definindo:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

O Flyway cria a tabela automaticamente ao iniciar a aplicação.

### Instalação

```bash
git clone https://github.com/Juliarezende521/API-Rest-Clientes.git
cd API-Rest-Clientes
```

No macOS ou Linux:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Depois, acesse `http://localhost:8080/clientes`.

## Testes automatizados

O projeto possui testes unitários e de integração para verificar:

- Regras do serviço de clientes
- Validações do modelo
- Cadastro e listagem
- Busca por ID
- Atualização e remoção
- Respostas `400 Bad Request` e `404 Not Found`

Para executar:

```bash
./mvnw test
```

Os testes também são executados automaticamente pelo GitHub Actions a cada alteração enviada para a branch `main`.

## Como testar manualmente

Os endpoints podem ser testados com:

- Postman
- Insomnia
- Thunder Client
- cURL

Exemplo com cURL:

```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@email.com","idade":28}'
```

## Próximas melhorias

- Criar paginação e filtros de busca
- Adicionar verificação de e-mail duplicado

## Aprendizados

Este projeto pratica conceitos de:

- Programação orientada a objetos
- Arquitetura em camadas
- Injeção de dependências
- Criação de APIs REST
- Métodos e códigos de status HTTP
- Manipulação de coleções com Java
