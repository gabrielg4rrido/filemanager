# FileManager

FileManager é uma aplicação web para gerenciar arquivos e diretórios. A aplicação utiliza Angular no frontend e Spring Boot no backend.

## Tecnologias Utilizadas

- **Frontend**: Angular
- **Backend**: Spring Boot
- **Banco de dados**: PostgreSQL
- **Gerenciamento de Dependências**: Maven
- **Containerização**: Docker

## Pré-requisitos
- [Node.js](https://nodejs.org/) (versão 18 ou superior)
- [Angular CLI](https://angular.io/cli) (versão 18 ou superior)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (versão 17)
- [Maven](https://maven.apache.org/) (versão 3.8.4 ou superior)
- [Docker](https://www.docker.com/)

## Configuração do Ambiente
### Executando com Docker

1. Certifique-se de que o Docker está instalado e em execução.

2. Clone o repositório:

    ```sh
    git clone https://github.com/gabrielg4rrido/filemanager.git
    cd filemanager
    ```

3. Navegue até o diretório raiz do projeto e execute o comando:

    ```sh
    docker-compose up --build
    ```

   Isso irá construir e iniciar os contêineres Docker para o backend, frontend e banco de dados.


4. Acesse a aplicação:

   - Backend: [`http://localhost:8080`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgabrielgarrido%2FWorkspaces%2FPessoal%2Ffilemanager%2Ffront%2Ffile-manager%2Fsrc%2Fapp%2Fservice%2Fdiretorio.service.ts%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A85%2C%22character%22%3A16%7D%7D%5D%2C%22ccba5f79-d892-4984-a7a1-b5068af09fea%22%5D "Go to definition")
   - Frontend: [`http://localhost:4200`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgabrielgarrido%2FWorkspaces%2FPessoal%2Ffilemanager%2Ffront%2Ffile-manager%2FREADME.md%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A6%2C%22character%22%3A46%7D%7D%5D%2C%22ccba5f79-d892-4984-a7a1-b5068af09fea%22%5D "Go to definition")

### Backend

1. Clone o repositório:

    ```sh
    git clone https://github.com/gabrielg4rrido/filemanager.git
    cd filemanager
    ```

2. Configure o banco de dados no arquivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/filemanager
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Compile e execute o backend:

    ```sh
    ./mvnw clean package
    java -jar target/*.jar
    ```

    O backend estará disponível em [`http://localhost:8080`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgabrielgarrido%2FWorkspaces%2FPessoal%2Ffilemanager%2Ffront%2Ffile-manager%2Fsrc%2Fapp%2Fservice%2Fdiretorio.service.ts%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A85%2C%22character%22%3A16%7D%7D%5D%2C%22ccba5f79-d892-4984-a7a1-b5068af09fea%22%5D "Go to definition").

### Frontend

1. Navegue até o diretório do frontend:

    ```sh
    cd front/file-manager
    ```

2. Instale as dependências do projeto:

    ```sh
    npm install
    ```

3. Execute o servidor de desenvolvimento:

    ```sh
    ng serve
    ```

    O frontend estará disponível em [`http://localhost:4200`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgabrielgarrido%2FWorkspaces%2FPessoal%2Ffilemanager%2Ffront%2Ffile-manager%2FREADME.md%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A6%2C%22character%22%3A46%7D%7D%5D%2C%22ccba5f79-d892-4984-a7a1-b5068af09fea%22%5D "Go to definition").

## Estrutura do Projeto

```plaintext
.gitignore
.idea/
.mvn/
database/
docker-compose.yml
Dockerfile
front/
    file-manager/
        .angular/
        .editorconfig
        .gitignore
        angular.json
        Dockerfile
        package.json
        public/
        src/
            app/
            assets/
            environments/
mvnw
mvnw.cmd
pom.xml
README.md
src/
    main/
        java/
            com/
                gg/
                    filemanager/
                        controller/
                        service/
                        repository/
                        model/
                        dto/
        resources/
            application.properties
    test/
target/