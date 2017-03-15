CREATE TABLE apprest.usuario (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL,
	senha VARCHAR(32) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX email_UNIQUE (email ASC)
);
  
INSERT INTO usuario(nome,email,senha) 
	VALUES('Eduardo', 'edu@edu.com','123');