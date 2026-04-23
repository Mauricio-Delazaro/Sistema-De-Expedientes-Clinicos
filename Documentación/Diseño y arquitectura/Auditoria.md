## 1. Alcance

La auditoría aplica a las operaciones sensibles relacionadas con el acceso y gestión de expedientes clínicos dentro del módulo.
Incluye eventos asociados a expedientes clínicos, reportes de sesión y documentos administrativos vinculados al expediente.

---

## 2. Lista de Eventos Auditables

### 2.1 Acceso a Expediente Clínico
- Consulta de expediente clínico.
- Intento de acceso no autorizado a expediente clínico.

### 2.2 Operaciones sobre Expedientes
- Modificación de expediente clínico.
- Cambio de estado del expediente (activo/archivado).
- Registro de entrevista socioeconómica.
- Registro de informe de consentimiento.

### 2.3 Operaciones sobre Reportes de Sesión
- Registro de reporte de sesión.
- Modificación de reporte de sesión.
- Envío de reporte a revisión.
- Aprobación de reporte.
- Rechazo de reporte.

---

## 3. Estructura del Registro de Auditoría

Cada evento de auditoría se almacena con la siguiente estructura uniforme. Esta es la definición canónica para todo el sistema.

| Campo | Tipo / Valores | Descripción |
|-------|---------------|-------------|
| id_log | Entero autoincremental | Identificador único del registro de auditoría. |
| id_usuario | Entero | Identificador del usuario que realizó la acción. |
| rol_usuario | Texto (Terapeuta / Supervisor / Administrador) | Rol del usuario en el sistema. |
| accion | `CONSULTAR_EXPEDIENTE` \| `ACCESO_DENEGADO` \| `MODIFICAR_EXPEDIENTE` \| `CAMBIAR_ESTADO_EXPEDIENTE` \| `REGISTRAR_ENTREVISTA` \| `REGISTRAR_CONSENTIMIENTO` \| `REGISTRAR_REPORTE` \| `MODIFICAR_REPORTE` \| `ENVIAR_REPORTE` \| `APROBAR_REPORTE` \| `RECHAZAR_REPORTE` | Acción realizada. Debe corresponder a uno de los valores definidos. |
| recurso | Texto | Entidad afectada, p. ej. `Expediente`, `Reporte`. |
| id_recurso | Texto | Identificador del recurso afectado. |
| fecha_hora | ISO 8601 (UTC) | Fecha y hora del evento. |
| resultado | `PERMITIDO` \| `DENEGADO` | Resultado de la operación. Valores controlados únicos. |

---

## 4. Flujo de Generación de Logs

1. El usuario realiza una solicitud al sistema.
2. El sistema recibe la identidad del usuario autenticado.
3. El módulo evalúa las reglas de autorización (ABAC) para la operación solicitada.
4. Si la operación es permitida, se ejecuta la operación.
5. Si la operación es denegada, se rechaza la solicitud.
6. Se genera un registro de auditoría con resultado `PERMITIDO` o `DENEGADO`.
7. El registro se almacena de forma persistente en la base de datos.

**Flujo general:**

```
Usuario  →  Controller  →  Autorización  →  Service  →  Auditoría  →  Base de datos
```

---

## 5. Consideraciones de Seguridad

- Los registros de auditoría se almacenan en base de datos de forma persistente.
- Los logs no pueden ser modificados ni eliminados desde la interfaz del sistema.
- Solo se permiten operaciones de inserción sobre los registros de auditoría.
- La auditoría se ejecuta exclusivamente en backend.
- Se registran tanto operaciones permitidas como denegadas.
- Solo usuarios autorizados pueden consultar los registros de auditoría.
- No se almacenan datos clínicos sensibles completos dentro de los logs.
- Los registros permiten mantener trazabilidad sobre las acciones realizadas en el sistema.

---

## 6. Ejemplos de Registro de Auditoría

**Ejemplo 1 — Consulta de expediente clínico**

| Campo | Valor de ejemplo |
|-------|-----------------|
| id_log | 1024 |
| id_usuario | 15 |
| rol_usuario | Terapeuta |
| accion | CONSULTAR_EXPEDIENTE |
| recurso | Expediente |
| id_recurso | EXP-23 |
| fecha_hora | 2026-04-04T10:30:00Z |
| resultado | PERMITIDO |

**Ejemplo 2 — Rechazo de reporte de sesión**

| Campo | Valor de ejemplo |
|-------|-----------------|
| id_log | 1025 |
| id_usuario | 8 |
| rol_usuario | Supervisor |
| accion | RECHAZAR_REPORTE |
| recurso | ReporteSesion |
| id_recurso | REP-47 |
| fecha_hora | 2026-04-04T11:15:00Z |
| resultado | PERMITIDO |