# Sistema de Reserva de Recursos — InPETU Hub

Projeto acadêmico desenvolvido em Java para a unidade curricular de **Programação Orientada a Objetos (POO)** do curso de Análise e Desenvolvimento de Sistemas.

## Sobre o projeto

O Sistema de Reserva de Recursos do InPETU Hub tem como objetivo organizar o gerenciamento de contatos, recursos e reservas, aplicando conceitos de orientação a objetos e boas práticas de desenvolvimento de software.

A aplicação permite cadastrar e consultar informações, administrar alterações e controlar as reservas dos recursos, respeitando as regras de negócio estabelecidas para o sistema.

## Funcionalidades

### Contatos
- Cadastro de contatos.
- Consulta por ID e nome.
- Atualização de informações.
- Listagem de contatos.
- Validação de campos obrigatórios.
- Bloqueio da exclusão de contatos vinculados a reservas.

### Recursos
- Cadastro de recursos.
- Consulta por ID.
- Atualização de informações.
- Listagem de recursos.
- Bloqueio da exclusão de recursos vinculados a reservas.

### Reservas
- Cadastro de reservas.
- Consulta por ID.
- Atualização de reservas existentes.
- Listagem de reservas.
- Validação dos horários de início e término.
- Verificação de disponibilidade dos recursos.
- Bloqueio de reservas com horários conflitantes.
- Validação da existência dos contatos e recursos associados.

## Tecnologias e conceitos

- **Java:** linguagem utilizada no desenvolvimento.
- **Programação Orientada a Objetos (POO):** organização do sistema em classes e objetos.
- **Princípios SOLID:** aplicados à organização e separação de responsabilidades.
- **Arquitetura em camadas:** Model, Repository e Service.
- **Memory Repository:** armazenamento em memória, usado nos cenários da aplicação sem argumentos.
- **MySQL e JDBC:** repositórios de contatos, recursos e agendas, além de consultas aos tipos de contato e espaço.

## Estrutura do projeto

```text
src/
└── inpetu/
    ├── Application.java
    ├── database/
    │   ├── ConexaoMySQL.java
    │   └── TesteConexao.java
    ├── model/
    │   ├── Agenda.java
    │   ├── Contato.java
    │   ├── Recurso.java
    │   ├── TipoContato.java
    │   └── TipoEspaco.java
    ├── repository/
    │   ├── IAgendaRepository.java
    │   ├── IContatoRepository.java
    │   ├── IRecursoRepository.java
    │   ├── ITipoContatoRepository.java
    │   ├── ITipoEspacoRepository.java
    │   ├── memory/
    │   │   ├── AgendaMemoryRepository.java
    │   │   ├── ContatoMemoryRepository.java
    │   │   └── RecursoMemoryRepository.java
    │   └── mysql/
    │       ├── AgendaMySQLRepository.java
    │       ├── ContatoMySQLRepository.java
    │       ├── RecursoMySQLRepository.java
    │       ├── TipoContatoMySQLRepository.java
    │       └── TipoEspacoMySQLRepository.java
    └── service/
        ├── AgendaService.java
        ├── ContatoService.java
        ├── RecursoService.java
        ├── IAgendaService.java
        ├── IContatoService.java
        └── IRecursoService.java
```

### Responsabilidades das camadas

- **Model:** representa as entidades e seus dados.
- **Repository:** define e implementa as operações de armazenamento e recuperação.
- **Service:** concentra as regras de negócio, validações e operações do sistema.
- **Application:** inicializa a aplicação e executa os testes das funcionalidades.

## Execução

O projeto pode ser executado por meio de uma IDE, como IntelliJ IDEA, utilizando a classe principal:

```text
inpetu.Application
```

Também é possível compilar e executar pelo terminal Linux, a partir da pasta raiz do projeto:

```bash
javac -encoding UTF-8 -cp 'lib/*' -d out $(find src -name '*.java')
java -cp out inpetu.Application
```

É necessário ter o JDK instalado e configurado no ambiente.
Sem argumentos, a aplicação executa os 35 cenários em memória, sem acessar o MySQL.

## Configuração da conexão MySQL

O projeto usa o driver `lib/mysql-connector-j-9.7.0.jar`, sem Maven ou Gradle.
As configurações do IntelliJ são locais e não estão no Git.

### Driver no IntelliJ

1. Abra **File → Project Structure → Modules → Dependencies**.
2. Clique em **+ → JARs or Directories**.
3. Selecione `lib/mysql-connector-j-9.7.0.jar` e confirme.

### Banco e dados iniciais

O MySQL deve estar rodando em `localhost:3306`, com o banco `inpetu_hub`.
Para uma instalação nova, execute `sql/schema.sql` no Workbench e depois
`sql/dados_iniciais.sql`. O segundo script cadastra 2 tipos de contato,
6 tipos de espaço e 10 recursos. Execute-o apenas uma vez, com essas tabelas vazias.
Se o banco já foi preparado, não execute os scripts novamente.

