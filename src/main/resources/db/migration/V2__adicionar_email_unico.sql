ALTER TABLE clientes
    ADD CONSTRAINT uq_clientes_email UNIQUE (email);
