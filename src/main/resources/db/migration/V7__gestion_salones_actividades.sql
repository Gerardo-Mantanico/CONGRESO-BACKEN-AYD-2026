CREATE TABLE salon (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       ubicacion VARCHAR(255),
                       capacidad INTEGER NOT NULL CHECK (capacidad > 0),
                       recursos TEXT,
                       activo BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       estado_id INTEGER NOT NULL REFERENCES estados(id) ON DELETE CASCADE default 1
);


CREATE TABLE convocatoria (
                              id BIGSERIAL PRIMARY KEY,
                              nombre VARCHAR(255) NOT NULL,
                              descripcion TEXT,
                              fecha_inicio TIMESTAMP NOT NULL,
                              fecha_fin TIMESTAMP NOT NULL,
                              congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              activo BOOLEAN DEFAULT TRUE,
                              estado_id INTEGER NOT NULL REFERENCES estados(id) ON DELETE CASCADE,
                              CONSTRAINT chk_fechas_convocatoria CHECK (fecha_inicio < fecha_fin)
);

CREATE TABLE actividad (
                           id BIGSERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('PONENCIA', 'TALLER')),
                           hora_inicio TIMESTAMP NOT NULL,
                           hora_fin TIMESTAMP NOT NULL,
                           capacidad_maxima INTEGER,
                           congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                           convocatoria_id BIGINT NOT NULL REFERENCES convocatoria(id) ON DELETE CASCADE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT chk_horas_actividad CHECK (hora_inicio < hora_fin),
                           CONSTRAINT chk_capacidad_taller CHECK (
                               (tipo = 'TALLER' AND capacidad_maxima > 0) OR
                               (tipo != 'TALLER' AND capacidad_maxima IS NULL)
                               ),
                           user_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                           estado_id INTEGER NOT NULL REFERENCES estados(id) ON DELETE CASCADE default 1,
                           activo BOOLEAN DEFAULT TRUE,
                           archivo_url Text
);

CREATE TABLE actividad_encargados(
                            actividad_id BIGINT NOT NULL REFERENCES actividad (id) ON DELETE CASCADE,
                            usuario_id   BIGINT NOT NULL REFERENCES public.user (id) ON DELETE CASCADE,
                            estado_id INTEGER NOT NULL REFERENCES estados(id) ON DELETE CASCADE default 3,
                            created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            activo BOOLEAN DEFAULT TRUE,
                            PRIMARY KEY (actividad_id, usuario_id)
);