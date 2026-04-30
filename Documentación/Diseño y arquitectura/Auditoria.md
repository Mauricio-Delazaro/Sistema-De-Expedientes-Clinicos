## 1. Alcance

La auditoría aplica a las operaciones sensibles relacionadas con el acceso y gestión de expedientes clínicos dentro del módulo.
Incluye eventos asociados a expedientes clínicos, reportes de sesión y documentos administrativos vinculados al expediente.

---

## 2. Lista de Eventos Auditables

### 2.1 Acceso a Expediente Clínico
- Consulta de expediente clínico, con resultado `PERMITIDO` o `DENEGADO` según la evaluación ABAC.

> Los intentos de acceso no autorizado no tienen una acción ENUM separada. Se registran con `accion: CONSULTAR_EXPEDIENTE` y `resultado: DENEGADO`, evitando redundancia con el campo `resultado` que ya discrimina el desenlace de la operación.

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
| rol_usuario | ENUM: `TERAPEUTA` \| `SUPERVISOR` \| `ADMINISTRADOR` | Rol del usuario en el sistema. |
| accion | `CONSULTAR_EXPEDIENTE` \| `MODIFICAR_EXPEDIENTE` \| `CAMBIAR_ESTADO_EXPEDIENTE` \| `REGISTRAR_ENTREVISTA` \| `REGISTRAR_CONSENTIMIENTO` \| `REGISTRAR_REPORTE` \| `MODIFICAR_REPORTE` \| `ENVIAR_REPORTE` \| `APROBAR_REPORTE` \| `RECHAZAR_REPORTE` | Acción realizada. Debe corresponder a uno de los valores definidos. Los accesos denegados se registran con `CONSULTAR_EXPEDIENTE` y `resultado: DENEGADO`. |
| recurso | Texto | Entidad afectada, p. ej. `Expediente`, `Reporte`. |
| id_recurso | Texto | Identificador numérico del recurso afectado, almacenado como cadena de texto (ej. `"45"` para un expediente con id_expediente = 45). |
| fecha_hora | ISO 8601 (UTC) | Fecha y hora del evento. |
| resultado | `PERMITIDO` \| `DENEGADO` | Resultado de la operación. Valores controlados únicos. |

---

## 4. Flujo de Generación de Logs

La evaluación de la autorización ABAC y la generación del registro de auditoría son responsabilidad de la **capa de servicio (Service)**, conforme a la arquitectura en capas definida en `Arquitectura_y_patrón.md`. El Controller únicamente recibe la solicitud y la delega al Service sin ejecutar lógica de negocio ni de autorización.

1. El usuario realiza una solicitud al sistema.
2. El Controller recibe la solicitud y la delega al Service.
3. El Service recibe la identidad del usuario autenticado y evalúa las reglas de autorización ABAC (rol del usuario y atributos de asignación al recurso solicitado).
4. Si la operación es permitida, el Service ejecuta la lógica de negocio e interactúa con el Repository.
5. Si la operación es denegada, el Service rechaza la solicitud sin invocar el Repository de datos.
6. El Service genera un registro de auditoría con resultado `PERMITIDO` o `DENEGADO`.
7. El registro se almacena de forma persistente en la base de datos mediante el Repository de auditoría.

**Flujo general:**

```
Usuario  →  Controller  →  Service (autorización ABAC + lógica de negocio)  →  Repository  →  Base de datos
                                              ↓
                                          Auditoría
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

## 6. Política de Acceso a los Registros de Auditoría

### 6.1 Roles Autorizados

Únicamente el rol **Administrador** está autorizado para consultar los registros de auditoría. Los roles Terapeuta y Supervisor no tienen acceso a los logs bajo ninguna circunstancia.

| Rol            | Leer registros         | Insertar registros | Modificar registros | Eliminar registros |
|----------------|------------------------|--------------------|---------------------|--------------------|
| `TERAPEUTA`    | No                     | No (solo backend)  | No                  | No                 |
| `SUPERVISOR`   | No                     | No (solo backend)  | No                  | No                 |
| `ADMINISTRADOR`| Sí (todos los registros)| No (solo backend) | No                  | No                 |

La inserción de registros es responsabilidad exclusiva del backend y ocurre de forma automática; ningún rol la invoca directamente.

### 6.2 Alcance de la Consulta

El Administrador puede consultar todos los registros de auditoría del sistema, sin restricción por recurso ni por usuario. El acceso no está sujeto a reglas ABAC; el rol `ADMINISTRADOR` tiene visibilidad global sobre todos los logs.

### 6.3 Filtros de Consulta Disponibles

La consulta admite los siguientes filtros opcionales combinables entre sí:

| Filtro      | Campo en `registro_auditoria` | Descripción |
|-------------|-------------------------------|-------------|
| `idUsuario` | `id_usuario`                  | Registros generados por un usuario específico |
| `fechaDesde`| `fecha_hora`                  | Registros con `fecha_hora` mayor o igual a la fecha indicada (ISO 8601 UTC) |
| `fechaHasta`| `fecha_hora`                  | Registros con `fecha_hora` menor o igual a la fecha indicada (ISO 8601 UTC) |
| `accion`    | `accion`                      | Registros de un tipo de acción específico (ENUM de la sección 3) |
| `recurso`   | `recurso`                     | Registros sobre una entidad específica (`Expediente`, `ReporteSesion`, etc.) |
| `idRecurso` | `id_recurso`                  | Registros sobre un recurso concreto identificado por su ID |
| `resultado` | `resultado`                   | Registros filtrados por resultado (`PERMITIDO` o `DENEGADO`) |

Cuando no se aplica ningún filtro, el sistema retorna todos los registros existentes.

### 6.4 Inmutabilidad de los Registros

Los registros de auditoría son de solo lectura e inalterables para todos los roles, incluido el Administrador:

- No se permite **modificar** ningún campo de un registro existente.
- No se permite **eliminar** registros de auditoría desde ninguna interfaz del sistema.
- La base de datos debe configurarse para aceptar únicamente operaciones `INSERT` sobre `registro_auditoria`; las operaciones `UPDATE` y `DELETE` están prohibidas a nivel del usuario de aplicación.
- Esta restricción garantiza la trazabilidad e integridad del historial de operaciones, conforme a los criterios de aceptación de RNF-05.

---

## 7. Ejemplos de Registro de Auditoría

**Ejemplo 1 — Consulta de expediente clínico**

| Campo | Valor de ejemplo |
|-------|-----------------|
| id_log | 1024 |
| id_usuario | 15 |
| rol_usuario | TERAPEUTA |
| accion | CONSULTAR_EXPEDIENTE |
| recurso | Expediente |
| id_recurso | 45 |
| fecha_hora | 2026-04-04T10:30:00Z |
| resultado | PERMITIDO |

**Ejemplo 2 — Rechazo de reporte de sesión**

| Campo | Valor de ejemplo |
|-------|-----------------|
| id_log | 1025 |
| id_usuario | 8 |
| rol_usuario | SUPERVISOR |
| accion | RECHAZAR_REPORTE |
| recurso | ReporteSesion |
| id_recurso | 210 |
| fecha_hora | 2026-04-04T11:15:00Z |
| resultado | PERMITIDO |