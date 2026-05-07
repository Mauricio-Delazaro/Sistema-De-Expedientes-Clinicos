-- ============================================================
-- SEED: expedientes_db — datos de prueba para verificación
-- Motor: PostgreSQL 18+  |  Requiere extensión pgcrypto
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ----------------------------------------------------------
-- USUARIOS (3 con login + 3 pacientes virtuales)
-- Contraseñas: Admin123! / Terapeuta1! / Supervisor1!
-- ----------------------------------------------------------
INSERT INTO usuario_cache (id_usuario, nombre_completo, tipo, username, password_hash) VALUES
  (1, 'Luis Martínez Ruiz',    'ADMINISTRADOR', 'admin',
      crypt('Admin123!',    gen_salt('bf', 10))),
  (2, 'Carlos Ramírez Torres', 'TERAPEUTA',     'terapeuta1',
      crypt('Terapeuta1!',  gen_salt('bf', 10))),
  (3, 'Ana González Pérez',    'SUPERVISOR',    'supervisor1',
      crypt('Supervisor1!', gen_salt('bf', 10))),
  (4, 'María López Sánchez',   'PACIENTE',      NULL, NULL),
  (5, 'Roberto García Mendoza','PACIENTE',      NULL, NULL),
  (6, 'Sofía Hernández Vega',  'PACIENTE',      NULL, NULL);

-- ----------------------------------------------------------
-- RELACIÓN SUPERVISOR–TERAPEUTA
-- ----------------------------------------------------------
INSERT INTO supervisor_terapeuta (id_supervisor, id_terapeuta) VALUES (3, 2);

-- ----------------------------------------------------------
-- EXPEDIENTE 1: María López — ACTIVO (con documentos)
-- ----------------------------------------------------------
WITH exp1 AS (
  INSERT INTO expediente (id_paciente, id_terapeuta, estado, fecha_prox_cita)
  VALUES (4, 2, 'ACTIVO', '2026-05-20 10:00:00')
  RETURNING id_expediente
),
-- Entrevista socioeconómica
doc_ent AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-03-10', 'ENTREVISTA_SOCIOECONOMICA' FROM exp1
  RETURNING id_documento
),
ins_ent AS (
  INSERT INTO entrevista_socioeconomica
    (id_documento, ingreso_familiar, gasto_alimentacion, lugar_procedencia, vivienda, estado_salud_familiar)
  SELECT id_documento, 8500.00, 3200.00, 'Guadalajara, Jalisco',
         'Casa propia de 3 habitaciones, agua y electricidad', 'Estable'
  FROM doc_ent
),
-- Informe de consentimiento
doc_inf AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-03-10', 'INFORME_CONSENTIMIENTO' FROM exp1
  RETURNING id_documento
),
ins_inf AS (
  INSERT INTO informe_consentimiento (id_documento, cuerpo_del_texto, acuerdo_confidencial)
  SELECT id_documento,
    'Se informa a la paciente sobre el proceso terapéutico, objetivos, duración estimada y metodología a emplear durante las sesiones clínicas.',
    'La información compartida durante las sesiones es estrictamente confidencial conforme a la normativa vigente, salvo riesgo inminente para la paciente o terceros.'
  FROM doc_inf
),
-- Reporte de sesión 1 — APROBADO
doc_rep1 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-04-05', 'REPORTE_SESION' FROM exp1
  RETURNING id_documento
),
ins_rep1 AS (
  INSERT INTO reporte_sesion
    (id_documento, id_terapeuta, fecha_sesion, duracion_sesion, observaciones_clinicas,
     estado, comentarios_terapeuta, comentarios_supervisor, fecha_creacion, fecha_modificacion)
  SELECT id_documento, 2, '2026-04-05', 50,
    'La paciente muestra disposición al diálogo. Se identificaron patrones de ansiedad asociados al entorno laboral. Se aplicó técnica de respiración diafragmática con respuesta positiva.',
    'APROBADO',
    'Se sugiere continuar con técnicas de relajación en casa.',
    'Reporte completo y detallado. Aprobar para expediente.',
    '2026-04-05 12:00:00', '2026-04-07 09:30:00'
  FROM doc_rep1
),
-- Reporte de sesión 2 — PENDIENTE
doc_rep2 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-04-26', 'REPORTE_SESION' FROM exp1
  RETURNING id_documento
)
INSERT INTO reporte_sesion
  (id_documento, id_terapeuta, fecha_sesion, duracion_sesion, observaciones_clinicas,
   estado, comentarios_terapeuta, fecha_creacion, fecha_modificacion)
SELECT id_documento, 2, '2026-04-26', 45,
  'Se observa mejoría en el manejo del estrés. La paciente reporta aplicar las técnicas aprendidas. Se inicia exploración de narrativa personal en relación al trabajo.',
  'PENDIENTE',
  'Sesión productiva. Pendiente revisión del supervisor.',
  '2026-04-26 13:00:00', '2026-04-26 13:00:00'
FROM doc_rep2;

