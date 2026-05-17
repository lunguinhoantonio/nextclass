# NextClass

API RESTful para gestão de usuários em uma plataforma educacional, desenvolvida com Spring Boot. O sistema suporta três perfis de usuário — aluno, professor e coordenador — e está preparado para a integração futura de autenticação JWT, matrículas e atendimentos.

---

## Tecnologias

- **Java 21**
- **Spring Boot 3.5**
  - Spring Web
  - Spring Data JPA
  - Spring Security
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
# Clone o repositório
git clone https://github.com/seu-usuario/NextClass.git
cd NextClass

# Execute com o Maven Wrapper
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## Documentação da API

O Swagger UI está disponível após a inicialização:

```
http://localhost:8080/swagger-ui.html
```

---

## Banco de dados (H2 Console)

O console do banco de dados em memória pode ser acessado em:

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:nextclass` |
| Usuário | `sa` |
| Senha | *(vazio)* |

---

## Estrutura do projeto

```
src/
└── main/
    ├── java/edu/technosplay/NextClass/
    │   ├── config/          # Configurações de segurança (SecurityConfig)
    │   ├── controller/      # Controllers REST (Auth, Usuario)
    │   ├── dto/
    │   │   ├── request/     # DTOs de entrada (UsuarioRequest)
    │   │   └── response/    # DTOs de saída (UsuarioResponse, PageResponse)
    │   ├── exception/       # Exceções de negócio personalizadas
    │   ├── mapper/          # Mapeamento entre entidades e DTOs
    │   ├── model/           # Entidades JPA
    │   │   └── enums/       # Role (ALUNO, PROFESSOR, COORDENADOR)
    │   ├── repository/      # Repositórios Spring Data
    │   └── service/         # Interfaces e implementações de serviço
    └── resources/
        └── application.yaml
```

---

## Endpoints

### Autenticação — `/nextclass/auth`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/nextclass/auth/registrar` | Cadastra um novo usuário |

### Usuários — `/nextclass/usuarios`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/nextclass/usuarios` | Lista usuários (filtros opcionais: `role`, `ativo`) |
| `GET` | `/nextclass/usuarios/{id}` | Busca usuário por ID |
| `PATCH` | `/nextclass/usuarios/{id}/ativar` | Ativa a conta de um usuário |
| `PATCH` | `/nextclass/usuarios/{id}/desativar` | Desativa a conta de um usuário |

---

## Perfis de usuário (Roles)

| Role | Descrição |
|---|---|
| `ALUNO` | Estudante da plataforma |
| `PROFESSOR` | Docente |
| `COORDENADOR` | Administrador / coordenação |

---

## Exemplo de requisição — Cadastro de usuário

**`POST /nextclass/auth/registrar`**

```json
{
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "email": "maria@email.com",
  "senha": "senha123",
  "telefone": "71999990000",
  "dataNascimento": "15/06/1998",
  "logradouro": "Rua das Flores",
  "numero": "42",
  "complemento": "Apto 3",
  "bairro": "Centro",
  "cidade": "Salvador",
  "estado": "BA",
  "cep": "40000000",
  "role": "ALUNO"
}
```

---

## Observações sobre segurança

A autenticação via **JWT** está preparada na estrutura do projeto (dependências comentadas no `pom.xml` e filtros no `SecurityConfig`), mas ainda não está ativa. Atualmente todos os endpoints do prefixo `/nextclass/**` são públicos. O controle por roles com `@PreAuthorize` também está comentado nos controllers, pronto para ser habilitado quando a autenticação JWT for implementada.

---

## Roadmap

- [ ] Implementar autenticação JWT
- [ ] Adicionar entidade `Curso` e gerenciamento de turmas
- [ ] Implementar `Matricula` (relacionamento Aluno ↔ Curso)
- [ ] Implementar `Atendimento`
- [ ] Migrar banco de dados para PostgreSQL em produção
- [ ] Adicionar testes de integração
