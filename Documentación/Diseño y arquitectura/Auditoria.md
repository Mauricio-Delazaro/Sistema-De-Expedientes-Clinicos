# Sistema de Auditoría

## 1. Alcance
La auditoría aplica a las operaciones sensibles relacionadas con el acceso y gestión de expedientes clínicos dentro del módulo.

Incluye:
- Consulta de expediente clínico.
- Modificación de expediente clínico.
- Registro de sesiones clínicas.
- Modificación de reportes de sesión.
- Envío de reportes a revisión.
- Aprobación de reportes.
- Rechazo de reportes.
- Intentos de acceso no autorizados a expedientes clínicos.
- Cambios de estado de expediente clínico.
- Cambios de estado de reportes de sesión.

---

## 2. Lista de Eventos Auditables

### 2.1 Acceso a Expediente Clínico
- Consulta de expediente clínico.
- Intento de acceso no autorizado a expediente clínico.

### 2.2 Operaciones sobre Expedientes
- Modificación de expediente clínico.
- Cambio de estado del expediente (activo/archivado).

### 2.3 Operaciones sobre Reportes de Sesión
- Registro de reporte de sesión.
- Modificación de reporte de sesión.
- Envío de reporte a revisión.
- Aprobación de reporte.
- Rechazo de reporte.
- Cambio de estado del reporte.

---

## 3. Estructura del Registro de Auditoría

Cada evento de auditoría deberá almacenarse con una estructura uniforme para mantener trazabilidad sobre las acciones realizadas en el sistema.

Campos del registro:

| Campo | Descripción |
|------|-------------|
| id_log | Identificador del registro |
| id_usuario | Identificador del usuario |
| rol_usuario | Rol del usuario |
| accion | Acción realizada |
| recurso | Entidad afectada |
| id_recurso | Identificador del recurso |
| fecha_hora | Fecha y hora del evento |
| resultado | Resultado de la operación (PERMITIDO / DENEGADO) |

---

## 4. Flujo de Generación de Logs

1. El usuario realiza una solicitud al sistema.
2. El sistema recibe la identidad del usuario autenticado.
3. El módulo evalúa las reglas de autorización para la operación solicitada.
4. Si la operación es permitida, se ejecuta la operación.
5. Si la operación es denegada, se rechaza la operación.
6. Se genera un registro de auditoría con el resultado de la operación (permitido o denegado).
7. El registro se almacena en la base de datos.

Flujo general:

Usuario → Controller → Autorización → Service → Auditoría → Base de datos

---

## 5. Consideraciones de Seguridad

- Los registros de auditoría se almacenan en base de datos de forma persistente.
- Los logs no pueden ser modificados ni eliminados desde la interfaz del sistema.
- La auditoría se ejecuta en backend.
- Se registran operaciones permitidas y denegadas.
- Solo usuarios autorizados pueden consultar los registros de auditoría.
- Los registros permiten mantener trazabilidad sobre las acciones realizadas en el sistema.

---

## 6. Ejemplo de Registro de Auditoría

| Campo | Valor |
|------|------|
| id_usuario | 15 |
| rol_usuario | Terapeuta |
| accion | CONSULTAR_EXPEDIENTE |
| recurso | Expediente |
| id_recurso | EXP-23 |
| fecha_hora | 2026-04-04 10:30 |
| resultado | PERMITIDO |