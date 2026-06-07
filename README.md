# NextClass

API RESTful para gestão de uma plataforma educacional, desenvolvida com Spring Boot. O sistema contempla autenticação JWT, controle de acesso por perfil, gerenciamento de usuários, cursos, turmas, matrículas e atendimentos.

---

## Tecnologias

- **Java 21**
- **Spring Boot 3.5**
    - Spring Web
    - Spring Data JPA
    - Spring Security + JWT
    - Spring Validation
    - Thymeleaf
- **H2 Database** (em memória, para desenvolvimento)
- **Lombok**
- **Springdoc OpenAPI / Swagger UI**
- **Maven**

---

## Pré-requisitos

- Java 21+
- Maven 3.8+ (ou usar o `mvnw` incluso no projeto)

---

## Como executar

```bash
git clone https://github.com/lunguinhoantonio/nextclass.git
cd nextclass

./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## Documentação da API

```
http://localhost:8080/swagger-ui.html
```

---

## Banco de dados (H2 Console)

```
http://localhost:8080/h2-console
```

| Campo    | Valor                  |
|----------|------------------------|
| JDBC URL | `jdbc:h2:mem:nextclass` |
| Usuário  | `sa`                   |
| Senha    | *(vazio)*              |

---

## Estrutura do projeto

```
src/
└── main/
    ├── java/edu/technosplay/NextClass/
    │   ├── config/          # SecurityConfig, filtros JWT
    │   ├── controller/      # Controllers REST e UI (Thymeleaf)
    │   ├── dto/
    │   │   ├── request/     # DTOs de entrada
    │   │   └── response/    # DTOs de saída
    │   ├── exception/       # GlobalExceptionHandler, exceções customizadas
    │   ├── mapper/          # Mapeamento entidade ↔ DTO
    │   ├── model/           # Entidades JPA
    │   │   └── enums/       # Role, StatusAtendimento, TipoAtendimento, StatusMatricula, DiaSemana
    │   ├── repository/      # Repositórios Spring Data JPA
    │   └── service/         # Interfaces e implementações de serviço
    └── resources/
        ├── static/          # CSS, JS, imagens
        ├── templates/       # Páginas Thymeleaf
        └── application.yaml
