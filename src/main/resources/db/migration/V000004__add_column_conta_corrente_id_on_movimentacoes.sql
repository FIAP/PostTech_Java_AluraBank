ALTER TABLE movimentacoes
    ADD COLUMN
        conta_corrente_id INT NOT NULL;

ALTER TABLE  movimentacoes
    ADD CONSTRAINT fk_movimentacoes_conta_corrente FOREIGN KEY (conta_corrente_id) REFERENCES conta_corrente(id);
