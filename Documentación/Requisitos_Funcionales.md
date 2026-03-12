  ### RF - 01 Consulta de pacientes

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

### RF - 02 Edición de expediente clínico

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

### RF - 03 Registro de sesiones psicológicas

**Requisito:** Registro de sesiones psicológicas.

**Descripción:**
El sistema deberá permitir registrar sesiones psicológicas asociadas a un paciente.

**Restricciones:**
- Cada sesión debe estar asociada a un paciente registrado.

**Criterios de aceptación:**
- El sistema permite registrar una sesión con la información correspondiente.
- La sesión queda asociada al expediente del paciente.

---

### RF - 04 Visualización operativa de expedientes

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

### RF - 05 Asignación de pacientes a terapeutas

**Requisito:** Asignación de pacientes.

**Descripción:**
El sistema deberá permitir asignar pacientes a terapeutas responsables de su atención.

**Restricciones:**
- Solo administradores pueden realizar asignaciones.

**Criterios de aceptación:**
- El sistema permite asignar un terapeuta a un paciente.
- El terapeuta asignado puede consultar el expediente del paciente.

---

### RF - 06 Navegación del sistema mediante menú

**Requisito:** Navegación mediante menú del sistema.

**Descripción:**
El sistema deberá proporcionar un menú de navegación para acceder a las diferentes funcionalidades disponibles.

**Restricciones:**
- Las opciones disponibles dependerán del rol del usuario autenticado.

**Criterios de aceptación:**
- El usuario puede navegar entre las funcionalidades del sistema.
- El menú muestra únicamente opciones disponibles para el rol del usuario.

---

### RF - 07 Registro de expediente clínico:

**Requisito:** Registro de los expedientes clínicos

**Descripción:** 
El sistema deberá permitir el registro y almacenaje de expedientes por paciente siguiendo las normativas y leyes pertinentes.

**Restricciones:**
- Cada paciente registrado deberá contar un expediente.
- Solo los terapeutas autorizados podrán acceder a la información clínica respectiva.
- El expediente deberá contar con los campos señalados por las normativas y leyes pertinentes.

**Criterios de aceptación:**
- El sistema crea un expediente por paciente.
- El expediente cuenta con la información clínica requerida
- El expediente solo puede ser consultado por el terapeuta asignado.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIyNzk1OTg4NF19
-->