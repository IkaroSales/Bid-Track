CREATE TABLE `acao` (
  `cod_acao` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `descricao` varchar(20) NOT NULL,
  PRIMARY KEY(`cod_acao`)
);

CREATE TABLE `empresa` (
  `cod_empresa` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `razao_social` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `nome_fantasia` varchar(100) NOT NULL,
  `complemento` varchar(20) NULL,
  `bairro` varchar(25) NOT NULL,
  `cidade` varchar(25) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_empresa`)
);

CREATE TABLE `destinatario` (
  `cod_destinatario` int UNSIGNED NOT NULL,
  `cod_empresa` int UNSIGNED NOT NULL,
  `razao_social` varchar(100) NOT NULL,
  `nome_fantasia` varchar(100) NULL,
  `tipo_pessoa` CHAR NOT NULL,
  `cnpj/cpf` varchar(18) NOT NULL,
  `email` varchar(60) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `complemento` varchar(20) NULL,
  `bairro` varchar(25) NOT NULL,
  `cidade` varchar(25) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `latitude` int NOT NULL,
  `longitude` int NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_destinatario`)
);


CREATE TABLE `estabelecimento` (
  `cod_estabelecimento` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `razao_social` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `complemento` varchar(20) NULL,
  `bairro` varchar(25) NOT NULL,
  `cidade` varchar(25) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_estabelecimento`)
);


CREATE TABLE `motorista` (
  `cod_motorista` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `nome` varchar(80) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `pontuacao` int UNSIGNED NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `complemento` varchar(20) NULL,
  `bairro` varchar(25) NOT NULL,
  `cidade` varchar(25) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `tipo_carteira` varchar(5) NOT NULL,
  `validade_carteira` DATE NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_motorista`)
);


CREATE TABLE `ocorrencia` (
  `cod_ocorrencia` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `descricao` varchar(20) NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_ocorrencia`)
);


CREATE TABLE `tipo_veiculo` (
  `cod_tipo_veiculo` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `descricao` varchar(30) NOT NULL,
  `peso` DOUBLE NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_tipo_veiculo`)
);


CREATE TABLE `transportadora` (
  `cod_transportadora` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `razao_social` varchar(100) NOT NULL,
  `nome_fantasia` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `complemento` varchar(20) NULL,
  `bairro` varchar(25) NOT NULL,
  `cidade` varchar(25) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_transportadora`)
);


CREATE TABLE `usuario` (
  `cod_usuario` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `nome` varchar(80) NOT NULL,
  `email` varchar(60) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `senha` varchar(15) NOT NULL,
  `perfil` CHAR NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_usuario`)
);


CREATE TABLE `veiculo` (
  `cod_veiculo` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `cod_motorista` int UNSIGNED NOT NULL,
  `cod_tipo_veiculo` int UNSIGNED NOT NULL,
  `placa` varchar(8) NOT NULL,
  `chassi` varchar(17) NOT NULL,
  `proprio` CHAR NOT NULL,
  `capacidade` int UNSIGNED NOT NULL,
  `antt` int UNSIGNED NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_veiculo`)
);

CREATE TABLE `log_capa` (
  `cod_log` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_empresa` int UNSIGNED NOT NULL,
  `cod_acao` int UNSIGNED NOT NULL,
  `cod_usuario` int UNSIGNED NOT NULL,
  `data_log` DATE NOT NULL,
  `modulo` varchar(30) NOT NULL,
  PRIMARY KEY(`cod_log`)
);

CREATE TABLE `log_detalhe` (
  `cod_log_dtl` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_log` int UNSIGNED NOT NULL,
  `detalhe` varchar(300) NOT NULL,
  PRIMARY KEY(`cod_log_dtl`)
);


CREATE TABLE `romaneio` (
  `cod_romaneio` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_veiculo` int UNSIGNED NOT NULL,
  `cod_transportadora` int UNSIGNED NOT NULL,
  `cod_motorista` int UNSIGNED NOT NULL,
  `finalizado` BIT NOT NULL,
  `ofertar_viagem` BIT NOT NULL,
  `integrado` BIT NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_romaneio`)
);

CREATE TABLE `entrega` (
  `cod_entrega` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_romaneio` int UNSIGNED NOT NULL,
  `cod_destinatario` int UNSIGNED NOT NULL,
  `cod_status` int UNSIGNED NOT NULL,
  `peso_carga` FLOAT NOT NULL,
  `imagem` BLOB NULL,
  `nota_fiscal` int UNSIGNED NOT NULL,
  `finalizado` BIT NOT NULL,
  `situacao` BIT NOT NULL,
  PRIMARY KEY(`cod_entrega`)
);

CREATE TABLE `status_entregas` (
  `cod_status` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `cod_ocorrencia` int UNSIGNED NOT NULL,
  `data_ocorrencia` DATE NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  PRIMARY KEY(`cod_status`),
  INDEX STATUS_NOTA_FKIndex2(`cod_ocorrencia`)
);


