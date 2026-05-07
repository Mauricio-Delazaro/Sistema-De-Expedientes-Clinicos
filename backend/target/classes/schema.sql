-- ============================================================
-- SCHEMA: expedientes_db
-- Módulo de Expedientes Clínicos (despliegue independiente)
-- Motor: PostgreSQL 14+
-- NOTA: Los id_usuario, id_paciente, id_terapeuta, id_supervisor
--       son referencias LÓGICAS al módulo de autenticación.
--       No se declaran FK cruzadas entre schemas.
-- ============================================================

-- ----------------------------------------------------------
-- CACHÉ LOCAL DE USUARIOS (recibido desde módulo de login)
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario_cache (
    id_usuario      BIGINT       NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    tipo            VARCHAR(20)  NOT NULL CHECK (tipo IN ('PACIENTE','TERAPEUTA','SUPERVISOR','ADMINISTRADOR')),
    username        VARCHAR(50)  UNIQUE,
    password_hash   VARCHAR(255),
    CONSTRAINT pk_usuario_cache PRIMARY KEY (id_usuario)
);

-- ----------------------------------------------------------
-- RELACIÓN SUPERVISOR–TERAPEUTA (gestionada por el administrador)
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS supervisor_terapeuta (
    id_supervisor BIGINT NOT NULL,
    id_terapeuta  BIGINT NOT NULL,
    CONSTRAINT pk_sup_ter PRIMARY KEY (id_supervisor, id_terapeuta)
);

-- ----------------------------------------------------------
-- EXPEDIENTE
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS expediente (
    id_expediente   BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_paciente     BIGINT      NOT NULL,
    id_terapeuta    BIGINT      NOT NULL,
    estado          VARCHAR(10) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO','ARCHIVADO')),
    fecha_prox_cita TIMESTAMP,
    CONSTRAINT pk_expediente   PRIMARY KEY (id_expediente),
    CONSTRAINT uq_exp_paciente UNIQUE (id_paciente)
);

CREATE INDEX IF NOT EXISTS idx_exp_terapeuta ON expediente(id_terapeuta);

-- ----------------------------------------------------------
-- JERARQUÍA DE DOCUMENTO
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS documento (
    id_documento  BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_expediente BIGINT      NOT NULL,
    fecha         DATE        NOT NULL,
    tipo          VARCHAR(30) NOT NULL CHECK (tipo IN ('REPORTE_SESION','INFORME_CONSENTIMIENTO','ENTREVISTA_SOCIOECONOMICA')),
    CONSTRAINT pk_documento      PRIMARY KEY (id_documento),
    CONSTRAINT fk_doc_expediente FOREIGN KEY (id_expediente) REFERENCES expediente(id_expediente)
);

CREATE INDEX IF NOT EXISTS idx_doc_expediente ON documento(id_expediente);

CREATE TABLE IF NOT EXISTS reporte_sesion (
    id_documento           BIGINT      NOT NULL,
    id_terapeuta           BIGINT      NOT NULL,
    fecha_sesion           DATE        NOT NULL,
    duracion_sesion        INT,
    observaciones_clinicas TEXT        NOT NULL,
    estado                 VARCHAR(10) NOT NULL DEFAULT 'CREADO' CHECK (estado IN ('CREADO','PENDIENTE','APROBADO','RECHAZADO')),
    comentarios_terapeuta  TEXT,
    comentarios_supervisor TEXT,
    fecha_creacion         TIMESTAMP   NOT NULL,
    fecha_modificacion     TIMESTAMP   NOT NULL,
    CONSTRAINT pk_reporte       PRIMARY KEY (id_documento),
    CONSTRAINT fk_rep_documento FOREIGN KEY (id_documento) REFERENCES documento(id_documento),
    CONSTRAINT chk_duracion     CHECK (duracion_sesion IS NULL OR duracion_sesion > 0)
);

CREATE INDEX IF NOT EXISTS idx_rep_terapeuta ON reporte_sesion(id_terapeuta);
CREATE INDEX IF NOT EXISTS idx_rep_estado    ON reporte_sesion(estado);