-- ----------------------------------------------------------
-- EXPEDIENTE 2: Roberto García — ACTIVO (reportes en proceso)
-- ----------------------------------------------------------
WITH exp2 AS (
  INSERT INTO expediente (id_paciente, id_terapeuta, estado, fecha_prox_cita)
  VALUES (5, 2, 'ACTIVO', '2026-05-14 16:00:00')
  RETURNING id_expediente
),
doc_inf2 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-04-01', 'INFORME_CONSENTIMIENTO' FROM exp2
  RETURNING id_documento
),
ins_inf2 AS (
  INSERT INTO informe_consentimiento (id_documento, cuerpo_del_texto, acuerdo_confidencial)
  SELECT id_documento,
    'Se explica al paciente el encuadre terapéutico, incluyendo frecuencia de sesiones, duración y objetivos generales del proceso.',
    'El paciente acepta los términos de confidencialidad y autoriza el registro de sesiones en el expediente clínico digital.'
  FROM doc_inf2
),
doc_ent2 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-04-01', 'ENTREVISTA_SOCIOECONOMICA' FROM exp2
  RETURNING id_documento
),
ins_ent2 AS (
  INSERT INTO entrevista_socioeconomica
    (id_documento, ingreso_familiar, gasto_alimentacion, lugar_procedencia, vivienda, estado_salud_familiar)
  SELECT id_documento, 12000.00, 4500.00, 'Zapopan, Jalisco',
         'Departamento rentado de 2 habitaciones', 'Buena'
  FROM doc_ent2
),
doc_rep3 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-04-15', 'REPORTE_SESION' FROM exp2
  RETURNING id_documento
)
INSERT INTO reporte_sesion
  (id_documento, id_terapeuta, fecha_sesion, duracion_sesion, observaciones_clinicas,
   estado, fecha_creacion, fecha_modificacion)
SELECT id_documento, 2, '2026-04-15', 60,
  'Primera sesión terapéutica. El paciente expresa dificultades en las relaciones interpersonales y episodios de irritabilidad frecuente. Se establece rapport y se plantean objetivos preliminares.',
  'CREADO',
  '2026-04-15 17:30:00', '2026-04-15 17:30:00'
FROM doc_rep3;

-- ----------------------------------------------------------
-- EXPEDIENTE 3: Sofía Hernández — ARCHIVADO (proceso concluido)
-- ----------------------------------------------------------
WITH exp3 AS (
  INSERT INTO expediente (id_paciente, id_terapeuta, estado, fecha_prox_cita)
  VALUES (6, 2, 'ARCHIVADO', NULL)
  RETURNING id_expediente
),
doc_rep4 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2026-01-10', 'REPORTE_SESION' FROM exp3
  RETURNING id_documento
),
ins_rep4 AS (
  INSERT INTO reporte_sesion
    (id_documento, id_terapeuta, fecha_sesion, duracion_sesion, observaciones_clinicas,
     estado, comentarios_terapeuta, comentarios_supervisor, fecha_creacion, fecha_modificacion)
  SELECT id_documento, 2, '2026-01-10', 50,
    'Sesión de cierre. La paciente ha alcanzado los objetivos terapéuticos propuestos. Se realiza evaluación final con resultados satisfactorios en las áreas de gestión emocional y habilidades sociales.',
    'APROBADO',
    'Proceso concluido exitosamente. Paciente dada de alta.',
    'Excelente trabajo terapéutico. Aprobado para cierre de expediente.',
    '2026-01-10 11:00:00', '2026-01-12 08:00:00'
  FROM doc_rep4
),
doc_inf3 AS (
  INSERT INTO documento (id_expediente, fecha, tipo)
  SELECT id_expediente, '2025-11-03', 'INFORME_CONSENTIMIENTO' FROM exp3
  RETURNING id_documento
)
INSERT INTO informe_consentimiento (id_documento, cuerpo_del_texto, acuerdo_confidencial)
SELECT id_documento,
  'Consentimiento informado registrado al inicio del proceso terapéutico.',
  'La paciente firmó el acuerdo de confidencialidad en la sesión inicial.'
FROM doc_inf3;

-- ----------------------------------------------------------
-- AUDITORÍA — registros de muestra
-- ----------------------------------------------------------
INSERT INTO registro_auditoria
  (id_usuario, rol_usuario, accion, recurso, id_recurso, fecha_hora, resultado)
VALUES
  (2, 'TERAPEUTA',     'REGISTRAR_REPORTE',          'reporte_sesion', '1', '2026-04-05 12:05:00', 'PERMITIDO'),
  (3, 'SUPERVISOR',    'APROBAR_REPORTE',             'reporte_sesion', '1', '2026-04-07 09:35:00', 'PERMITIDO'),
  (2, 'TERAPEUTA',     'REGISTRAR_REPORTE',           'reporte_sesion', '2', '2026-04-26 13:05:00', 'PERMITIDO'),
  (2, 'TERAPEUTA',     'CONSULTAR_EXPEDIENTE',        'expediente',     '1', '2026-04-26 09:00:00', 'PERMITIDO'),
  (1, 'ADMINISTRADOR', 'CAMBIAR_ESTADO_EXPEDIENTE',   'expediente',     '3', '2026-01-13 10:00:00', 'PERMITIDO'),
  (3, 'SUPERVISOR',    'CONSULTAR_EXPEDIENTE',        'expediente',     '2', '2026-04-20 14:00:00', 'PERMITIDO');
