# Gerenciador de Livros

Este é um projeto Spring Boot para um sistema de gerenciamento de livros. A aplicação permite aos usuários realizar operações CRUD (Criar, Ler, Atualizar, Excluir) em uma coleção de livros.

## Tecnologias Utilizadas

- **Java 21**: Versão mais recente do Java com suporte de longo prazo (LTS).
- **Spring Boot 3**: Framework para criar aplicações Java baseadas em Spring de forma rápida e fácil.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.
- **Spring Data JPA**: Facilita a implementação de repositórios baseados em JPA.
- **Thymeleaf**: Motor de templates Java para web e ambientes autônomos.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional.
- **Lombok**: Biblioteca para reduzir código boilerplate em classes Java.

## Funcionalidades

- **Adicionar Novos Livros**: Cadastre novos livros no sistema.
- **Listar Livros**: Visualize a lista completa de livros cadastrados.
- **Atualizar Livros**: Edite as informações de um livro existente.
- **Excluir Livros**: Remova livros do sistema.

## Pré-requisitos

Antes de começar, você precisará ter o seguinte instalado em sua máquina:

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/)

## Como Executar

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/GerenciadorLivro.git
   cd GerenciadorLivro
   ```

2. **Configure o banco de dados:**

   Abra o arquivo `src/main/resources/application.properties` e atualize as seguintes propriedades com suas credenciais do MySQL:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Execute a aplicação:**

   Você pode executar a aplicação usando o Maven Wrapper:

   ```bash
   ./mvnw spring-boot:run
   ```

   A aplicação estará disponível em `http://localhost:8080`.
