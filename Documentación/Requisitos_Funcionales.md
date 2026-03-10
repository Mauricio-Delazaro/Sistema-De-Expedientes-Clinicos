### RF - 01 Autenticación mediante código de un solo uso

**Requisito:** Inicio de sesión mediante código OTP.

**Descripción:**
El sistema deberá permitir que terapeutas y administradores inicien sesión mediante un código de un solo uso enviado al correo electrónico registrado en el sistema.

**Restricciones:**
- Solo usuarios previamente registrados pueden solicitar un código de acceso.
- El código debe enviarse al correo asociado al usuario.

**Criterios de aceptación:**
- El usuario puede solicitar un código de acceso desde la interfaz de inicio de sesión.
- El sistema envía un código al correo electrónico registrado.
- El usuario puede ingresar el código recibido para autenticarse en el sistema.

---

### RF - 02 Consulta de pacientes

**Requisito:** Consulta de pacientes registrados.

**Descripción:**
El sistema deberá permitir a los usuarios consultar los pacientes registrados en el sistema mediante un listado y visualizar la información general de cada paciente.

**Restricciones:**
- La información mostrada dependerá del rol del usuario.
- Los terapeutas únicamente podrán consultar pacientes que les hayan sido asignados.

**Criterios de aceptación:**
- El sistema muestra un listado de pacientes disponibles para el usuario autenticado.
- El usuario puede seleccionar un paciente del listado.
- El sistema muestra la información general y expediente del paciente seleccionado.

---

### RF - 03 Edición de expediente clínico

**Requisito:** Edición de expediente clínico.

**Descripción:**
El sistema deberá permitir actualizar la información clínica del expediente de un paciente registrado en el sistema.

**Restricciones:**
- Solo usuarios autorizados pueden modificar un expediente clínico.
- No se permitirá modificar expedientes archivados.

**Criterios de aceptación:**
- El usuario autorizado puede acceder a la opción de edición del expediente clínico.
- El sistema permite modificar la información clínica del expediente.
- Los cambios realizados se almacenan correctamente en la base de datos.
- Al consultar nuevamente el expediente, el sistema muestra la información actualizada.

---

### RF - 04 Registro de sesiones psicológicas

**Requisito:** Registro de sesiones psicológicas.

**Descripción:**
El sistema deberá permitir registrar sesiones psicológicas asociadas a un paciente.

**Restricciones:**
- Cada sesión debe estar asociada a un paciente registrado.

**Criterios de aceptación:**
- El sistema permite registrar una sesión con la información correspondiente.
- La sesión queda asociada al expediente del paciente.

---

### RF - 05 Visualización operativa de expedientes

**Requisito:** Visualización operativa para administradores.

**Descripción:**
El sistema deberá permitir que los administradores consulten información operativa de los expedientes clínicos, como estado del expediente, terapeuta asignado y registros de sesiones, sin mostrar información clínica sensible.

**Restricciones:**
- Solo usuarios con rol de administrador pueden acceder a esta funcionalidad.
- La información mostrada no debe incluir contenido clínico, como notas terapéuticas u observaciones psicológicas.

**Criterios de aceptación:**
- El administrador puede consultar expedientes clínicos desde el sistema.
- El sistema muestra información operativa del expediente, como estado y terapeuta asignado.
- El sistema oculta o excluye información clínica sensible del expediente.

---

### RF - 06 Asignación de pacientes a terapeutas

**Requisito:** Asignación de pacientes.

**Descripción:**
El sistema deberá permitir asignar pacientes a terapeutas responsables de su atención.

**Restricciones:**
- Solo administradores pueden realizar asignaciones.

**Criterios de aceptación:**
- El sistema permite asignar un terapeuta a un paciente.
- El terapeuta asignado puede consultar el expediente del paciente.

---

### RF - 07 Navegación del sistema mediante menú

**Requisito:** Navegación mediante menú del sistema.

**Descripción:**
El sistema deberá proporcionar un menú de navegación para acceder a las diferentes funcionalidades disponibles.

**Restricciones:**
- Las opciones disponibles dependerán del rol del usuario autenticado.

**Criterios de aceptación:**
- El usuario puede navegar entre las funcionalidades del sistema.
- El menú muestra únicamente opciones disponibles para el rol del usuario.
