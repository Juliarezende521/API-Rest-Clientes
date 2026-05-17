# API REST de Clientes — CRUD em Memória

API desenvolvida com **Spring Boot** para gerenciar o cadastro de clientes de uma loja, utilizando armazenamento em memória (sem banco de dados).

Projeto prático da disciplina de Desenvolvimento de Sistemas — **Unifacef**.

---

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Maven

---

## Estrutura do Projeto

```
src/main/java/dc/unifacef/memoria/
├── controller/
│   └── ClienteController.java
├── model/
│   └── Cliente.java
├── service/
│   └── ClienteService.java
└── MemoriaApplication.java
```

---

## Endpoints

Base URL: `http://localhost:8080`

| Método | Rota | Descrição | Status de retorno |
|--------|------|-----------|-------------------|
| GET | `/clientes` | Lista todos os clientes | 200 OK |
| POST | `/clientes` | Cadastra um novo cliente | 201 Created |
| PUT | `/clientes/{id}` | Atualiza os dados de um cliente | 200 OK / 404 Not Found |
| DELETE | `/clientes/{id}` | Remove um cliente pelo ID | 204 No Content / 404 Not Found |

---

## Exemplos de Uso

### POST /clientes — Cadastrar cliente

**Requisição:**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "idade": 28
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "idade": 28
}
```

### GET /clientes — Listar todos

**Resposta (200 OK):**
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

### PUT /clientes/1 — Atualizar cliente

**Requisição:**
```json
{
  "nome": "João Souza",
  "email": "joao.souza@email.com",
  "idade": 29
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Souza",
  "email": "joao.souza@email.com",
  "idade": 29
}
```

### DELETE /clientes/1 — Remover cliente

**Resposta:** `204 No Content`  
Se o ID não existir: `404 Not Found`

---

## Como executar

**Pré-requisitos:** Java 21 e Maven instalados.

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/memoria.git
cd memoria

# Execute
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## Testando a API

Use o **Postman** ou **Insomnia** para testar os endpoints POST, PUT e DELETE.  
O GET pode ser testado diretamente no navegador.
