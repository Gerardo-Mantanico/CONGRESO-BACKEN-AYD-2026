CREATE TABLE configuracion_sistema (
                                       id BIGINT PRIMARY KEY CHECK (id = 1),
                                       comision_porcentaje_default DECIMAL(5,2) NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Instituciones
CREATE TABLE institucion (
                             id BIGINT  PRIMARY KEY ,
                             nombre VARCHAR(255) NOT NULL UNIQUE,
                             descripcion TEXT,
                             direccion VARCHAR(255),
                             activo BOOLEAN DEFAULT TRUE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
