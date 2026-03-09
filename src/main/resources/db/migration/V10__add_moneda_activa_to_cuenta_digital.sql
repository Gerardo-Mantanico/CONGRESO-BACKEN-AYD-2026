-- Añade columnas moneda y activa a cuenta_digital con valores por defecto y actualiza filas existentes
ALTER TABLE cuenta_digital ADD COLUMN moneda VARCHAR(8);
ALTER TABLE cuenta_digital ADD COLUMN activa BOOLEAN;

-- Poner valores por defecto para nuevas inserciones
ALTER TABLE cuenta_digital ALTER COLUMN moneda SET DEFAULT 'GTQ';
ALTER TABLE cuenta_digital ALTER COLUMN activa SET DEFAULT true;

-- Actualizar filas existentes
UPDATE cuenta_digital SET moneda = 'GTQ' WHERE moneda IS NULL;
UPDATE cuenta_digital SET activa = true WHERE activa IS NULL;

