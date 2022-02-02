CREATE TABLE movimentacoes (
    id SERIAL NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    operacao VARCHAR(255) NOT NULL ,
    data TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    conta_id INT NOT NULL,

    CONSTRAINT pk_movimentacoes PRIMARY KEY (id),
    CONSTRAINT fk_movimentacoes_conta_corrente FOREIGN KEY (conta_id) REFERENCES  conta_corrente(id)
)