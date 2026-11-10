-- Banco de dados do InPETU Hub.
-- Script para revisão. Ainda não foi executado.

CREATE DATABASE IF NOT EXISTS inpetu_hub;
USE inpetu_hub;

CREATE TABLE tipo_contato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    CHECK (TRIM(nome) <> '')
);

CREATE TABLE tipo_espaco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    CHECK (TRIM(nome) <> '')
);

CREATE TABLE contato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14),
    cnpj VARCHAR(18),
    email VARCHAR(150),
    telefone VARCHAR(20),
    tipo_contato_id INT NOT NULL,
    FOREIGN KEY (tipo_contato_id) REFERENCES tipo_contato(id),
    CHECK (TRIM(nome) <> ''),
    -- Deve ter pelo menos um documento preenchido.
    -- TRIM retira os espaços das pontas do texto.
    CHECK (
        (cpf IS NOT NULL AND TRIM(cpf) <> '')
        OR (cnpj IS NOT NULL AND TRIM(cnpj) <> '')
    )
);

CREATE TABLE recurso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    capacidade INT NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    tipo_espaco_id INT NOT NULL,
    FOREIGN KEY (tipo_espaco_id) REFERENCES tipo_espaco(id),
    CHECK (TRIM(nome) <> ''),
    CHECK (capacidade >= 0),
    CHECK (ativo IN (0, 1))
);

CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contato_id INT NOT NULL,
    recurso_id INT NOT NULL,
    inicio DATETIME NOT NULL,
    fim DATETIME NOT NULL,
    finalidade VARCHAR(255),
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    motivo_recusa VARCHAR(255),
    FOREIGN KEY (contato_id) REFERENCES contato(id),
    FOREIGN KEY (recurso_id) REFERENCES recurso(id),
    CHECK (inicio < fim),
    CHECK (TRIM(status) <> '')
);
