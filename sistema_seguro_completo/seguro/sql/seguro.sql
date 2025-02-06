create database seguro;

-- Uso do banco de dados
USE seguro;

-- Tabela cliente
CREATE TABLE cliente (
   cpf VARCHAR(14) PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   rg VARCHAR(25) NOT NULL,
   telefone VARCHAR(18) NOT NULL
);

-- Tabela carro
CREATE TABLE carro (
   renavam VARCHAR(20) PRIMARY KEY,
   placa VARCHAR(10) NOT NULL,
   modelo VARCHAR(50) NOT NULL,
   fabricante VARCHAR(50) NOT NULL,
   cpf VARCHAR(14) NOT NULL,
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

create table login(
usuario varchar(255)
primary key,senha varchar(255)
);

ALTER TABLE ocorrencia MODIFY COLUMN renavam VARCHAR(20) DEFAULT 'N/A';
ALTER TABLE cliente MODIFY COLUMN cpf VARCHAR(14);
ALTER TABLE carro ADD CONSTRAINT unique_renavam UNIQUE (renavam);
ALTER TABLE ocorrencia MODIFY COLUMN renavam VARCHAR(255) NULL;
ALTER TABLE carro MODIFY COLUMN cpf VARCHAR(14);

Use seguro;
select * from login;

insert into login values
('aluno','$2a$10$x5LxuB7sf5Mx8wZyY111m.Hpp2IMqdoAUuH729l5By8eY25kwsK1S');


INSERT INTO cliente (cpf, nome, rg, telefone) VALUES
('123.456.789-01', 'João Silva', '1234567', '(11) 99999-9999'),
('234.567.890-12', 'Maria Oliveira', '2345678', '(11) 88888-8888'),
('345.678.901-23', 'Carlos Souza', '3456789', '(11) 77777-7777'),
('456.789.012-34', 'Ana Costa', '4567890', '(11) 66666-6666'),
('567.890.123-45', 'Pedro Santos', '5678901', '(11) 55555-5555'),
('678.901.234-56', 'Mariana Lima', '6789012', '(11) 44444-4444'),
('789.012.345-67', 'Lucas Pereira', '7890123', '(11) 33333-3333'),
('890.123.456-78', 'Fernanda Rocha', '8901234', '(11) 22222-2222'),
('901.234.567-89', 'Rafael Alves', '9012345', '(11) 11111-1111'),
('012.345.678-90', 'Juliana Martins', '0123456', '(11) 00000-0000');


INSERT INTO carro (renavam, placa, modelo, fabricante, cpf) VALUES
('12345678901', 'ABC-1234', 'Gol', 'Volkswagen', '123.456.789-01'),
('23456789012', 'DEF-5678', 'Onix', 'Chevrolet', '234.567.890-12'),
('34567890123', 'GHI-9101', 'HB20', 'Hyundai', '345.678.901-23'),
('45678901234', 'JKL-1121', 'Corolla', 'Toyota', '456.789.012-34'),
('56789012345', 'MNO-3141', 'Civic', 'Honda', '567.890.123-45'),
('67890123456', 'PQR-5161', 'Compass', 'Jeep', '678.901.234-56'),
('78901234567', 'STU-7181', 'T-Cross', 'Volkswagen', '789.012.345-67'),
('89012345678', 'VWX-9202', 'Renegade', 'Jeep', '890.123.456-78'),
('90123456789', 'YZA-1222', 'Ka', 'Ford', '901.234.567-89'),
('01234567890', 'BCD-3242', 'Argo', 'Fiat', '012.345.678-90');


INSERT INTO ocorrencia (local, descricao, data, renavam) VALUES
('Av. Paulista, 1000', 'Colisão com poste', '2025-01-15', '12345678901'),
('Rua Augusta, 500', 'Batida lateral', '2025-02-20', '23456789012'),
('Av. Brasil, 200', 'Pane elétrica', '2025-03-25', '34567890123'),
('Rua Oscar Freire, 300', 'Furto de espelho', '2025-04-10', '45678901234'),
('Av. Rebouças, 400', 'Pneu furado', '2025-05-05', '56789012345'),
('Rua da Consolação, 600', 'Farol quebrado', '2025-06-12', '67890123456'),
('Av. Faria Lima, 700', 'Colisão traseira', '2025-07-18', '78901234567'),
('Rua Haddock Lobo, 800', 'Problema no motor', '2025-08-22', '89012345678'),
('Av. Brigadeiro Faria Lima, 900', 'Arranhão na porta', '2025-09-30', '90123456789'),
('Rua Pamplona, 100', 'Vidro quebrado', '2025-10-05', '01234567890');

select * from login;
select * from cliente;
select * from carro;
select * from ocorrencia;
