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
- **Memory Repository:** armazenamento de informações em memória, sem utilização de banco de dados nesta versão.

## Estrutura do projeto

```text
src/
└── inpetu/
    ├── Application.java
    ├── model/
    │   ├── Agenda.java
    │   ├── Contato.java
    │   └── Recurso.java
    ├── repository/
    │   ├── IAgendaRepository.java
    │   ├── IContatoRepository.java
    │   ├── IRecursoRepository.java
    │   └── memory/
    │       ├── AgendaMemoryRepository.java
    │       ├── ContatoMemoryRepository.java
    │       └── RecursoMemoryRepository.java
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
javac -d out $(find src -name "*.java")
java -cp out inpetu.Application
```

É necessário ter o JDK instalado e configurado no ambiente.

## Testes e validações

A aplicação possui testes de execução implementados na classe `Application.java`, que demonstram o funcionamento das operações e das regras de negócio.

Entre os cenários verificados estão:

- Cadastro e atualização de registros.
- Consultas e listagens.
- Reservas com horários conflitantes.
- Tentativas de utilização de contatos e recursos inexistentes.
- Tentativas de exclusão de registros vinculados.
- Validação de IDs e dados obrigatórios.

## Status do projeto

**Em desenvolvimento.**

As funcionalidades implementadas são executadas e verificadas por meio dos testes presentes na aplicação.

---

Projeto desenvolvido para fins acadêmicos no curso de Análise e Desenvolvimento de Sistemas.