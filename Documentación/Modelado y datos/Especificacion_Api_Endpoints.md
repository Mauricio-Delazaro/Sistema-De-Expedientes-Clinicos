# Especificación de la API REST

##  Información General

### URL Base

```
/api/v1
```

Todos los endpoints descritos en este documento se encuentran bajo la URL base `/api/v1`.

### Formato

- Todas las solicitudes y respuestas utilizan JSON (`Content-Type: application/json`).
- Las fechas siguen el estándar **ISO 8601**: `YYYY-MM-DD` para fechas y `YYYY-MM-DDTHH:mm:ssZ` para fechas con hora en UTC.
- Los identificadores son enteros de tipo `Long` (máximo 19 dígitos).

### Autenticación y autorización

El acceso a todos los endpoints requiere que el usuario esté autenticado (la autenticación no está dentro del alcance del módulo). El sistema implementa autorización mediante **Spring Security** con un enfoque **ABAC (Attribute-Based Access Control)** conforme a `Arquitectura_y_patrón.md` y `Requisitos_No_Funcionales.md` (RNF-02, RNF-03).

Los tres roles contemplados del sistema son:

| Rol            | Descripción |
|----------------|-------------|
| `TERAPEUTA`    | Accede y gestiona expedientes de sus pacientes asignados. |
| `SUPERVISOR`   | Revisa y dictamina reportes de terapeutas bajo su supervisión. |
| `ADMINISTRADOR`| Administra expedientes, documentos y registros de auditoría. |

La validación de rol y de atributos (asignación terapeuta-paciente, relación supervisor-terapeuta) ocurre en la capa de servicio antes de ejecutar cualquier operación.

### Formato de respuestas de error

Todas las respuestas de error siguen la siguiente estructura:

```json
{
  "codigo": 403,
  "error": "ACCESO_DENEGADO",
  "mensaje": "El terapeuta no tiene asignación al expediente solicitado."
}
```

### Auditoría automática

Toda operación sobre expedientes, documentos y reportes genera automáticamente un registro en `registro_auditoria` con resultado `PERMITIDO` o `DENEGADO`, conforme a `Auditoria.md`. 

---

## Módulo Terapeuta

### CU-01 — Visualizar pacientes asignados

**RF-01**

```
GET /api/v1/terapeutas/mis-pacientes
```

**Autorización:** `TERAPEUTA`

**Parámetros:** ninguno

**Response 200 OK:**

```json
[
  {
    "idPaciente": 12,
    "nombreCompleto": "María López García",
    "idExpediente": 45
  }
]
```

| Campo          | Tipo   | Descripción |
|----------------|--------|-------------|
| idPaciente     | Long   | Identificador del paciente |
| nombreCompleto | String | Nombre completo del paciente |
| idExpediente   | Long   | Número de expediente clínico asociado |

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Lista retornada exitosamente (puede ser vacía `[]`) |
| 403    | El usuario autenticado no tiene rol `TERAPEUTA` |

---

### CU-02 — Acceder a expediente clínico

**RF-02**

```
GET /api/v1/expedientes/{idExpediente}
```

**Autorización:** `TERAPEUTA` — únicamente si está asignado al expediente.

**Parámetros de ruta:**

| Parámetro    | Tipo | Descripción |
|--------------|------|-------------|
| idExpediente | Long | Identificador del expediente clínico |

**Response 200 OK:**

```json
{
  "idExpediente": 45,
  "estado": "ACTIVO",
  "fechaProxCita": "2026-05-10T09:00:00Z",
  "paciente": {
    "idPaciente": 12,
    "nombreCompleto": "María López García",
    "edad": 34,
    "fechaNacimiento": "1992-03-15",
    "correoElectronico": "maria.lopez@correo.com",
    "numeroTelefonico": "5512345678"
  },
  "entrevistaSocioeconomica": {
    "idDocumento": 101,
    "fecha": "2026-01-10"
  },
  "informeConsentimiento": {
    "idDocumento": 102,
    "fecha": "2026-01-10"
  },
  "reportesSesion": [
    {
      "idDocumento": 201,
      "fechaSesion": "2026-04-01",
      "estado": "APROBADO"
    }
  ]
}
```

Los campos `entrevistaSocioeconomica` e `informeConsentimiento` son `null` si aún se han registrado. El arreglo `reportesSesion` puede estar vacío `[]`.

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Expediente retornado exitosamente |
| 403    | El terapeuta autenticado no tiene asignación al expediente (ABAC) o no tiene rol `TERAPEUTA` |
| 404    | El expediente no existe |

> **Auditoría:** genera un registro `CONSULTAR_EXPEDIENTE` con resultado `PERMITIDO` o `DENEGADO`.

---

### CU-03 — Registrar reporte de sesión

**RF-03**

```
POST /api/v1/expedientes/{idExpediente}/reportes
```

**Autorización:** `TERAPEUTA` — únicamente si está asignado al expediente.

**Parámetros de ruta:**

| Parámetro    | Tipo | Descripción |
|--------------|------|-------------|
| idExpediente | Long | Identificador del expediente clínico |

**Request body:**

```json
{
  "fechaSesion": "2026-04-28",
  "duracionSesion": 60,
  "observacionesClinicas": "El paciente muestra mejora en el manejo de ansiedad.",
  "comentariosTerapeuta": "Se recomienda continuar con técnicas de relajación."
}
```

| Campo                 | Tipo   | Requerido | Restricciones |
|-----------------------|--------|-----------|---------------|
| fechaSesion           | Date   | Sí        | Formato `YYYY-MM-DD` |
| duracionSesion        | Int    | No        | Mayor a 0, en minutos |
| observacionesClinicas | String | Sí        | No vacío |
| comentariosTerapeuta  | String | No        | — |

**Response 201 Created:**

```json
{
  "idDocumento": 210,
  "idExpediente": 45,
  "fechaSesion": "2026-04-28",
  "duracionSesion": 60,
  "observacionesClinicas": "El paciente muestra mejora en el manejo de ansiedad.",
  "comentariosTerapeuta": "Se recomienda continuar con técnicas de relajación.",
  "estado": "CREADO",
  "fechaCreacion": "2026-04-28T14:30:00Z",
  "fechaModificacion": "2026-04-28T14:30:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 201    | Reporte creado exitosamente |
| 400    | Datos inválidos o faltantes (ej. `observacionesClinicas` vacío) |
| 403    | Sin asignación al expediente o rol incorrecto |
| 404    | El expediente no existe |

> **Auditoría:** genera un registro `REGISTRAR_REPORTE`.

---

### CU-04 — Enviar reporte a revisión

**RF-04**

```
PATCH /api/v1/reportes/{idReporte}/enviar
```

**Autorización:** `TERAPEUTA` — propietario del reporte.

**Parámetros de ruta:**

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| idReporte | Long | Identificador del reporte de sesión |

**Request body:** ninguno

**Response 200 OK:**

```json
{
  "idDocumento": 210,
  "estado": "PENDIENTE",
  "fechaModificacion": "2026-04-28T15:00:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Reporte enviado a revisión exitosamente |
| 400    | El reporte no se encuentra en estado `CREADO` ni `RECHAZADO` |
| 403    | El terapeuta no es propietario del reporte o rol incorrecto |
| 404    | El reporte no existe |

> **Transición de estado:** `CREADO` → `PENDIENTE` · `RECHAZADO` → `PENDIENTE`.  
> **Auditoría:** genera un registro `ENVIAR_REPORTE`.

---

### CU-05 — Modificar reporte rechazado

**RF-05**

```
PUT /api/v1/reportes/{idReporte}
```

**Autorización:** `TERAPEUTA` — propietario del reporte.

**Parámetros de ruta:**

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| idReporte | Long | Identificador del reporte de sesión |

**Request body:**

```json
{
  "fechaSesion": "2026-04-28",
  "duracionSesion": 75,
  "observacionesClinicas": "Observaciones corregidas según retroalimentación del supervisor.",
  "comentariosTerapeuta": "Se ajustó la descripción de la técnica aplicada."
}
```

| Campo                 | Tipo   | Requerido | Restricciones |
|-----------------------|--------|-----------|---------------|
| fechaSesion           | Date   | Sí        | Formato `YYYY-MM-DD` |
| duracionSesion        | Int    | No        | Mayor a 0, en minutos |
| observacionesClinicas | String | Sí        | No vacío |
| comentariosTerapeuta  | String | No        | — |

**Response 200 OK:**

```json
{
  "idDocumento": 210,
  "fechaSesion": "2026-04-28",
  "duracionSesion": 75,
  "observacionesClinicas": "Observaciones corregidas según retroalimentación del supervisor.",
  "comentariosTerapeuta": "Se ajustó la descripción de la técnica aplicada.",
  "comentariosSupervisor": "Las observaciones clínicas eran insuficientes. Detalle la técnica aplicada.",
  "estado": "RECHAZADO",
  "fechaModificacion": "2026-04-28T16:00:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Reporte modificado exitosamente |
| 400    | El reporte no se encuentra en estado `RECHAZADO`, o datos inválidos |
| 403    | El terapeuta no es propietario del reporte o rol incorrecto |
| 404    | El reporte no existe |

> **Auditoría:** genera un registro `MODIFICAR_REPORTE`.

---

## Módulo Supervisor

### CU-06 — Visualizar reportes pendientes de revisión

**RF-06**

```
GET /api/v1/supervisores/mis-reportes-pendientes
```

**Autorización:** `SUPERVISOR`

**Parámetros de query (todos opcionales):**

| Parámetro   | Tipo | Descripción |
|-------------|------|-------------|
| idTerapeuta | Long | Filtra reportes de un terapeuta específico bajo supervisión |
| fechaDesde  | Date | Filtra reportes con `fechaSesion` mayor o igual a esta fecha (`YYYY-MM-DD`) |
| fechaHasta  | Date | Filtra reportes con `fechaSesion` menor o igual a esta fecha (`YYYY-MM-DD`) |

**Response 200 OK:**

```json
[
  {
    "idReporte": 210,
    "idTerapeuta": 7,
    "nombreTerapeuta": "Carlos Ramírez Torres",
    "fechaSesion": "2026-04-28"
  }
]
```

| Campo           | Tipo   | Descripción |
|-----------------|--------|-------------|
| idReporte       | Long   | Identificador del reporte |
| idTerapeuta     | Long   | Identificador del terapeuta que elaboró el reporte |
| nombreTerapeuta | String | Nombre completo del terapeuta |
| fechaSesion     | Date   | Fecha de la sesión clínica |

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Lista retornada exitosamente (puede ser vacía `[]`) |
| 400    | Parámetros de query con formato inválido |
| 403    | El usuario autenticado no tiene rol `SUPERVISOR` |

---

### CU-07 — Visualizar contenido de reporte

**RF-07**

```
GET /api/v1/reportes/{idReporte}
```

**Autorización:** `SUPERVISOR` (únicamente reportes de terapeutas bajo su supervisión) · `TERAPEUTA` (únicamente sus propios reportes).

**Parámetros de ruta:**

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| idReporte | Long | Identificador del reporte de sesión |

**Response 200 OK:**

```json
{
  "idDocumento": 210,
  "idExpediente": 45,
  "idTerapeuta": 7,
  "nombreTerapeuta": "Carlos Ramírez Torres",
  "nombrePaciente": "María López García",
  "fechaSesion": "2026-04-28",
  "duracionSesion": 60,
  "observacionesClinicas": "El paciente muestra mejora en el manejo de ansiedad.",
  "comentariosTerapeuta": "Se recomienda continuar con técnicas de relajación.",
  "comentariosSupervisor": null,
  "estado": "PENDIENTE",
  "fechaCreacion": "2026-04-28T14:30:00Z",
  "fechaModificacion": "2026-04-28T15:00:00Z"
}
```

El campo `comentariosSupervisor` es `null` cuando el reporte no ha sido rechazado previamente.

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Reporte retornado exitosamente |
| 403    | El supervisor no supervisa al terapeuta del reporte, o el terapeuta no es propietario |
| 404    | El reporte no existe |

---

### CU-08 — Aprobar reporte de sesión

**RF-08**

```
PATCH /api/v1/reportes/{idReporte}/aprobar
```

**Autorización:** `SUPERVISOR` — únicamente reportes de terapeutas bajo su supervisión.

**Parámetros de ruta:**

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| idReporte | Long | Identificador del reporte de sesión |

**Request body:** ninguno

**Response 200 OK:**

```json
{
  "idDocumento": 210,
  "estado": "APROBADO",
  "fechaModificacion": "2026-04-29T10:00:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Reporte aprobado exitosamente |
| 400    | El reporte no se encuentra en estado `PENDIENTE` |
| 403    | El supervisor no supervisa al terapeuta del reporte |
| 404    | El reporte no existe |

> **Transición de estado:** `PENDIENTE` → `APROBADO`.  
> **Auditoría:** genera un registro `APROBAR_REPORTE`.

---

### CU-09 — Rechazar reporte de sesión

**RF-08**

```
PATCH /api/v1/reportes/{idReporte}/rechazar
```

**Autorización:** `SUPERVISOR` — únicamente reportes de terapeutas bajo su supervisión.

**Parámetros de ruta:**

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| idReporte | Long | Identificador del reporte de sesión |

**Request body:**

```json
{
  "comentariosSupervisor": "Las observaciones clínicas son insuficientes. Detalle la técnica aplicada."
}
```

| Campo                 | Tipo   | Requerido | Restricciones |
|-----------------------|--------|-----------|---------------|
| comentariosSupervisor | String | Sí        | No vacío |

**Response 200 OK:**

```json
{
  "idDocumento": 210,
  "estado": "RECHAZADO",
  "comentariosSupervisor": "Las observaciones clínicas son insuficientes. Detalle la técnica aplicada.",
  "fechaModificacion": "2026-04-29T10:30:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Reporte rechazado exitosamente |
| 400    | El reporte no se encuentra en estado `PENDIENTE`, o `comentariosSupervisor` está vacío |
| 403    | El supervisor no supervisa al terapeuta del reporte |
| 404    | El reporte no existe |

> **Transición de estado:** `PENDIENTE` → `RECHAZADO`.  
> **Auditoría:** genera un registro `RECHAZAR_REPORTE`.

---

## Módulo Administrador

### CU-10 — Registrar expediente clínico

**RF-11**

```
POST /api/v1/expedientes
```

**Autorización:** `ADMINISTRADOR`

**Request body:**

```json
{
  "idPaciente": 12,
  "idTerapeuta": 7,
  "fechaProxCita": "2026-05-10T09:00:00Z"
}
```

| Campo         | Tipo     | Requerido | Restricciones |
|---------------|----------|-----------|---------------|
| idPaciente    | Long     | Sí        | El paciente debe existir en el sistema |
| idTerapeuta   | Long     | Sí        | El terapeuta debe existir en el sistema |
| fechaProxCita | DateTime | No        | Debe ser una fecha futura |

**Response 201 Created:**

```json
{
  "idExpediente": 45,
  "idPaciente": 12,
  "idTerapeuta": 7,
  "estado": "ACTIVO",
  "fechaProxCita": "2026-05-10T09:00:00Z"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 201    | Expediente creado exitosamente |
| 400    | Datos inválidos o faltantes |
| 403    | El usuario autenticado no tiene rol `ADMINISTRADOR` |
| 404    | El `idPaciente` o `idTerapeuta` indicado no existe |
| 409    | El paciente ya tiene un expediente registrado (restricción 1:1) |

---

### RNF-01 — Cambiar estado del expediente

**RNF-01**

```
PATCH /api/v1/expedientes/{idExpediente}/estado
```

**Autorización:** `ADMINISTRADOR`

**Parámetros de ruta:**

| Parámetro    | Tipo | Descripción |
|--------------|------|-------------|
| idExpediente | Long | Identificador del expediente clínico |

**Request body:**

```json
{
  "estado": "ARCHIVADO"
}
```

| Campo  | Tipo   | Requerido | Valores válidos |
|--------|--------|-----------|-----------------|
| estado | String | Sí        | `ACTIVO` \| `ARCHIVADO` |

**Response 200 OK:**

```json
{
  "idExpediente": 45,
  "estado": "ARCHIVADO"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Estado actualizado exitosamente |
| 400    | Valor de `estado` inválido |
| 403    | El usuario autenticado no tiene rol `ADMINISTRADOR` |
| 404    | El expediente no existe |

> **Auditoría:** genera un registro `CAMBIAR_ESTADO_EXPEDIENTE`.

---

### CU-11 — Registrar entrevista socioeconómica

**RF-09**

```
POST /api/v1/expedientes/{idExpediente}/entrevista-socioeconomica
```

**Autorización:** `ADMINISTRADOR`

**Parámetros de ruta:**

| Parámetro    | Tipo | Descripción |
|--------------|------|-------------|
| idExpediente | Long | Identificador del expediente clínico |

**Request body:**

```json
{
  "ingresoFamiliar": 8500.00,
  "gastoAlimentacion": 2000.00,
  "lugarProcedencia": "Ciudad de México, CDMX",
  "vivienda": "Casa propia de 3 habitaciones en colonia popular.",
  "estadoSaludFamiliar": "2 enfermos crónicos"
}
```

| Campo               | Tipo    | Requerido | Restricciones |
|---------------------|---------|-----------|---------------|
| ingresoFamiliar     | Decimal | Sí        | >= 0 |
| gastoAlimentacion   | Decimal | Sí        | >= 0 |
| lugarProcedencia    | String  | Sí        | Máximo 100 caracteres |
| vivienda            | String  | No        | Máximo 1000 caracteres |
| estadoSaludFamiliar | String  | Sí        | Máximo 50 caracteres |

**Response 201 Created:**

```json
{
  "idDocumento": 101,
  "idExpediente": 45,
  "fecha": "2026-04-29",
  "ingresoFamiliar": 8500.00,
  "gastoAlimentacion": 2000.00,
  "lugarProcedencia": "Ciudad de México, CDMX",
  "vivienda": "Casa propia de 3 habitaciones en colonia popular.",
  "estadoSaludFamiliar": "2 enfermos crónicos"
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 201    | Entrevista socioeconómica registrada exitosamente |
| 400    | Datos inválidos o faltantes |
| 403    | El usuario autenticado no tiene rol `ADMINISTRADOR` |
| 404    | El expediente no existe |

> **Auditoría:** genera un registro `REGISTRAR_ENTREVISTA`.

---

### CU-12 — Registrar consentimiento informado

**RF-10**

```
POST /api/v1/expedientes/{idExpediente}/consentimiento
```

**Autorización:** `ADMINISTRADOR`

**Parámetros de ruta:**

| Parámetro    | Tipo | Descripción |
|--------------|------|-------------|
| idExpediente | Long | Identificador del expediente clínico |

**Request body:**

```json
{
  "cuerpoDelTexto": "Texto legal completo con los términos y condiciones del tratamiento...",
  "acuerdoConfidencial": "El personal clínico se compromete a proteger la privacidad del paciente conforme a la NOM-024-SSA3-2010."
}
```

| Campo               | Tipo   | Requerido | Restricciones |
|---------------------|--------|-----------|---------------|
| cuerpoDelTexto      | String | Sí        | No vacío |
| acuerdoConfidencial | String | Sí        | No vacío |

**Response 201 Created:**

```json
{
  "idDocumento": 102,
  "idExpediente": 45,
  "fecha": "2026-04-29",
  "cuerpoDelTexto": "Texto legal completo con los términos y condiciones del tratamiento...",
  "acuerdoConfidencial": "El personal clínico se compromete a proteger la privacidad del paciente conforme a la NOM-024-SSA3-2010."
}
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 201    | Consentimiento informado registrado exitosamente |
| 400    | Datos inválidos o faltantes |
| 403    | El usuario autenticado no tiene rol `ADMINISTRADOR` |
| 404    | El expediente no existe |

> **Auditoría:** genera un registro `REGISTRAR_CONSENTIMIENTO`.

---

## Módulo Auditoría

> **Autorización formal:** La política de acceso a los registros de auditoría está definida en `Auditoria.md`, sección 6, y en `Requisitos_No_Funcionales.md` (RNF-10). Únicamente el rol `ADMINISTRADOR` puede consultar los registros; los roles `TERAPEUTA` y `SUPERVISOR` tienen acceso denegado. El requisito funcional correspondiente es RF-12 (caso de uso CU-13).

### Consultar registros de auditoría

```
GET /api/v1/auditoria
```

**Autorización:** `ADMINISTRADOR`

**Parámetros de query (todos opcionales):**

| Parámetro  | Tipo     | Descripción |
|------------|----------|-------------|
| idUsuario  | Long     | Filtra por el usuario que realizó la acción |
| fechaDesde | DateTime | Filtra registros con `fechaHora` >= este valor (ISO 8601 UTC) |
| fechaHasta | DateTime | Filtra registros con `fechaHora` <= este valor (ISO 8601 UTC) |
| accion     | String   | Uno de los valores del ENUM `accion` definido en `Auditoria.md` |
| recurso    | String   | Tipo de entidad afectada (ej. `Expediente`, `ReporteSesion`) |
| idRecurso  | String   | Identificador del recurso afectado |
| resultado  | String   | `PERMITIDO` \| `DENEGADO` |

**Response 200 OK:**

```json
[
  {
    "idLog": 1024,
    "idUsuario": 15,
    "rolUsuario": "TERAPEUTA",
    "accion": "CONSULTAR_EXPEDIENTE",
    "recurso": "Expediente",
    "idRecurso": "45",
    "fechaHora": "2026-04-28T10:30:00Z",
    "resultado": "PERMITIDO"
  }
]
```

**Códigos de respuesta:**

| Código | Significado |
|--------|-------------|
| 200    | Registros retornados exitosamente (puede ser vacío `[]`) |
| 400    | Parámetros de query con formato inválido (ej. fecha mal formada, valor de `accion` fuera del ENUM) |
| 403    | El usuario autenticado no tiene rol `ADMINISTRADOR` |

> Los registros de auditoría son de solo lectura. No existen endpoints `POST`, `PUT`, `PATCH` ni `DELETE` sobre este recurso. Toda modificación o eliminación está prohibida conforme a `Auditoria.md`, sección 5, y `Requisitos_No_Funcionales.md` (RNF-05).

---

## Resumen de endpoints

| Caso de uso | Método | Ruta                                                          | Rol autorizado                  |
|-------------|--------|---------------------------------------------------------------|---------------------------------|
| CU-01       | GET    | `/api/v1/terapeutas/mis-pacientes`                            | `TERAPEUTA`                     |
| CU-02       | GET    | `/api/v1/expedientes/{idExpediente}`                          | `TERAPEUTA` (ABAC)              |
| CU-03       | POST   | `/api/v1/expedientes/{idExpediente}/reportes`                 | `TERAPEUTA` (ABAC)              |
| CU-04       | PATCH  | `/api/v1/reportes/{idReporte}/enviar`                         | `TERAPEUTA` (propietario)       |
| CU-05       | PUT    | `/api/v1/reportes/{idReporte}`                                | `TERAPEUTA` (propietario)       |
| CU-06       | GET    | `/api/v1/supervisores/mis-reportes-pendientes`                | `SUPERVISOR`                    |
| CU-07       | GET    | `/api/v1/reportes/{idReporte}`                                | `SUPERVISOR` · `TERAPEUTA`      |
| CU-08       | PATCH  | `/api/v1/reportes/{idReporte}/aprobar`                        | `SUPERVISOR`                    |
| CU-09       | PATCH  | `/api/v1/reportes/{idReporte}/rechazar`                       | `SUPERVISOR`                    |
| CU-10       | POST   | `/api/v1/expedientes`                                         | `ADMINISTRADOR`                 |
| RNF-01      | PATCH  | `/api/v1/expedientes/{idExpediente}/estado`                   | `ADMINISTRADOR`                 |
| CU-11       | POST   | `/api/v1/expedientes/{idExpediente}/entrevista-socioeconomica`| `ADMINISTRADOR`                 |
| CU-12       | POST   | `/api/v1/expedientes/{idExpediente}/consentimiento`           | `ADMINISTRADOR`                 |
| Auditoría   | GET    | `/api/v1/auditoria`                                           | `ADMINISTRADOR`                 |

---

## Estados de Entidades

### Expediente (`estado`)

| Valor       | Descripción |
|-------------|-------------|
| `ACTIVO`    | El expediente puede consultarse y modificarse. Valor por defecto. |
| `ARCHIVADO` | El expediente solo puede consultarse; no se permiten modificaciones (RNF-01). |

### ReporteSesion (`estado`)

| Valor       | Transición desde           | Transición a |
|-------------|----------------------------|--------------|
| `CREADO`    | —                          | `PENDIENTE` (CU-04) |
| `PENDIENTE` | `CREADO` · `RECHAZADO`     | `APROBADO` (CU-08) · `RECHAZADO` (CU-09) |
| `APROBADO`  | `PENDIENTE`                | — (estado final, inmutable) |
| `RECHAZADO` | `PENDIENTE`                | `PENDIENTE` (CU-04 tras corrección con CU-05) |

---

## Acciones de auditoría

Cada acción de la tabla siguiente corresponde a un valor del ENUM `accion` en `registro_auditoria`. La columna "Disparado por" indica qué endpoint o flujo genera ese registro de manera automática.

| Valor de `accion`          | Disparado por |
|----------------------------|---------------|
| `CONSULTAR_EXPEDIENTE`     | CU-02 — resultado `PERMITIDO` si el acceso es autorizado, `DENEGADO` si la evaluación ABAC lo rechaza |
| `MODIFICAR_EXPEDIENTE`     | Modificación directa de datos del expediente |
| `CAMBIAR_ESTADO_EXPEDIENTE`| RNF-01 (PATCH `/estado`) |
| `REGISTRAR_ENTREVISTA`     | CU-11 |
| `REGISTRAR_CONSENTIMIENTO` | CU-12 |
| `REGISTRAR_REPORTE`        | CU-03 |
| `MODIFICAR_REPORTE`        | CU-05 |
| `ENVIAR_REPORTE`           | CU-04 |
| `APROBAR_REPORTE`          | CU-08 |
| `RECHAZAR_REPORTE`         | CU-09 |
