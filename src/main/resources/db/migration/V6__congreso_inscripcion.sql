CREATE TABLE congreso (
                          id BIGSERIAL PRIMARY KEY,
                          titulo VARCHAR(255) NOT NULL,
                          descripcion TEXT,
                          fecha_inicio TIMESTAMP NOT NULL,
                          fecha_fin TIMESTAMP NOT NULL,
                          ubicacion VARCHAR(255),
                          precio_inscripcion DECIMAL(10,2) NOT NULL CHECK (precio_inscripcion >= 35.00),
                          comision_porcentaje DECIMAL(5,2) NOT NULL DEFAULT 10.00,
                          foto_url TEXT,
                          activo BOOLEAN DEFAULT TRUE,
                          institucion_id BIGINT NOT NULL REFERENCES institucion(id),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT chk_fechas_congreso CHECK (fecha_inicio < fecha_fin)
);
CREATE INDEX idx_congreso_fechas ON congreso(fecha_inicio, fecha_fin);
CREATE INDEX idx_congreso_activo ON congreso(activo);


CREATE TABLE congreso_administradores (
                                          congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                                          usuario_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          PRIMARY KEY (congreso_id, usuario_id)
);

CREATE TABLE comite_cientifico (
                                   congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                                   usuario_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (congreso_id, usuario_id)
);

CREATE TABLE inscripcion_congreso (
                                      id BIGSERIAL PRIMARY KEY,
                                      usuario_id BIGINT NOT NULL REFERENCES public.user(id) ON DELETE CASCADE,
                                      congreso_id BIGINT NOT NULL REFERENCES congreso(id) ON DELETE CASCADE,
                                      fecha_inscripcion TIMESTAMP NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      UNIQUE (usuario_id, congreso_id)
);

CREATE TABLE inscripcion_congreso_tipos (
                                            inscripcion_id BIGINT NOT NULL REFERENCES inscripcion_congreso(id) ON DELETE CASCADE,
                                            tipo_participacion VARCHAR(50) NOT NULL CHECK (tipo_participacion IN ('ASISTENTE', 'PONENTE', 'TALLERISTA', 'PONENTE_INVITADO')),
                                            PRIMARY KEY (inscripcion_id, tipo_participacion)
);


CREATE TABLE pago (
                      id BIGSERIAL PRIMARY KEY,
                      inscripcion_id BIGINT NOT NULL UNIQUE REFERENCES inscripcion_congreso(id) ON DELETE CASCADE,
                      monto_total DECIMAL(10,2) NOT NULL,
                      comision_porcentaje DECIMAL(5,2) NOT NULL,
                      monto_comision DECIMAL(10,2) NOT NULL,
                      fecha_pago TIMESTAMP NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);