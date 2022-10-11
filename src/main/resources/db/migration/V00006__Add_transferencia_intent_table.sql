CREATE TABLE transferencia_intent
(
    id       SERIAL         NOT NULL,
    valor    DECIMAL(12, 2) NOT NULL,
    data     TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    conta_id INT            NOT NULL,
    hash     VARCHAR(255)   NOT NULL,

    CONSTRAINT pk_transferencia_intent PRIMARY KEY (id),
    CONSTRAINT fk_transferencia_intent_conta_corrente FOREIGN KEY (conta_id) REFERENCES conta_corrente (id)
)
