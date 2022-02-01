CREATE TABLE correntista (
    id SERIAL NOT NULL,
    nome varchar(255) NOT NULL,
    cpf varchar(11) NOT NULL,
    data_entrada date NOT NULL,

    CONSTRAINT pk_correntista PRIMARY KEY(id)
);