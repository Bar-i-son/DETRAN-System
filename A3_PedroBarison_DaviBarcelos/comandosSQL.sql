CREATE DATABASE IF NOT EXISTS detran;
USE detran;
CREATE TABLE IF NOT EXISTS proprietarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS veiculos (
	id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(7) NOT NULL UNIQUE, -- Placa Mercosul tem 7 caracteres alfanuméricos
    marca VARCHAR(100),
    modelo VARCHAR(100),
    ano INT,
    cor VARCHAR(50),
    id_proprietario INT,
    FOREIGN KEY (id_proprietario) REFERENCES proprietarios(id)
);
CREATE TABLE IF NOT EXISTS transferencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo INT NOT NULL,
    id_proprietario_antigo INT NOT NULL,
    id_proprietario_novo INT NOT NULL,
    data_transferencia VARCHAR(10), -- Formato 'DD/MM/AAAA'
    FOREIGN KEY (id_veiculo) REFERENCES veiculos(id),
    FOREIGN KEY (id_proprietario_antigo) REFERENCES proprietarios(id),
    FOREIGN KEY (id_proprietario_novo) REFERENCES proprietarios(id)
);
INSERT INTO proprietarios (nome, cpf) VALUES ('Joao Silva', '97576828021');
INSERT INTO proprietarios (nome, cpf) VALUES ('MARIA OLIVEIRA', '20574540024');
INSERT INTO proprietarios (nome, cpf) VALUES ('PEDRO SOUZA', '25982560006');
INSERT INTO proprietarios (nome, cpf) VALUES ('ANA PAULA COSTA', '57992766040');

INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario)
VALUES ('JFW6507', 'FIAT', 'PALIO', 2010, 'PRATA', 1);

INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario)
VALUES ('MOI9580', 'VW', 'GOL', 2015, 'BRANCO', 2);

INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario)
VALUES ('GZU1883', 'CHEVROLET', 'ONIX', 2020, 'PRETO', 2);

INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario)
VALUES ('KDA4B95 ', 'FORD', 'KA', 2018, 'AZUL', 3);

INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario)
VALUES ('NAN6211', 'HYUNDAI', 'HB20', 2022, 'VERMELHO', 4)