# Desafio Backend - API de clientes

- API REST para gerenciamento de clientes e suas informações de contato (e-mails, telefones e endereços), com autenticação via JWT e controle de acesso por roles (ROLE_USER e ROLE_ADMIN). Desenvolvida com Spring Boot e banco de dados H2 em memória, facilita testes e integração com Postman.

# Estrutura do projeto
- O projeto está organizado nos seguintes diretórios:

- backend: API REST desenvolvida com Spring Boot, incluindo autenticação e autorização via Spring Security.

- database: (Opcional) scripts de inicialização do banco de dados H2.

# Requisitos

Para executar este projeto, você precisa ter instalado:

- Java 8
- Spring boot
- Hibernate
- Maven

# Backend (API RESTE)

O backend é uma aplicação Spring Boot que fornece APIs para gerenciamento de clientes e suas informações de contato, incluindo:

- Clientes

Cadastro, atualização, listagem e remoção.

Nome: mínimo de 3 caracteres e máximo de 100, podendo conter apenas letras, espaços e números, sendo obrigatório.

CPF: obrigatório, exibido com máscara e persistido sem máscara.

- Endereços

Preenchimento obrigatório do CEP.

Integrado com um serviço externo de consulta de CEP, permitindo alterar os dados retornados pelo serviço.

CEP: exibido com máscara e persistido sem máscara.

- Telefones

Permite cadastrar múltiplos telefones.

Pelo menos um telefone deve ser cadastrado.

Cada telefone deve ter tipo informado.

Máscara exibida de acordo com o tipo e persistido sem máscara.

- E-mails

Permite cadastrar múltiplos e-mails.

Pelo menos um e-mail deve ser cadastrado e válido.

Segurança

Autenticação via JWT.

Controle de acesso por roles: ROLE_USER e ROLE_ADMIN.

# Tecnologias 

Spring Boot

- Spring Data JPA
- Spring Web (REST)
- Spring Security + JWT
- H2 Database (em memória)

#Segurança 

- Autenticação via JWT.
- Controle de acesso por roles: `ROLE_USER` e `ROLE_ADMIN`.

**Usuários de teste:**

| Usuário | Senha        | Role       |
|---------|-------------|------------|
| admin   | 123qwe!@#   | ROLE_ADMIN |
| padrão  | 123qwe123   | ROLE_USER  |

**Endpoint de login:**

# Testando a API com Postman

Para facilitar os testes, incluímos uma Collection do Postman com todos os endpoints da API.

Como usar a Collection

Abra o Postman.

Clique em Import → Upload Files.

Selecione o arquivo desafio_backend.postman_collection.json que está na raiz do projeto.

Todos os endpoints estarão disponíveis para testes.

Dica: você pode criar variáveis no Postman para BASE_URL e TOKEN_JWT para facilitar os testes.


# Executando a API

Com H2 (recomendado)

Clone o repositório:

git clone <URL_DO_REPOSITORIO>
cd desafio_backend


Rode a aplicação com Maven:

./mvnw spring-boot:run


ou no Windows:

mvnw.cmd spring-boot:run


A API estará disponível em:

http://localhost:8080


