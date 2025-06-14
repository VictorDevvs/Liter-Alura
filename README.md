-----

# Literalura: Sua Biblioteca Pessoal de Livros Gutendex  

## Sobre o Projeto

Literalura é uma aplicação de linha de comando (CLI) desenvolvida como parte do desafio proposto pela Alura, através do programa **ONE (Oracle Next Education)**. O objetivo principal deste projeto é consolidar e aprofundar os conhecimentos em **Spring Boot**, **manipulação de APIs REST** e **persistência de dados com JPA/Hibernate**.

A aplicação permite interagir com a API [Gutendex](https://gutendex.com/), uma vasta biblioteca de livros gratuitos, possibilitando a busca e o gerenciamento de informações sobre livros e autores. Os dados buscados são armazenados em um banco de dados local MySQL, garantindo que você possa acessar seus livros favoritos mesmo offline e evitar buscas repetitivas na API.

## Funcionalidades

  * **Buscar Livro por Título:** Encontra livros na API Gutendex e os salva no banco de dados local.
  * **Buscar Livro por ID:** Busca um livro específico na API Gutendex usando seu ID e o persiste localmente.
  * **Buscar Livros por Idioma:** Filtra e exibe livros da API Gutendex com base no idioma, salvando-os.
  * **Buscar Livros por Autor:** Permite encontrar livros de um autor específico na API e armazená-los.
  * **Buscar Livros por Tópicos:** Busca livros relacionados a um tema ou categoria na API e os persiste.

## Tecnologias Utilizadas

  * **Java 21:** Linguagem de programação.
  * **Spring Boot 3.5.0:** Framework para desenvolvimento rápido de aplicações Java.
  * **Spring WebFlux (WebClient):** Para consumo de APIs REST de forma reativa.
  * **Spring Data JPA:** Para interação com o banco de dados.
  * **Hibernate:** Implementação da especificação JPA.
  * **MySQL:** Banco de dados relacional para persistência dos dados.
  * **Maven:** Ferramenta de automação de construção de projetos.
  * **Lombok:** Para reduzir boilerplate code (Getters, Setters, Construtores, etc.).

## Como Executar o Projeto

Siga os passos abaixo para configurar e executar a aplicação Literalura em sua máquina.

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

  * **JDK 21**.
  * **Maven** 3.5.0.
  * **MySQL Server** e um cliente MySQL (como MySQL Workbench, DBeaver ou linha de comando).

### Configuração do Banco de Dados MySQL

1.  **Crie o banco de dados:** No seu MySQL Server, crie um banco de dados chamado `gutendex_db`.

    ```sql
    CREATE DATABASE gutendex_db;
    ```

2.  **Configure o `application.properties`:** Abra o arquivo `src/main/resources/application.properties` e atualize as configurações do banco de dados com suas credenciais do MySQL.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/gutendex_db?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=sua_senha_do_mysql
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```

    **Lembre-se de substituir `sua_senha_do_mysql` pela sua senha real do MySQL.**

### Executando a Aplicação

1.  **Clone o Repositório (se ainda não o fez):**

    ```bash
    git clone https://github.com/seu-usuario/literalura.git
    cd literalura
    ```

2.  **Compile o Projeto:** Navegue até a pasta raiz do projeto (`literalura`) e execute o Maven para compilar.

    ```bash
    mvn clean install
    ```

3.  **Execute a Aplicação:** Após a compilação, você pode executar o JAR gerado.

    ```bash
    java -jar target/literalura-0.0.1-SNAPSHOT.jar
    ```

    Alternativamente, se você estiver usando um IDE como IntelliJ IDEA ou Eclipse, pode simplesmente executar a classe `LiteraluraApplication.java` como uma aplicação Spring Boot.

### Interagindo com a Aplicação

Ao executar a aplicação, você verá um menu interativo no console. Digite o número correspondente à opção desejada e siga as instruções.

```
--- Menu LiterAlura ---
1. Buscar livro pelo título
2. Buscar livro por ID
3. Buscar livros por idioma
4. Buscar livros por autor
5. Buscar livros por tópicos
6. Sair
Escolha uma opção:
```

-----

## Contribuições

Este projeto foi desenvolvido como parte de um desafio educacional. Contribuições são bem-vindas, especialmente para melhorias ou novas funcionalidades que se alinhem com os objetivos do projeto. Sinta-se à vontade para abrir issues ou pull requests\!
