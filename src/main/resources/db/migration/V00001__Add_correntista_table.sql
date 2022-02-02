CREATE TABLE correntista (
    id SERIAL NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_entrada DATE,

    CONSTRAINT pk_correntista PRIMARY KEY (id)
)