# Estrutura do projeto
O projeto está organizado nos seguintes diretórios:

- backend: API REST desenvolvida com Spring Boot, incluindo autenticação e autorização via Spring Security.

- database: (Opcional) scripts de inicialização do banco de dados H2.
# Requisitos


Para executar este projeto, você precisa ter instalado:

- Java 8
- Spring/Springboot
- Hibernate
- Maven

# Backend (API RESTE)

O backend é uma aplicação Spring Boot que fornece APIs para gerenciamento de clientes e suas informações de contato, incluindo:

-Clientes: cadastro, atualização, listagem e remoção. Sebdo o nome com mínimo de 3 caracteres e máximo de 100 caracteres , podendo permitir apenas letras, espaços e números e também o campo sendo obrigatório.  CPF sempre ser mostrado com máscara e persistido sem máscara , sendo um campo obrigatório.

-Emails: podendo ser cadastrados múltiplos e-mails , pelo menos um e-mail deve ser cadastrado e um e-mail deve ser valido .

-Endereços: obrigatorio o preenchimento do CEP , deve ser integrado com um serviço externo de consulta , podendo ser alterados os dados qu vieram do serviço de consulta , o CEP deve ser mostrado com máscara e persistido sem máscara.

-Telefones: podem ser cadastrados múltiplos telefones , pelo menos um deve ser cadastrado , no cadastro deve ser informado o tipo , a máscara deve ser de acordo com o tipo e o telefone deve ser mostrado com máscara e persistido sem máscara.

-Segurança: autenticação JWT e controle de acesso por roles (ROLE_USER e ROLE_ADMIN).

# Tecnologias 

Spring Boot

- Spring Data JPA
- Spring Web (REST)
- Spring Security + JWT
- H2 Database (em memória)

Configuração

A aplicação pode ser configurada através de variáveis de ambiente:



