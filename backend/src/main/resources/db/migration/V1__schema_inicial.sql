-- ============================================================
-- V1 — Esquema inicial del Sistema de Expedientes Clínicos
-- ============================================================

-- Tabla base de usuarios (herencia JOINED con discriminador)
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario      BIGSERIAL    PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    tipo_usuario    VARCHAR(20)  NOT NULL
);

-- Subtipos de usuario
CREATE TABLE IF NOT EXISTS paciente (
    id_paciente        BIGINT      PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    edad               INTEGER     NOT NULL CHECK (edad >= 1 AND edad <= 120),
    fecha_nacimiento   DATE,
    correo_electronico VARCHAR(100) UNIQUE,
    numero_telefonico  VARCHAR(20)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS terapeuta (
    id_terapeuta BIGINT PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS supervisor (
    id_supervisor BIGINT PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS administrador (
    id_administrador BIGINT PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tablas de asociación
CREATE TABLE IF NOT EXISTS terapeuta_paciente (
    id_terapeuta BIGINT NOT NULL REFERENCES terapeuta(id_terapeuta) ON DELETE CASCADE,
    id_paciente  BIGINT NOT NULL REFERENCES paciente(id_paciente)   ON DELETE CASCADE,
    PRIMARY KEY (id_terapeuta, id_paciente)
);

CREATE TABLE IF NOT EXISTS supervisor_terapeuta (
    id_supervisor BIGINT NOT NULL REFERENCES supervisor(id_supervisor) ON DELETE CASCADE,
    id_terapeuta  BIGINT NOT NULL REFERENCES terapeuta(id_terapeuta)   ON DELETE CASCADE,
    PRIMARY KEY (id_supervisor, id_terapeuta)
);

-- Expediente clínico
CREATE TABLE IF NOT EXISTS expediente (
    id_expediente   BIGSERIAL    PRIMARY KEY,
    id_paciente     BIGINT       NOT NULL UNIQUE REFERENCES paciente(id_paciente)   ON DELETE RESTRICT,
    id_terapeuta    BIGINT       NOT NULL        REFERENCES terapeuta(id_terapeuta) ON DELETE RESTRICT,
    estado          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'ARCHIVADO')),
    fecha_prox_cita TIMESTAMPTZ
);

-- Reporte de sesión
CREATE TABLE IF NOT EXISTS reporte_sesion (
    id_documento           BIGSERIAL    PRIMARY KEY,
    id_expediente          BIGINT       NOT NULL REFERENCES expediente(id_expediente) ON DELETE RESTRICT,
    id_terapeuta           BIGINT       NOT NULL REFERENCES terapeuta(id_terapeuta)   ON DELETE RESTRICT,
    fecha                  DATE         NOT NULL,
    fecha_sesion           DATE         NOT NULL,
    duracion_sesion        INTEGER      CHECK (duracion_sesion > 0),
    observaciones_clinicas TEXT         NOT NULL,
    estado                 VARCHAR(20)  NOT NULL DEFAULT 'CREADO'
                               CHECK (estado IN ('CREADO', 'PENDIENTE', 'APROBADO', 'RECHAZADO')),
    comentarios_terapeuta  TEXT,
    comentarios_supervisor TEXT,
    fecha_creacion         TIMESTAMPTZ  NOT NULL,
    fecha_modificacion     TIMESTAMPTZ  NOT NULL
);

-- Informe de consentimiento (1:1 con expediente)
CREATE TABLE IF NOT EXISTS informe_consentimiento (
    id_documento         BIGSERIAL PRIMARY KEY,
    id_expediente        BIGINT    NOT NULL UNIQUE REFERENCES expediente(id_expediente) ON DELETE RESTRICT,
    fecha                DATE      NOT NULL,
    cuerpo_del_texto     TEXT      NOT NULL,
    acuerdo_confidencial TEXT      NOT NULL
);

-- Entrevista socioeconómica (1:1 con expediente)
CREATE TABLE IF NOT EXISTS entrevista_socioeconomica (
    id_documento          BIGSERIAL      PRIMARY KEY,
    id_expediente         BIGINT         NOT NULL UNIQUE REFERENCES expediente(id_expediente) ON DELETE RESTRICT,
    fecha                 DATE           NOT NULL,
    ingreso_familiar      NUMERIC(15, 2) NOT NULL CHECK (ingreso_familiar >= 0),
    gasto_alimentacion    NUMERIC(15, 2) NOT NULL CHECK (gasto_alimentacion >= 0),
    lugar_procedencia     VARCHAR(100)   NOT NULL,
    vivienda              VARCHAR(1000),
    estado_salud_familiar VARCHAR(50)    NOT NULL
);

-- Registro de auditoría (solo lectura — únicamente INSERT permitido)
CREATE TABLE IF NOT EXISTS log_auditoria (
    id_log      BIGSERIAL   PRIMARY KEY,
    id_usuario  BIGINT      NOT NULL,
    rol_usuario VARCHAR(20) NOT NULL
                    CHECK (rol_usuario IN ('TERAPEUTA', 'SUPERVISOR', 'ADMINISTRADOR')),
    accion      VARCHAR(30) NOT NULL
                    CHECK (accion IN (
                        'CONSULTAR_EXPEDIENTE', 'MODIFICAR_EXPEDIENTE', 'CAMBIAR_ESTADO_EXPEDIENTE',
                        'REGISTRAR_ENTREVISTA', 'REGISTRAR_CONSENTIMIENTO',
                        'REGISTRAR_REPORTE', 'MODIFICAR_REPORTE', 'ENVIAR_REPORTE',
                        'APROBAR_REPORTE', 'RECHAZAR_REPORTE'
                    )),
    recurso     VARCHAR(50) NOT NULL,
    id_recurso  VARCHAR(50),
    fecha_hora  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    resultado   VARCHAR(10) NOT NULL CHECK (resultado IN ('PERMITIDO', 'DENEGADO'))
);

-- Índices para mejorar rendimiento en consultas frecuentes
CREATE INDEX IF NOT EXISTS idx_reporte_expediente ON reporte_sesion(id_expediente);
CREATE INDEX IF NOT EXISTS idx_reporte_terapeuta  ON reporte_sesion(id_terapeuta);
CREATE INDEX IF NOT EXISTS idx_reporte_estado     ON reporte_sesion(estado);
CREATE INDEX IF NOT EXISTS idx_auditoria_usuario  ON log_auditoria(id_usuario);
CREATE INDEX IF NOT EXISTS idx_auditoria_fecha    ON log_auditoria(fecha_hora);
CREATE INDEX IF NOT EXISTS idx_auditoria_accion   ON log_auditoria(accion);
