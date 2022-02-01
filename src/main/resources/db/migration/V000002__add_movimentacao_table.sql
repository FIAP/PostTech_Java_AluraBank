CREATE TABLE movimentacoes (
    id SERIAL NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    operacao VARCHAR(255) NOT NULL,
    data timestamp WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_movimentacoes PRIMARY KEY(id)
);