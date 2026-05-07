## 1. Contexto y Arquitectura de Despliegue

El sistema está compuesto por dos módulos independientes. Cada módulo es una aplicación Spring Boot que se despliega como un `.jar` independiente en el mismo servidor. Los módulos **nunca comparten código fuente**; la integración ocurre únicamente a través de:

1. **Tokens JWT** emitidos por el módulo de Autenticación y Agenda
2. **Datos de contexto** que el frontend envía al módulo de Expedientes al provenir de una pantalla de agenda

```
Servidor
├── :8081  →  Módulo Agenda
└── :8082  →  Módulo Expedientes Clínicos
```

Un reverse proxy (nginx o similar) unifica las rutas bajo un mismo dominio para el frontend.

### Bases de datos

Cada módulo opera sobre su propio schema de MySQL en la misma instancia de base de datos. **No existen FK cruzadas entre schemas.** Los identificadores de usuarios y pacientes se tratan como referencias lógicas dentro del módulo de Expedientes.

```
MySQL
├── agenda_db    ← Agenda
└── expedientes_db    ← este módulo
```

---

## 2. Requerimientos al Módulo de Autenticación y Agenda

### 2.1 Contrato del Token JWT

El módulo de Expedientes **no implementa login**. Únicamente valida y decodifica el JWT emitido por el módulo de Agenda en cada petición entrante.

El token debe llegar en el header HTTP de cada solicitud:

```
Authorization: Bearer <token>
```

#### Campos obligatorios en el payload del JWT

| Campo    | Tipo   | Descripción                                           | Ejemplo                     |
|----------|--------|-------------------------------------------------------|-----------------------------|
| `sub`    | String | Identificador único del usuario (`id_usuario`)        | `"15"`                      |
| `rol`    | String | Rol del usuario en mayúsculas                         | `"TERAPEUTA"`               |
| `nombre` | String | Nombre completo del usuario autenticado               | `"Carlos Ramírez Torres"`   |
| `exp`    | Long   | Tiempo de expiración estándar JWT (Unix timestamp)    | `1746144000`                |

#### Valores válidos del campo `rol`

| Valor           | Descripción                                              |
|-----------------|----------------------------------------------------------|
| `TERAPEUTA`     | Accede y gestiona expedientes de sus pacientes asignados |
| `SUPERVISOR`    | Revisa y dictamina reportes de terapeutas a su cargo     |
| `ADMINISTRADOR` | Administra expedientes, documentos y auditoría           |

> **Nota:** El rol `PACIENTE` no accede al módulo de Expedientes. No debe incluirse como valor válido para las operaciones de esta API.

#### Algoritmo de firma

| Parámetro         | Valor requerido                                                                                       |
|-------------------|-------------------------------------------------------------------------------------------------------|
| Algoritmo         | `HS256` (HMAC-SHA256) con secreto compartido, **o** `RS256` (RSA) con par de llaves                  |
| Método preferido  | `RS256` — vuestro módulo conserva la clave privada; el módulo de Expedientes recibe solo la clave pública |

El secreto o clave pública debe ser entregado al módulo de expedientes antes de la integración. Se configurará como variable de entorno.

---

### 2.2 Notificación de Usuarios Registrados

El módulo de Expedientes necesita conocer el nombre completo de usuarios que no son el usuario autenticado actual (por ejemplo, el nombre del terapeuta asignado a un expediente o el nombre del paciente). Para ello se expone un **endpoint interno** que su módulo debe invocar **cada vez que crea o actualiza un usuario**.

#### Endpoint interno de registro de usuarios

```
POST /api/v1/internal/usuarios
Content-Type: application/json
X-Internal-Secret: <secreto-compartido>
```

**Request body:**

```json
{
  "idUsuario": 15,
  "nombreCompleto": "Carlos Ramírez Torres",
  "tipo": "TERAPEUTA"
}
```

| Campo            | Tipo   | Requerido | Valores válidos                                          |
|------------------|--------|-----------|----------------------------------------------------------|
| `idUsuario`      | Long   | Sí        | Identificador único del usuario en el sistema            |
| `nombreCompleto` | String | Sí        | Nombre completo, máximo 100 caracteres                   |
| `tipo`           | String | Sí        | `PACIENTE` · `TERAPEUTA` · `SUPERVISOR` · `ADMINISTRADOR` |

**Response 200 OK** — usuario registrado o actualizado exitosamente.  
**Response 400** — datos faltantes o inválidos.  
**Response 401** — secreto incorrecto.

> El encabezado `X-Internal-Secret` es un secreto compartido entre ambos módulos, configurado como variable de entorno en cada uno. Protege este endpoint de llamadas externas no autorizadas.

#### Cuándo invocar este endpoint

