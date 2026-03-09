-- Crear secuencia para generar números de cuenta únicos de 10 dígitos
CREATE SEQUENCE IF NOT EXISTS cuenta_num_seq START 1000000000;

-- Asegurar que el siguiente valor de la secuencia sea mayor que los existentes en la tabla
DO $$
DECLARE
    max_num BIGINT;
    next_val BIGINT := 1000000000;
BEGIN
    SELECT MAX(CAST(numero_cuenta AS BIGINT)) INTO max_num FROM cuenta_digital;
    IF max_num IS NOT NULL AND max_num >= next_val THEN
        PERFORM setval('cuenta_num_seq', max_num + 1, false);
    END IF;
END$$;

-- Función que crea una fila en cuenta_digital al insertar un usuario
CREATE OR REPLACE FUNCTION fn_create_cuenta_digital_on_user_insert()
RETURNS TRIGGER AS $$
DECLARE
    new_num BIGINT;
BEGIN
    -- Generar número único a partir de la secuencia
    new_num := nextval('cuenta_num_seq');

    INSERT INTO cuenta_digital (usuario_id, saldo, moneda, activa, numero_cuenta, created_at, updated_at)
    VALUES (NEW.id, 50.00, 'GTQ', true, new_num::text, NOW(), NOW());

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger AFTER INSERT en la tabla "user"
DROP TRIGGER IF EXISTS trg_create_cuenta_digital ON "user";
CREATE TRIGGER trg_create_cuenta_digital
AFTER INSERT ON "user"
FOR EACH ROW
EXECUTE FUNCTION fn_create_cuenta_digital_on_user_insert();

