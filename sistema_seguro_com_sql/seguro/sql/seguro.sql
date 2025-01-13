
create database seguro;

-- Uso do banco de dados
USE seguro;

-- Tabela cliente
CREATE TABLE cliente (
   cpf VARCHAR(11) PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   rg VARCHAR(20) NOT NULL,
   telefone VARCHAR(15) NOT NULL
);

-- Tabela carro
CREATE TABLE carro (
   renavam VARCHAR(20) PRIMARY KEY,
   placa VARCHAR(10) NOT NULL,
   modelo VARCHAR(50) NOT NULL,
   fabricante VARCHAR(50) NOT NULL,
   cpf VARCHAR(11) NOT NULL,
   FOREIGN KEY (cpf) REFERENCES cliente(cpf) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabela ocorrência
CREATE TABLE ocorrencia (
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   local VARCHAR(100) NOT NULL,
   descricao TEXT NOT NULL,
   data DATE NOT NULL,
   renavam VARCHAR(20) NOT NULL,
   FOREIGN KEY (renavam) REFERENCES carro(renavam) ON DELETE CASCADE ON UPDATE CASCADE
);

select * from cliente; 

ALTER TABLE ocorrencia MODIFY COLUMN renavam VARCHAR(20) DEFAULT 'N/A';

ALTER TABLE cliente MODIFY COLUMN cpf VARCHAR(14);

ALTER TABLE carro ADD CONSTRAINT unique_renavam UNIQUE (renavam);

