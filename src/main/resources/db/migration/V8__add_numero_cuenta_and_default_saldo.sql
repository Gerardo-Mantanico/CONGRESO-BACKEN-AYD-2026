ALTER TABLE cuenta_digital ADD COLUMN numero_cuenta VARCHAR(50);
UPDATE cuenta_digital SET numero_cuenta = (1000000000 + id)::text WHERE numero_cuenta IS NULL;
ALTER TABLE cuenta_digital ALTER COLUMN saldo SET DEFAULT 50.00;
UPDATE cuenta_digital SET saldo = 50.00 WHERE saldo IS NULL;
ALTER TABLE cuenta_digital ALTER COLUMN numero_cuenta SET NOT NULL;
CREATE UNIQUE INDEX uq_cuenta_numero ON cuenta_digital(numero_cuenta);
