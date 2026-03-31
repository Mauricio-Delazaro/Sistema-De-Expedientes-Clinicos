# Modelo de estados

## Entidades que manejan estados

Las entidades del sistema que manejan estados son:
- Expediente clínico
- Reporte de sesión

## Estados por entidad

### Expediente clínico

| Estado | Descripción |
|---|---|
| Activo | El expediente puede consultarse y modificarse. |
| Archivado | El expediente no puede modificarse, solo consultarse. |

### Reporte de sesión

| Estado | Descripción |
|---|---|
| Creado | El reporte ha sido registrado pero no enviado a revisión. |
| Pendiente | El reporte fue enviado y está en revisión. |
| Aprobado | El reporte fue aprobado y ya no puede modificarse. |
| Rechazado | El reporte fue rechazado y puede corregirse. |


## Transiciones de estado

### Expediente clínico

| Estado actual | Acción | Nuevo estado |
|---|---|---|
| Activo | Archivar expediente | Archivado |
| Archivado | Reactivar expediente | Activo |

### Reporte de sesión

| Estado actual | Acción | Nuevo estado |
|---|---|---|
| Creado | Enviar a revisión | Pendiente |
| Pendiente | Aprobar | Aprobado |
| Pendiente | Rechazar | Rechazado |
| Rechazado | Corregir y reenviar | Pendiente |
