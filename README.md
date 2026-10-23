# Sistema de Reserva de Recursos — InPETU Hub

Projeto acadêmico desenvolvido em Java para a unidade curricular de **Programação Orientada a Objetos (POO)** do curso de Análise e Desenvolvimento de Sistemas.

## Sobre o projeto

O objetivo deste projeto é desenvolver um sistema para gerenciar contatos, recursos e reservas do InPETU Hub, como parte de uma atividade prática da faculdade.

A aplicação busca organizar a lógica de cadastro e gerenciamento de recursos, permitindo controlar as reservas e verificar a disponibilidade dos recursos durante determinados períodos.

## Funcionalidades

- **Contatos:** cadastro, consulta, edição e listagem.
- **Recursos:** cadastro, consulta, edição e listagem de salas, auditórios e outros recursos.
- **Reservas:** cadastro, consulta, edição e listagem de reservas.
- **Validação de disponibilidade:** impedir reservas em períodos já ocupados.
- **Regras de negócio:** impedir a exclusão de contatos e recursos vinculados a reservas.

*As funcionalidades serão documentadas e atualizadas conforme o desenvolvimento do projeto.*

## Tecnologias e conceitos

- Java 25
- Programação Orientada a Objetos (POO)
- Princípios SOLID
- Organização em camadas: Model, Repository e Service
- Armazenamento em memória (*Memory Repository*)

## Estrutura do projeto

A organização inicial do código está dividida em camadas com responsabilidades distintas:

- `model/` — classes que representam as entidades do sistema.
- `memory repository/` — armazenamento e recuperação de dados em memória.
- `service/` — implementação das regras de negócio e operações do sistema.

A estrutura poderá evoluir conforme novas funcionalidades forem implementadas.

## Execução

O projeto está em desenvolvimento. As instruções para compilação e execução serão adicionadas conforme a estrutura de inicialização da aplicação for definida.

## Objetivo acadêmico

Este trabalho tem como finalidade aplicar os conceitos estudados em Programação Orientada a Objetos, praticando a modelagem de classes, a organização do código, a implementação de regras de negócio e o tratamento de erros.

## Status

**Em desenvolvimento.**

---

Projeto desenvolvido para fins educacionais no curso de Análise e Desenvolvimento de Sistemas.