ALTER TABLE `destinatario` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `estabelecimento` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `motorista` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `ocorrencia` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `tipo_veiculo` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `usuario` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `transportadora` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `veiculo` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `veiculo` ADD FOREIGN KEY (`cod_motorista`) REFERENCES `motorista`(`cod_motorista`);
ALTER TABLE `veiculo` ADD FOREIGN KEY (`cod_tipo_veiculo`) REFERENCES `tipo_veiculo`(`cod_tipo_veiculo`);
ALTER TABLE `entrega` ADD FOREIGN KEY (`cod_romaneio`) REFERENCES `romaneio`(`cod_romaneio`);
ALTER TABLE `entrega` ADD FOREIGN KEY (`cod_destinatario`) REFERENCES `destinatario`(`cod_destinatario`);
ALTER TABLE `entrega` ADD FOREIGN KEY (`cod_status`) REFERENCES `status_entregas`(`cod_status`);
ALTER TABLE `log_capa` ADD FOREIGN KEY (`cod_empresa`) REFERENCES `empresa`(`cod_empresa`);
ALTER TABLE `log_capa` ADD FOREIGN KEY (`cod_acao`) REFERENCES `acao`(`cod_acao`);
ALTER TABLE `log_capa` ADD FOREIGN KEY (`cod_usuario`) REFERENCES `usuario`(`cod_usuario`);
ALTER TABLE `log_detalhe` ADD FOREIGN KEY (`cod_log`) REFERENCES `log_capa`(`cod_log`);
ALTER TABLE `romaneio` ADD FOREIGN KEY (`cod_veiculo`) REFERENCES `veiculo`(`cod_veiculo`);
ALTER TABLE `romaneio` ADD FOREIGN KEY (`cod_transportadora`) REFERENCES `transportadora`(`cod_transportadora`);
ALTER TABLE `romaneio` ADD FOREIGN KEY (`cod_motorista`) REFERENCES `motorista`(`cod_motorista`);
ALTER TABLE `status_entregas` ADD FOREIGN KEY (`cod_ocorrencia`) REFERENCES `ocorrencia`(`cod_ocorrencia`);

INSERT INTO `empresa` (`cod_empresa`, `razao_social`, `cnpj`, `logradouro`, `numero`, `nome_fantasia`, `complemento`, `bairro`, `cidade`, `uf`, `cep`, `latitude`, `longitude`, `situacao`) VALUES
(1, 'Renan Yasmin', '80.341.029/0001-76', 'Rua Seis', '396', 'Renan e Yasmin Marketing Ltda', NULL, 'Jardim São Paulo', 'Petrolina', 'PE', '56314-040', 0, 0, b'0'),
(3, 'Sara Alice', '66.875.292/0001-14', 'Rua Dona Maria de Souza', '705', 'Sara Alice Entregas Expressas Ltda', NULL, 'Santo Amaro', 'Recife', 'PE', '50040-330', 0, 0, b'0'),
(4, 'Ryan Carolina', '05.127.366/0001-40', 'Rua Laranjeira do Sul', '237', 'Ryan e Carolina Entregas Expressas Ltda', NULL, 'Nazaré', 'Camaragibe', 'PE', '54753-115', 0, 0, b'0');

INSERT INTO `motorista` (`cod_motorista`, `cod_empresa`, `nome`, `cpf`, `pontuacao`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `uf`, `cep`, `latitude`, `longitude`, `tipo_carteira`, `validade_carteira`, `situacao`) VALUES
(1, 1, 'César Lucas Moura', '933.375.288-99', 0, 'Travessa Santa Márcia', '814', NULL, 'Pici', 'Fortaleza', 'CE', '60440-572', 0, 0, 'CDE', '2020-10-08', b'0'),
(2, 4, 'Stefany Sophia Barbosa', '900.750.503-31', 0, 'Rua Vicente Mota Rodrigues', '326', NULL, 'Nova Cidade', 'Boa Vista', 'RR', '69316-224', 0, 0, 'DE', '2022-12-08', b'0');

INSERT INTO `tipo_veiculo` (`cod_tipo_veiculo`, `cod_empresa`, `descricao`, `peso`, `situacao`) VALUES
(1, 4, 'Grande porte', 4, '0');

INSERT INTO `transportadora` (`cod_transportadora`, `cod_empresa`, `razao_social`, `nome_fantasia`, `cnpj`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `uf`, `cep`, `latitude`, `longitude`, `situacao`) VALUES
(1, 4, 'Augusto Hugo', 'Augusto e Hugo Entregas Expressas ME', '82.244.921/0001-64', 'Rua Israel de Lima', '176', NULL, 'Vila Rica', 'Jaboatão dos Guararapes', 'PE', '54100-705', 0, 0, b'0'),
(2, 1, 'Yasmin Lorena', 'Yasmin e Lorena Contábil ME', '79.822.134/0001-48', 'Beco da Aurora', '818', NULL, 'Nossa Senhora das Graças', 'Gravatá', 'PE', '55641-696', 0, 0, b'0');

INSERT INTO `veiculo` (`cod_veiculo`, `cod_empresa`, `cod_motorista`, `cod_tipo_veiculo`, `placa`, `chassi`, `proprio`, `capacidade`, `antt`, `situacao`) VALUES
(1, 4, 1, 1, 'KHY-8566', '50223602473', 'S', 15, 0, b'0');

INSERT INTO `romaneio` (`cod_romaneio`, `cod_veiculo`, `cod_transportadora`, `cod_motorista`, `finalizado`, `ofertar_viagem`, `integrado`, `situacao`) VALUES
(1, 1, 2, 2, b'0', b'0', b'0', b'0');