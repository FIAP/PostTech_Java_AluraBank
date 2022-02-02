CREATE TABLE conta_corrente (
  id SERIAL NOT NULL,
  correntista_id INT NOT NULL,
  banco VARCHAR(255) NOT NULL,
  agencia VARCHAR(255) NOT NULL,
  numero VARCHAR(255) NOT NULL,

  CONSTRAINT pk_conta_corrente PRIMARY KEY (id),
  CONSTRAINT fk_conta_corrente_correntista FOREIGN KEY (correntista_id) REFERENCES correntista(id)
);