### RNF - 01 Seguridad del mecanismo de autenticación por código
**Requisito:** Autenticación mediante código de un solo uso (OTP).

**Descripción:**
El sistema deberá diseñarse para utilizar códigos de acceso de un solo uso enviados al correo electrónico registrado del terapeuta o administrador como mecanismo de autenticación temporal.

**Restricciones:**
- El código deberá tener una vigencia limitada (5–10 minutos).
- El código solo podrá utilizarse una vez.
- El diseño no deberá permitir reutilización del código.

**Criterios de aceptación:**
- El sistema genera códigos OTP con longitud de 6 caracteres.
- El sistema invalida automáticamente cualquier código marcado como utilizado con anterioridad.
- El código no se almacena en texto plano, sino mediante una función hash criptográfica.

### RNF - 02 Protección contra accesos no autorizados
**Requisito:** Control de intentos fallidos.

**Descripción:**
El sistema deberá implementar un mecanismo de limitación de intentos fallidos al ingresar el código enviado por correo, para prevenir ataques de fuerza bruta.

**Restricciones:**
- No se permitirán intentos ilimitados de validación del código.
- El diseño debe implementar un bloqueo temporal tras cierto número de intentos fallidos.

**Criterios de aceptación:**
- El sistema permite hasta un maximo de 5 intentos fallidos por codigo.
- El código es marcado como bloqueado al superar el número máximo de intentos.
- El sistema aplica un bloqueo temporal mínimo de 10 minutos tras superar el límite de intentos.

### RNF - 03 Conservación de expedientes clínicos
**Requisito:** Preservación en lugar de eliminación física de pacientes

**Descripción:**
El sistema deberá diseñarse para que la "eliminación" de pacientes no implique la eliminación completa de sus datos en la base de datos, sino su archivado, preservando el expediente clínico.

**Restricciones:**
- No se permitirá la eliminación permanente de expedientes clínicos desde la interfaz del sistema.
- El diseño deberá tener un atributo de estado (activo/archivado).
- Los pacientes archivados no deberán aparecer en listados activos.
- La información archivada no podrá ser modificada.

**Criterios de aceptación:**
- El sistema muestra un atributo de estado para el expediente de cada paciente.
- El sistema impide la modificación de la información en el expediente del paciente una vez este ha sido archivado.

### RNF - 04 Vistas diferenciadas según rol de usuario
**Requisito:** Vistas diferenciadas según rol de usuario

**Descripción:**
El sistema deberá diseñarse para presentar y habilitar únicamente las vistas y funcionalidades permitidas según el rol del usuario autenticado (Terapeuta / Administrador), de forma que las acciones no autorizadas no puedan ejecutarse ni por navegación normal ni por acceso directo a rutas.

**Restricciones:**
- La autorización es obligatoria en backend para cada endpoint.
- Las vistas del cliente (front) deben alinearse con los permisos reales, pero no sustituyen la validación del servidor.
- Accesos directos a rutas/recursos no permitidos deben ser bloqueados por políticas de autorización.

**Criterios de aceptación:**
- Un terapeuta no puede acceder a vistas administrativas, aun ingresando la URL directamente (backend rechaza).
- Un administrador sí puede acceder a vistas de gestión (backend permite).
- Toda solicitud a endpoints administrativos por un terapeuta es rechazada por el backend.

### RNF - 05 Restricción de acceso a pacientes por asignación
**Requisito:** Restricción de acceso a pacientes por asignación

**Descripción:**
El sistema deberá diseñarse para garantizar que un terapeuta únicamente pueda consultar y operar sobre expedientes/pacientes que le hayan sido asignados explícitamente, evitando exposición o manipulación de información de otros pacientes.

**Restricciones:**
- La validación de asignación se realiza en backend en cada operación sensible (listar, ver detalle, editar, etc.).
- No se permite inferencia por IDs: cualquier acceso a un paciente no asignado debe ser rechazado incluso si el ID existe.
- La capa de acceso a datos debe filtrar por asignación.

**Criterios de aceptación:**
- Un terapeuta solo puede ver pacientes asignados al listar o buscar (backend filtra resultados).
- Cualquier intento de acceso directo a un paciente no asignado es rechazado por el backend.
- La validación se aplica en endpoints de lectura y modificación.

### RNF - 06 Menú dinámico basado en el rol del usuario
**Requisito:** Menú dinámico basado en el rol del usuario
**Descripción:**
El sistema deberá diseñarse para que la interfaz muestre opciones de navegación y acciones coherentes con el rol y permisos del usuario, reduciendo errores de uso y minimizando intentos de acceso indebido, sin sustituir la autorización del backend.

**Restricciones:**
- Las opciones visibles del menú se determinan por rol/permisos derivados de la sesión/token (no “hardcode” manual sin verificación).
- La UI no debe presentar enlaces/acciones que el rol no puede ejecutar.
- Debe existir consistencia: lo visible en UI coincide con lo permitido por backend.

**Criterios de aceptación:**
- Terapeutas no visualizan opciones administrativas en el menú.
- Administradores visualizan el panel/opciones de gestión correspondientes.
- Si un usuario intenta ejecutar una acción no permitida fuera del menú, el backend la rechaza.