| Evento en vuestro módulo                              | Acción requerida                           |
|-------------------------------------------------------|--------------------------------------------|
| Se crea un nuevo usuario (cualquier tipo)             | Invocar `POST /internal/usuarios`          |
| Se actualiza el nombre completo de un usuario         | Invocar `POST /internal/usuarios` (upsert) |
| Se elimina un usuario                                 | No es necesario notificar                  |

---

### 2.3 Relación Supervisor–Terapeuta

El módulo de Expedientes requiere saber qué terapeutas están bajo supervisión de cada supervisor para aplicar control de acceso (ABAC). Esta relación **la gestiona el administrador directamente en el módulo de Expedientes** a través de un endpoint administrativo propio.

**No es necesario que su módulo envíe esta relación.** Solo es necesario que los `id_usuario` de supervisores y terapeutas sean consistentes con los notificados vía el endpoint del punto 2.2.

---

### 2.4 Integración con la Funcionalidad de Agenda

El módulo de Expedientes **no realiza llamadas directas a su módulo** para obtener datos de agenda. La integración ocurre a nivel de frontend:

Cuando el usuario terapeuta navega desde una pantalla de agenda hacia el módulo de Expedientes para registrar un reporte de sesión, **el frontend es responsable de transportar el contexto necesario**.

#### Datos de contexto requeridos del frontend

| Dato              | Cómo se envía al módulo de Expedientes        | Origen en la agenda              |
|-------------------|-----------------------------------------------|----------------------------------|
| ID del expediente | Ruta de la URL: `{idExpediente}`              | Asociado al paciente de la cita  |
| Fecha de sesión   | Body del request: `fechaSesion`               | Fecha de la cita registrada      |

#### Flujo esperado

```
1. Terapeuta visualiza una cita en la pantalla de agenda
2. Hace clic en "Registrar reporte de sesión"
3. El frontend navega al módulo de Expedientes, llevando:
   - idExpediente (asociado al paciente de la cita)
   - fechaSesion  (fecha de la cita)
4. El frontend llama:
   POST /api/v1/expedientes/{idExpediente}/reportes
   Header: Authorization: Bearer <jwt>
   Body: { "fechaSesion": "...", "observacionesClinicas": "...", ... }
```

#### Requerimiento de consistencia de identificadores

El `id_usuario` que vuestro módulo asigna a cada paciente debe ser **el mismo** que se envía vía el endpoint interno (sección 2.2) y el que el frontend utiliza para navegar a un expediente. Todos los módulos deben referenciar al paciente por el mismo identificador.

---

## 3. Checklist de Verificación Previo al Acople

Antes de realizar la integración técnica, verificar que se cumpla cada punto:

### Requerimientos módulo de agenda

- [ ] El JWT emitido contiene los campos `sub`, `rol`, `nombre`, `exp`
- [ ] El campo `rol` usa exactamente los valores: `TERAPEUTA`, `SUPERVISOR`, `ADMINISTRADOR`
- [ ] El algoritmo de firma fue acordado y la clave/secreto fue entregada al equipo de Expedientes
- [ ] El módulo invoca `POST /api/v1/internal/usuarios` al crear cada usuario (paciente, terapeuta, supervisor, administrador)
- [ ] El `id_usuario` en el campo `sub` del JWT coincide con el `idUsuario` enviado al endpoint interno
- [ ] Se realizó una prueba con un token real y el módulo de Expedientes lo validó correctamente
- [ ] El frontend es capaz de pasar `idExpediente` y `fechaSesion` al navegar de una cita a un reporte

### Requerimientos del módulo de Expedientes (este módulo)

- [ ] El endpoint interno `POST /api/v1/internal/usuarios` está disponible y configurado
- [ ] La variable de entorno con la clave JWT está configurada
- [ ] Los usuarios de prueba fueron notificados vía el endpoint interno antes de las pruebas
- [ ] Existe al menos un expediente de prueba antes de intentar crear reportes

---

## 4. Variables de Entorno Requeridas en el Módulo de Expedientes

Las siguientes variables de entorno deben estar configuradas antes del despliegue:

| Variable          | Descripción                                                   | Ejemplo                                               |
|-------------------|---------------------------------------------------------------|-------------------------------------------------------|
| `DB_URL`          | URL de conexión al schema de Expedientes                      | `jdbc:mysql://localhost:3306/expedientes_db`          |
| `DB_USERNAME`     | Usuario de base de datos                                      | `expedientes_user`                                    |
| `DB_PASSWORD`     | Contraseña de base de datos                                   | `***`                                                 |
| `JWT_SECRET`      | Secreto compartido para verificar firma JWT (si HS256)        | `***`                                                 |
| `JWT_PUBLIC_KEY`  | Clave pública RSA para verificar firma JWT (si RS256)         | `-----BEGIN PUBLIC KEY-----...`                       |
| `INTERNAL_SECRET` | Secreto para proteger el endpoint `/internal/usuarios`        | `***`                                                 |
| `SERVER_PORT`     | Puerto donde corre este módulo                                | `8082`                                                |
