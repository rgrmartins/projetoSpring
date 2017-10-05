CREATE TABLE empresa (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cnpj varchar(255) NOT NULL,
  data_atualizacao datetime  NOT NULL,
  data_criacao datetime NOT NULL,
  razao_social varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE funcionario (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cpf varchar(255) NOT NULL,
  data_atualizacao datetime  NOT NULL,
  data_criacao datetime NOT NULL,
  email varchar(255) NOT NULL,
  nome varchar(255) NOT NULL,
  perfil varchar(255) NOT NULL,
  qtd_horas_almoco float DEFAULT NULL,
  qtd_horas_trabalho_dia float DEFAULT NULL,
  senha varchar(255) NOT NULL,
  valor_hora decimal(19,2) DEFAULT NULL,
  empresa_id bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE lancamento (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  data datetime NOT NULL,
  data_atualizacao datetime NOT NULL,
  data_criacao datetime NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  localizacao varchar(255) DEFAULT NULL,
  tipo varchar(255) NOT NULL,
  funcionario_id bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




 
ALTER TABLE funcionario
  ADD CONSTRAINT FK4cm1kg523jlopyexjbmi6y54j FOREIGN KEY (empresa_id) REFERENCES empresa(id);
  
ALTER TABLE lancamento
  ADD CONSTRAINT FK46i4k5vl8wah7feutye9kbpi4 FOREIGN KEY (funcionario_id) REFERENCES funcionario(id) ; 