-- ============================================================
-- R__seed -- Datos de prueba (se re-ejecuta al cambiar el archivo)
-- ============================================================

TRUNCATE TABLE
    log_auditoria,
    entrevista_socioeconomica,
    informe_consentimiento,
    reporte_sesion,
    expediente,
    terapeuta_paciente,
    supervisor_terapeuta,
    administrador, supervisor, terapeuta, paciente,
    usuario
RESTART IDENTITY CASCADE;

-- Administrador (id_usuario = 1)
INSERT INTO usuario (nombre_completo, tipo_usuario) VALUES ('Admin Principal', 'ADMINISTRADOR');
INSERT INTO administrador (id_administrador) VALUES (1);

-- Supervisor (id_usuario = 2)
INSERT INTO usuario (nombre_completo, tipo_usuario) VALUES ('Supervisor Uno', 'SUPERVISOR');
INSERT INTO supervisor (id_supervisor) VALUES (2);

-- Terapeuta (id_usuario = 3)
INSERT INTO usuario (nombre_completo, tipo_usuario) VALUES ('Terapeuta Uno', 'TERAPEUTA');
INSERT INTO terapeuta (id_terapeuta) VALUES (3);

-- Paciente (id_usuario = 4)
INSERT INTO usuario (nombre_completo, tipo_usuario) VALUES ('Paciente Ejemplo', 'PACIENTE');
INSERT INTO paciente (id_paciente, edad, fecha_nacimiento, correo_electronico, numero_telefonico)
VALUES (4, 30, '1995-01-15', 'paciente@ejemplo.com', '5551234567');

-- Relaciones supervisor-terapeuta y terapeuta-paciente
INSERT INTO supervisor_terapeuta (id_supervisor, id_terapeuta) VALUES (2, 3);
INSERT INTO terapeuta_paciente   (id_terapeuta,  id_paciente)  VALUES (3, 4);

-- Expediente activo: Paciente #4 con Terapeuta #3
INSERT INTO expediente (id_paciente, id_terapeuta, estado)
VALUES (4, 3, 'ACTIVO');