### Variáveis de ambiente no IntelliJ

1. Abra **Run → Edit Configurations…** e crie uma configuração **Application**.
2. Defina a classe principal como `inpetu.database.TesteConexao` e use o módulo `InPETU`.
3. Em **Environment variables**, adicione:

   | Nome | Valor |
   |---|---|
   | `INPETU_DB_USUARIO` | Usuário do MySQL |
   | `INPETU_DB_SENHA` | Senha desse usuário no MySQL |

4. Deixe **Store as project file** desmarcado e salve.

As variáveis precisam estar na configuração que executa o teste.
Não coloque a senha no código, no README ou em arquivos enviados ao Git.

### Teste da conexão

Execute a configuração de `inpetu.database.TesteConexao`. Se funcionar, aparecerá:

```text
Conexão com o banco inpetu_hub realizada com sucesso!
```

O teste abre e fecha a conexão, sem consultar, inserir ou alterar registros.
Em caso de falha, a exceção aparece no console.
A classe `inpetu.Application`, sem argumentos, continua executando os cenários em memória.

No terminal Linux, as variáveis precisam estar definidas na mesma sessão que
executa o Java. Para preencher sem escrever a senha no comando ou no histórico:

```bash
read -r -p 'Usuário MySQL: ' INPETU_DB_USUARIO
read -r -s -p 'Senha MySQL: ' INPETU_DB_SENHA
echo
export INPETU_DB_USUARIO INPETU_DB_SENHA
```

Depois, compile e execute o teste:

```bash
javac -encoding UTF-8 -cp 'lib/*' -d out $(find src -name '*.java')
java -cp 'out:lib/*' inpetu.database.TesteConexao
```

### Executar a aplicação com MySQL

No IntelliJ, crie uma configuração para `inpetu.Application`, informe `--mysql`
em **Program arguments** e preencha as variáveis de ambiente nessa configuração.
Mantenha **Store as project file** desmarcado.

No terminal Linux, após compilar e definir as variáveis:

```bash
java -cp 'out:lib/*' inpetu.Application --mysql
```

Esse modo apenas lista contatos, recursos e agendas existentes por meio dos serviços.
Ele não cadastra os dados de teste da execução em memória nem oferece um menu de cadastro.
Os métodos de cadastro, atualização e exclusão estão disponíveis nos serviços.

### Regras da integração

- Contatos precisam de nome e pelo menos CPF ou CNPJ; telefone é opcional.
- Recursos precisam de nome e capacidade não negativa. Capacidade zero é permitida.
- No MySQL, contatos e recursos precisam estar vinculados a tipos existentes.
- Novos registros no MySQL devem começar com ID zero; o banco gera o ID e o repositório o atribui ao objeto.
- Os repositórios em memória de contatos, recursos e agendas também geram IDs para objetos com ID zero e preservam IDs manuais positivos.
- Agendas exigem contato e recurso cadastrados e início anterior ao fim, sem frações de segundo nesses dois horários.
- A data de criação salva é preservada nas atualizações. O banco armazena as datas com precisão de segundos.
- Os serviços bloqueiam conflitos de horário e a exclusão de contatos e recursos vinculados a agendas.

## Testes e validações

A aplicação possui testes de execução implementados na classe `Application.java`, que demonstram o funcionamento das operações e das regras de negócio.

Entre os cenários verificados estão:

- Cadastro e atualização de registros.
- Consultas e listagens.
- Reservas com horários conflitantes.
- Tentativas de utilização de contatos e recursos inexistentes.
- Tentativas de exclusão de registros vinculados.
- Validação de IDs e dados obrigatórios.

Também foram executados testes temporários em memória e no MySQL para os
repositórios e serviços. Os testes de integração usaram registros fictícios,
removidos ao final, e compararam os dados das cinco tabelas antes e depois.
Esses testes temporários não fazem parte dos 35 cenários da `Application`
e não estão versionados no projeto.

### Pendências e limitações

- Comprovar a persistência entre execuções separadas da aplicação na Fase E.
- A verificação de conflitos consulta as agendas existentes e cobre operações sequenciais.
  Ainda não há proteção transacional contra duas reservas simultâneas para o mesmo recurso.
- Os repositórios de tipos oferecem apenas consulta por ID e listagem; o cadastro inicial é feito pelo SQL.
- Interface gráfica, otimização de consultas e testes de desempenho ficam para as etapas correspondentes.

## Status do projeto

**Em desenvolvimento.**

Repositórios MySQL implementados e integrados aos serviços, com execução em memória preservada.
O modo `--mysql` e os testes funcionais básicos de integração foram verificados.
A documentação e a implementação da Fase D estão preparadas para revisão e versionamento;
as verificações restantes de persistência fazem parte da Fase E.

---

Projeto desenvolvido para fins acadêmicos no curso de Análise e Desenvolvimento de Sistemas.