```

---

## Autenticação

A API usa **JWT Bearer Token**. Para acessar endpoints protegidos:

1. Registre um usuário via `POST /nextclass/auth/registrar`
2. Faça login via `POST /nextclass/auth/login` e copie o `token` da resposta
3. Envie o token no header de todas as requisições:

```
Authorization: Bearer <token>
```

---

## Perfis de usuário (Roles)

| Role          | Descrição                              |
|---------------|----------------------------------------|
| `ALUNO`       | Pode se matricular em turmas e abrir atendimentos |
| `PROFESSOR`   | Docente vinculado a cursos             |
| `ATENDENTE`   | Realiza e gerencia atendimentos        |
| `COORDENADOR` | Acesso administrativo completo         |

---

## Endpoints

### Autenticação — `/nextclass/auth`

| Método | Rota                        | Autenticação | Descrição                  |
|--------|-----------------------------|--------------|----------------------------|
| `POST` | `/nextclass/auth/registrar` | Pública      | Cadastra um novo usuário   |
| `POST` | `/nextclass/auth/login`     | Pública      | Retorna o JWT de acesso    |
| `POST` | `/nextclass/auth/logout`    | JWT          | Invalida a sessão          |

---

### Usuários — `/nextclass/usuarios`

| Método  | Rota                              | Role mínima    | Descrição                                         |
|---------|-----------------------------------|----------------|---------------------------------------------------|
| `GET`   | `/nextclass/usuarios`             | `COORDENADOR`  | Lista usuários (filtros: `role`, `ativo`)         |
| `GET`   | `/nextclass/usuarios/{id}`        | Autenticado    | Busca usuário por ID                              |
| `PATCH` | `/nextclass/usuarios/{id}/ativar` | `COORDENADOR`  | Ativa a conta de um usuário                       |
| `PATCH` | `/nextclass/usuarios/{id}/desativar` | `COORDENADOR` | Desativa a conta de um usuário                  |

**Query params — listar:**

| Parâmetro | Tipo    | Exemplo      | Descrição                              |
|-----------|---------|--------------|----------------------------------------|
| `role`    | String  | `ALUNO`      | Filtra por perfil                      |
| `ativo`   | Boolean | `true`       | Filtra por status ativo/inativo        |

---

### Cursos — `/nextclass/cursos`

| Método   | Rota                               | Role mínima    | Descrição                                       |
|----------|------------------------------------|----------------|-------------------------------------------------|
| `POST`   | `/nextclass/cursos`                | `COORDENADOR`  | Cria um novo curso                              |
| `GET`    | `/nextclass/cursos`                | Autenticado    | Lista cursos (filtros: `professorId`, `ativo`)  |
| `GET`    | `/nextclass/cursos/{id}`           | Autenticado    | Busca curso por ID                              |
| `PUT`    | `/nextclass/cursos/{id}`           | `COORDENADOR`  | Atualiza curso completamente                    |
| `PATCH`  | `/nextclass/cursos/{id}`           | `COORDENADOR`  | Atualiza campos específicos do curso            |
| `PATCH`  | `/nextclass/cursos/{id}/ativar`    | `COORDENADOR`  | Ativa o curso                                   |
| `PATCH`  | `/nextclass/cursos/{id}/desativar` | `COORDENADOR`  | Desativa o curso                                |

**Query params — listar:**

| Parâmetro    | Tipo    | Exemplo | Descrição                    |
|--------------|---------|---------|------------------------------|
| `professorId`| Long    | `1`     | Filtra por professor         |
| `ativo`      | Boolean | `true`  | Filtra por status ativo/inativo |

---

### Turmas — `/nextclass/turmas`

| Método  | Rota                               | Role mínima    | Descrição                                      |
|---------|------------------------------------|----------------|------------------------------------------------|
| `POST`  | `/nextclass/turmas`                | `COORDENADOR`  | Cria uma nova turma vinculada a um curso       |
| `GET`   | `/nextclass/turmas`                | Autenticado    | Lista turmas (filtros: `cursoId`, `ativa`)     |
| `GET`   | `/nextclass/turmas/{id}`           | Autenticado    | Busca turma por ID                             |
| `PATCH` | `/nextclass/turmas/{id}/ativar`    | `COORDENADOR`  | Ativa a turma                                  |
| `PATCH` | `/nextclass/turmas/{id}/desativar` | `COORDENADOR`  | Desativa a turma                               |

**Query params — listar:**

| Parâmetro | Tipo    | Exemplo | Descrição                      |
|-----------|---------|---------|--------------------------------|
| `cursoId` | Long    | `1`     | Filtra por curso               |
| `ativa`   | Boolean | `true`  | Filtra por status ativa/inativa |

**Regras de negócio:**
- Não é possível criar turma para um curso inativo
- A resposta inclui `vagasTotais`, `vagasOcupadas` e `vagasDisponiveis`

---

### Matrículas — `/nextclass/matriculas`

| Método   | Rota                                  | Role mínima              | Descrição                                    |
|----------|---------------------------------------|--------------------------|----------------------------------------------|
| `POST`   | `/nextclass/matriculas`               | `ALUNO`                  | Matricula o aluno autenticado em uma turma   |
| `GET`    | `/nextclass/matriculas/minhas`        | `ALUNO`                  | Lista as matrículas do aluno autenticado     |
| `GET`    | `/nextclass/matriculas/{id}`          | `ALUNO`, `COORDENADOR`, `ATENDENTE` | Busca matrícula por ID            |
| `GET`    | `/nextclass/matriculas/aluno/{id}`    | `COORDENADOR`, `ATENDENTE` | Lista matrículas de um aluno específico    |
| `DELETE` | `/nextclass/matriculas/{id}`          | `ALUNO`, `COORDENADOR`   | Cancela uma matrícula ativa                  |

**Regras de negócio:**
- O aluno só pode estar matriculado em **1 turma ativa** por vez
- O aluno não pode cursar mais de **2 cursos distintos** simultaneamente
- Não é possível se matricular em turma inativa ou sem vagas
- O aluno só pode cancelar a própria matrícula; o `COORDENADOR` pode cancelar qualquer uma

---

### Atendimentos — `/nextclass/atendimentos`

| Método  | Rota                                                  | Autenticação    | Descrição                                        |
|---------|-------------------------------------------------------|-----------------|--------------------------------------------------|
| `POST`  | `/nextclass/atendimentos/publico`                     | Pública         | Abre atendimento sem conta cadastrada            |
| `POST`  | `/nextclass/atendimentos/solicitante/{solicitanteId}` | JWT             | Abre atendimento vinculado a um usuário          |
| `GET`   | `/nextclass/atendimentos`                             | JWT             | Lista todos (filtros: `tipo`, `status`)          |
| `GET`   | `/nextclass/atendimentos/{id}`                        | JWT             | Busca atendimento por ID                         |
| `GET`   | `/nextclass/atendimentos/solicitante/{id}`            | JWT             | Lista atendimentos de um solicitante (filtro: `status`) |
| `GET`   | `/nextclass/atendimentos/atendente/{id}`              | JWT             | Lista atendimentos de um atendente (filtro: `status`)   |
| `GET`   | `/nextclass/atendimentos/sem-atendente`               | JWT             | Lista atendimentos sem atendente atribuído       |
| `PATCH` | `/nextclass/atendimentos/{id}/atribuir-atendente/{atendenteId}` | JWT | Atribui um atendente ao atendimento       |
| `PATCH` | `/nextclass/atendimentos/{id}/status`                 | JWT             | Atualiza o status do atendimento                 |

**Valores de `tipo`:** `SUPORTE` · `ACADEMICO` · `FINANCEIRO` · `OUTRO`

**Valores de `status`:** `AGENDADO` · `CONFIRMADO` · `REALIZADO` · `CANCELADO`

---

## Exemplos de requisição

### Login

```http
POST /nextclass/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "minhasenha123"
}
```

### Registrar usuário

```http
POST /nextclass/auth/registrar
Content-Type: application/json

{
  "nome": "João da Silva",
  "cpf": "12345678909",
  "email": "joao@email.com",
  "senha": "minhasenha123",
  "telefone": "71987654321",
  "dataNascimento": "15/03/2000",
  "logradouro": "Rua das Flores",
  "numero": "123",
  "complemento": "Apto 45",
  "bairro": "Brotas",
  "cidade": "Salvador",
  "estado": "BA",
  "cep": "40000000",
  "role": "ALUNO"
}
```

### Matricular aluno em uma turma

```http
POST /nextclass/matriculas
Authorization: Bearer <token>
Content-Type: application/json

{
  "turmaId": 1
}
```

---

## Roadmap

- [ ] Migrar banco de dados para PostgreSQL em produção
- [ ] Adicionar testes de integração
- [ ] Implementar refresh token