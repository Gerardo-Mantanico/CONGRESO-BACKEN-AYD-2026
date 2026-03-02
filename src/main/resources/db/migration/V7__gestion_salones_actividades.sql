CREATE TABLE salon (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       ubicacion VARCHAR(255),
                       capacidad INTEGER NOT NULL CHECK (capacidad > 0),
                       recursos TEXT,
                       congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                       activo BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
                              CONSTRAINT chk_fechas_convocatoria CHECK (fecha_inicio < fecha_fin)
);

CREATE TABLE trabajo (
                         id BIGSERIAL PRIMARY KEY,
                         titulo VARCHAR(255) NOT NULL,
                         resumen TEXT NOT NULL,
                         tipo_trabajo VARCHAR(50) NOT NULL CHECK (tipo_trabajo IN ('PONENCIA', 'TALLER')),
                         fecha_envio TIMESTAMP NOT NULL,
                         estado VARCHAR(50) NOT NULL CHECK (estado IN ('PENDIENTE', 'APROBADO', 'RECHAZADO')),
                         archivo_url VARCHAR(500),
                         autor_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                         convocatoria_id BIGINT NOT NULL REFERENCES convocatoria(id) ON DELETE CASCADE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evaluacion (
                            id BIGSERIAL PRIMARY KEY,
                            comentarios TEXT,
                            decision VARCHAR(50) NOT NULL CHECK (decision IN ('APROBADO', 'RECHAZADO', 'CAMBIOS_REQUERIDOS')),
                            fecha_evaluacion TIMESTAMP NOT NULL,
                            trabajo_id BIGINT NOT NULL REFERENCES trabajo(id) ON DELETE CASCADE,
                            evaluador_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            UNIQUE (trabajo_id, evaluador_id)
);

CREATE TABLE actividad (
                           id BIGSERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('PONENCIA', 'TALLER')),
                           hora_inicio TIMESTAMP NOT NULL,
                           hora_fin TIMESTAMP NOT NULL,
                           capacidad_maxima INTEGER,
                           salon_id BIGINT NOT NULL REFERENCES salon(id),
                           congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                           trabajo_origen_id BIGINT REFERENCES trabajo(id) ON DELETE SET NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT chk_horas_actividad CHECK (hora_inicio < hora_fin),
                           CONSTRAINT chk_capacidad_taller CHECK (
                               (tipo = 'TALLER' AND capacidad_maxima > 0) OR
                               (tipo != 'TALLER' AND capacidad_maxima IS NULL)
                               )
);

CREATE TABLE actividad_encargados(
                            actividad_id BIGINT NOT NULL REFERENCES actividad (id) ON DELETE CASCADE,
                            usuario_id   BIGINT NOT NULL REFERENCES public.user (id) ON DELETE CASCADE,
                            created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (actividad_id, usuario_id)
);