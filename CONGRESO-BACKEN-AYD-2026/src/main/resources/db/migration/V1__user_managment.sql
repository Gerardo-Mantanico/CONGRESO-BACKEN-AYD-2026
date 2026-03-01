CREATE TYPE security_code_type AS ENUM ('LOGIN_2FA', 'RECOVERY_PASSWORD');
CREATE TYPE security_code_status AS ENUM ('ACTIVE', 'USED', 'INACTIVE');

CREATE TABLE IF NOT EXISTS public.permission
(
    id          serial                                            NOT NULL,
    name        character varying(100) COLLATE pg_catalog.default NOT NULL,
    description character varying(255) COLLATE pg_catalog.default NOT NULL,
    created_at  timestamp(3) without time zone                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp(3) without time zone                    NOT NULL,
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role
(
    id          serial                                            NOT NULL,
    name        character varying(100) COLLATE pg_catalog.default NOT NULL,
    description character varying(255) COLLATE pg_catalog.default NOT NULL,
    created_at  timestamp(3) without time zone                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp(3) without time zone                    NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_permission
(
    id            serial                         NOT NULL,
    role_id       integer                        NOT NULL,
    permission_id integer                        NOT NULL,
    created_at    timestamp(3) without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp(3) without time zone NOT NULL,
    CONSTRAINT role_permission_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user
(
    id           serial                                            NOT NULL,
    firstname    character varying(100) COLLATE pg_catalog.default NOT NULL,
    lastname     character varying(100) COLLATE pg_catalog.default NOT NULL,
    email        character varying(100) COLLATE pg_catalog.default NOT NULL UNIQUE,
    phone_number character varying(20) COLLATE pg_catalog.default  NOT NULL,
    password     character varying(100) COLLATE pg_catalog.default NOT NULL,
    dpi          character varying(100) COLLATE pg_catalog.default NOT NULL UNIQUE,
    use_2fa      boolean                                           NOT NULL DEFAULT false,
    active       boolean                                           NOT NULL DEFAULT true,
    created_at   timestamp(3) without time zone                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   timestamp(3) without time zone                    NOT NULL,
    foto_url      character varying(500) COLLATE pg_catalog.default,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_role
(
    id         serial                         NOT NULL,
    user_id    integer                        NOT NULL,
    role_id    integer                        NOT NULL,
    created_at timestamp(3) without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(3) without time zone NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_security
(
    id              serial                                            NOT NULL,
    code            character varying(100) COLLATE pg_catalog.default NOT NULL,
    code_expiration timestamp(6) without time zone                    NOT NULL,
    code_type       security_code_type                                NOT NULL,
    user_id         integer                                           NOT NULL,
    status          security_code_status                              NOT NULL,
    CONSTRAINT user_security_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.role_permission
    ADD CONSTRAINT role_permission_permission_id_fkey FOREIGN KEY (permission_id)
        REFERENCES public.permission (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS role_permission_permission_id_idx
    ON public.role_permission (permission_id);


ALTER TABLE IF EXISTS public.role_permission
    ADD CONSTRAINT role_permission_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS role_permission_role_id_idx
    ON public.role_permission (role_id);


ALTER TABLE IF EXISTS public.user_role
    ADD CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS user_role_role_id_idx
    ON public.user_role (role_id);


ALTER TABLE IF EXISTS public.user_role
    ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS user_role_user_id_idx
    ON public.user_role (user_id);


ALTER TABLE IF EXISTS public.user_security
    ADD CONSTRAINT user_security_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;

-- CREATE APP ROLES
INSERT INTO public.role (name, description, created_at, updated_at)
VALUES ('ADMIN', 'ADMIN_SISTEMA', Now(), Now());

-- Insert admin
INSERT INTO public.user (firstname, lastname, email, phone_number, password, active, created_at, updated_at,dpi)
VALUES ('Admin', '', 'webbank404@gmail.com', '12341234',
        '$2a$10$AqbbVC2uUKLvMWXrLj2TVuLMMN1BeEAHR5Qfxq001Rf61O43ZKfF6',
        true, Now(), Now(),123456789);

-- Assign role
INSERT INTO public.user_role (user_id, role_id, created_at, updated_at)
VALUES ((SELECT id FROM public."user" WHERE email = 'webbank404@gmail.com'),
        (SELECT id FROM public.role WHERE name = 'ADMIN'),
        Now(),
        Now());