CREATE TABLE IF NOT EXISTS informe_consentimiento (
    id_documento         BIGINT NOT NULL,
    cuerpo_del_texto     TEXT   NOT NULL,
    acuerdo_confidencial TEXT   NOT NULL,
    CONSTRAINT pk_informe       PRIMARY KEY (id_documento),
    CONSTRAINT fk_inf_documento FOREIGN KEY (id_documento) REFERENCES documento(id_documento)
);

CREATE TABLE IF NOT EXISTS entrevista_socioeconomica (
    id_documento          BIGINT        NOT NULL,
    ingreso_familiar      DECIMAL(15,2) NOT NULL,
    gasto_alimentacion    DECIMAL(15,2) NOT NULL,
    lugar_procedencia     VARCHAR(100)  NOT NULL,
    vivienda              VARCHAR(1000),
    estado_salud_familiar VARCHAR(50)   NOT NULL,
    CONSTRAINT pk_entrevista    PRIMARY KEY (id_documento),
    CONSTRAINT fk_ent_documento FOREIGN KEY (id_documento) REFERENCES documento(id_documento),
    CONSTRAINT chk_ingreso      CHECK (ingreso_familiar >= 0),
    CONSTRAINT chk_gasto        CHECK (gasto_alimentacion >= 0)
);

-- ----------------------------------------------------------
-- AUDITORÍA (solo INSERT permitido a nivel de aplicación)
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS registro_auditoria (
    id_log      BIGINT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_usuario  BIGINT       NOT NULL,
    rol_usuario VARCHAR(15)  NOT NULL CHECK (rol_usuario IN ('TERAPEUTA','SUPERVISOR','ADMINISTRADOR')),
    accion      VARCHAR(30)  NOT NULL CHECK (accion IN (
                    'CONSULTAR_EXPEDIENTE',
                    'MODIFICAR_EXPEDIENTE',
                    'CAMBIAR_ESTADO_EXPEDIENTE',
                    'REGISTRAR_ENTREVISTA',
                    'REGISTRAR_CONSENTIMIENTO',
                    'REGISTRAR_REPORTE',
                    'MODIFICAR_REPORTE',
                    'ENVIAR_REPORTE',
                    'APROBAR_REPORTE',
                    'RECHAZAR_REPORTE'
                )),
    recurso     VARCHAR(100) NOT NULL,
    id_recurso  VARCHAR(100) NOT NULL,
    fecha_hora  TIMESTAMP    NOT NULL,
    resultado   VARCHAR(10)  NOT NULL CHECK (resultado IN ('PERMITIDO','DENEGADO')),
    CONSTRAINT pk_auditoria PRIMARY KEY (id_log)
);

CREATE INDEX IF NOT EXISTS idx_audit_usuario    ON registro_auditoria(id_usuario);
CREATE INDEX IF NOT EXISTS idx_audit_fecha      ON registro_auditoria(fecha_hora);
CREATE INDEX IF NOT EXISTS idx_audit_accion     ON registro_auditoria(accion);
CREATE INDEX IF NOT EXISTS idx_audit_id_recurso ON registro_auditoria(id_recurso);

-- ----------------------------------------------------------
-- Usuarios de prueba para verificación de despliegue
-- Contraseñas: Admin123! / Terapeuta1! / Supervisor1!
-- ----------------------------------------------------------
-- INSERT INTO usuario_cache VALUES
--   (1, 'Luis Martínez Ruiz',    'ADMINISTRADOR', 'admin',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LjZEAbiTxii'),
--   (2, 'Carlos Ramírez Torres', 'TERAPEUTA',     'terapeuta1',  '$2a$10$TKh8H1.PfJH3/5WL/5PkVOlMCOTsj3kW3vZ6.4kCrZ.7DqIkiWHMq'),
--   (3, 'Ana González Pérez',    'SUPERVISOR',    'supervisor1', '$2a$10$TKh8H1.PfJH3/5WL/5PkVOlMCOTsj3kW3vZ6.4kCrZ.7DqIkiWHMq');
