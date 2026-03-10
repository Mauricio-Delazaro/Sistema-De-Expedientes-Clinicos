### RNF - 01 Seguridad del mecanismo de autenticación por código
**Requisito:** Seguridad del mecanismo de autenticación mediante código OTP.

**Descripción:**
El sistema deberá implementar el mecanismo de autenticación mediante código de un solo uso (OTP) definido en el RF-01, garantizando que dichos códigos sean generados, almacenados y validados de forma segura.

**Restricciones:**
- El código deberá tener una vigencia limitada de 5 a 10 minutos desde su generación.
- Cada código OTP podrá utilizarse una sola vez para completar la autenticación.
- El código OTP no deberá almacenarse en texto plano en la base de datos.
- La generación del código deberá utilizar un mecanismo de generación pseudoaleatoria adecuado para fines de seguridad.

**Criterios de aceptación:**
- El sistema genera códigos OTP con longitud de 6 caracteres.
- El sistema invalida automáticamente cualquier código marcado como utilizado con anterioridad.
- El código no se almacena en texto plano, sino mediante una función hash criptográfica.

---

### RNF - 02 Protección contra intentos de autenticación no autorizados
**Requisito:** Limitación de intentos fallidos de autenticación.

**Descripción:**
El sistema deberá implementar mecanismos de protección contra intentos repetidos de validación del código OTP definido en el RF-01, con el objetivo de reducir el riesgo de ataques de fuerza bruta durante el proceso de autenticación.

**Restricciones:**
- El sistema deberá limitar el número de intentos fallidos de validación de un código OTP.
- El sistema deberá aplicar un bloqueo temporal del proceso de autenticación tras superar el límite de intentos fallidos.

**Criterios de aceptación:**
- El sistema permite un máximo de 5 intentos fallidos de validación por cada código OTP generado.
- Al superar el límite de intentos fallidos, el código OTP se marca como inválido o bloqueado.
- El sistema impide nuevos intentos de autenticación durante un período mínimo de 10 minutos después de superar el límite.
- Una vez finalizado el período de bloqueo, el usuario puede solicitar un nuevo código OTP para iniciar nuevamente el proceso de autenticación.

---

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

---

### RNF - 04 Aplicación obligatoria de políticas de autorización

**Requisito:** Aplicación de controles de autorización en el backend.

**Descripción:**
El sistema deberá implementar controles de autorización en el backend para garantizar que únicamente los usuarios con permisos adecuados puedan ejecutar cada operación disponible en el sistema, independientemente de las restricciones de la interfaz de usuario.

**Restricciones:**
- Todas las operaciones del sistema deberán validar los permisos del usuario autenticado en el backend.
- Las restricciones de acceso no deberán depender únicamente de la interfaz de usuario.
- El acceso directo a rutas o endpoints no autorizados deberá ser bloqueado por el servidor.

**Criterios de aceptación:**

- El backend valida el rol o permisos del usuario antes de ejecutar cualquier operación protegida.
- Las solicitudes a endpoints no autorizados son rechazadas por el servidor.
- Las restricciones de acceso se aplican incluso si el usuario intenta acceder directamente a una ruta mediante URL.
---

### RNF - 05 Aislamiento de datos clínicos por asignación

**Requisito:** Aislamiento de información clínica entre terapeutas.

**Descripción:**
El sistema deberá garantizar el aislamiento de los expedientes clínicos mediante controles de acceso basados en la asignación de pacientes a terapeutas, evitando que usuarios accedan a información clínica de pacientes que no forman parte de su ámbito de atención.

**Restricciones:**
- Las consultas a datos clínicos deberán aplicar filtrado por asignación en la capa de acceso a datos..
- El sistema no deberá permitir el acceso a expedientes clínicos de pacientes no asignados al terapeuta.
- La validación de asignación deberá realizarse en el backend para cada operación que implique acceso a información clínica.
- 
**Criterios de aceptación:**
- Las consultas realizadas por terapeutas retornan únicamente pacientes que les han sido asignados.
- Las solicitudes de acceso a expedientes de pacientes no asignados son rechazadas por el servidor.
- Las reglas de aislamiento de datos se aplican tanto en operaciones de lectura como de modificación.

---

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
