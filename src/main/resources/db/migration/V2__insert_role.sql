INSERT INTO public.role (name, description, created_at, updated_at)
VALUES
    ('ADMIN_CONGRESO', 'Administrador encargado de crear y gestionar uno o varios congresos', Now(), Now()),
    ('COMITE_CIENTIFICO', 'Miembro del comité que evalúa los trabajos (ponencias/talleres) enviados a un congreso', Now(), Now()),
    ('PONENTE_INVITADO', 'Ponente especial que no pasa por el proceso de evaluación del comité científico',  Now(), Now()),
    ('PARTICIPANTE', 'Usuario base que puede inscribirse y participar en congresos', Now(), Now()),
    ('PONENTE', 'Participante que presenta una ponencia en un congreso (trabajo aprobado) ', Now(), Now()),
    ('TALLERISTA', 'Participante que imparte un taller en un congreso (trabajo aprobado)', Now(), Now()),
    ('ASISTENTE', 'Participante que solo asiste a las actividades del congreso', Now(), Now());

CREATE TABLE estados(
                        id serial primary key,
                        nombre varchar(255) not null
);

INSERT INTO estados (nombre) VALUES
                                 ('Pendiente'),
                                 ('En espera'),
                                 ('Activo'),
                                 ('En proceso'),
                                 ('Aceptado'),
                                 ('Rechazado'),
                                 ('Cancelado'),
                                 ('Completado'),
                                 ('Ocupado'),
                                 ('Inactivo'),
                                 ('Suspendido'),
                                 ('Finalizado');

