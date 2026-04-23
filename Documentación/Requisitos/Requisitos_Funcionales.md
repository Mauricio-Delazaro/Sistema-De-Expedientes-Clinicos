## Requisitos funcionales

### RF-01 Visualización de pacientes asignados 

**Actor:** Terapeuta

**Descripción:** El sistema debe mostrar al terapeuta una lista que contenga unicamente los pacientes que tiene asignados dentro de la clínica.
Cada elemento de la lista mostrará:
- Nombre del paciente
- Número de expediente clínico

**Precondiciones:**
- El paciente está registrado en el sistema
- El terapeuta está registrado en el sistema
- Existen uno o más registros en **AsignacionTerapeutaPaciente** con estado = "activa" para el terapeuta


**Criterios de aceptación:**
- Dado un terapeuta autorizado con al menos un paciente asignado, cuando solicita la lista, el sistema devuelve solo pacientes asociados a ese terapeuta.
- La respuesta incluye para cada paciente el nombre completo y el número de expediente clínico.
- Si el terapeuta no tiene pacientes asignados, el sistema muestra una lista vacía y no incluye pacientes de otros terapeutas.
- El sistema muestra la lista de pacientes con registros activos en **AsignacionTerapeutaPaciente** asociados al terapeuta.
- Solo se muestran pacientes con estado = "activa" en la asignación

### RF-02 Acceso al expediente clínico de paciente asignado

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta acceder al expediente clínico de un paciente seleccionado desde la lista de pacientes que tiene asignados.
El expediente clínico mostrará la información básica del paciente, así como:
- Documento de entrevista socioeconomica
- Documento de informe de consentimiento
- Apartado de control de sesiones

**Precondiciones:**
- El paciente pertenece a la lista de pacientes asignados al terapeuta.

**Criterios de aceptación:**
- Dado un terapeuta autorizado y un paciente asignado en su lista, cuando selecciona el paciente, el sistema abre el expediente clínico correspondiente.
- El expediente mostrado incluye la información básica del paciente, el documento de entrevista socioeconómica, el documento de consentimiento y el apartado de control de sesiones.
- Si el terapeuta intenta abrir un expediente de un paciente no asignado, el sistema deniega el acceso y muestra un mensaje de autorización insuficiente.

### RF-03 Registro de reporte de sesión

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta registrar un reporte de sesión asociado al expediente clínico de un paciente asignado.
El reporte de sesión debe permitir registrar al menos:
- Nombre del terapeuta
- Nombre del paciente
- Fecha de la sesión
- Duración de la sesión
- Observaciones clínicas de la sesión

**Precondiciones:**
- El terapeuta tiene acceso al apartado de control de sesiones en el expediente de un paciente asignado.

**Criterios de aceptación:**
- Dado un terapeuta con acceso al expediente de un paciente asignado, cuando registra un reporte con nombre del terapeuta, nombre del paciente, fecha, duración y observaciones, el sistema guarda el reporte en el control de sesiones.
- El sistema no permite guardar el reporte si falta alguno de los campos obligatorios.
- Al guardar correctamente, el sistema genera un identificador único para el reporte y lo deja en estado inicial de borrador o equivalente.


### RF-04 Envío de reporte de sesión para revisión

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta enviar un reporte de sesión al supervisor correspondiente para su revisión.

**Precondiciones:**
- El reporte de sesión existe en el sistema.

**Criterios de aceptación:**
- Dado un reporte existente en estado editable, cuando el terapeuta lo envía a revisión, el sistema cambia su estado a **pendiente de revisión**.
- El reporte enviado queda visible en la bandeja de revisión del supervisor correspondiente.
- Si el reporte no existe o no pertenece al terapeuta autorizado, el sistema rechaza el envío y conserva el estado anterior.


### RF-05 Modificación de reportes

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta modificar la información de un reporte de sesión asociado a un paciente asignado.

**Precondiciones:**
- El reporte de sesión tiene estado **rechazado** (se envió para revisión y fue rechazado por el supervisor del terapeuta). 

**Criterios de aceptación:**
- Dado un reporte en estado **rechazado**, cuando el terapeuta autorizado modifica uno o más campos y guarda los cambios, el sistema actualiza la información almacenada.
- El sistema no permite editar reportes en estados distintos de **rechazado**.
- Tras guardar la modificación, el reporte puede volver a enviarse a revisión y cambia nuevamente a **pendiente de revisión**.

### RF-06 Visualización de reportes de sesión pendientes de revisión

**Actor:** Supervisor

**Descripción:** El sistema debe mostrar al supervisor la lista de reportes de sesión que se encuentran en estado **pendiente de revisión** y que fueron enviados por terapeutas bajo su supervisión.
Cada elemento de la lista debe incluir al menos:
- Identificador del reporte
- Nombre del terapeuta responsable
- Fecha de la sesión

**Precondiciones:**
- Existen reportes de sesión enviados para revisión por terapeutas asignados al supervisor.

**Criterios de aceptación:**
- Dado un supervisor autorizado con terapeutas asignados, cuando solicita la lista de revisión, el sistema muestra únicamente los reportes en estado **pendiente de revisión** enviados por esos terapeutas.
- Cada elemento listado incluye identificador del reporte, nombre del terapeuta responsable y fecha de la sesión.
- Si no existen reportes pendientes para ese supervisor, el sistema muestra una lista vacía.

### RF-07 Visualización del contenido de un reporte de sesión

**Actor:** Supervisor

**Descripción:** El sistema debe permitir al supervisor visualizar la información completa de un reporte de sesión seleccionado.
La información mostrada debe incluir al menos:
- Nombre del terapeuta
- Nombre del paciente
- Fecha de la sesión
- Duración de la sesión
- Observaciones registradas por el terapeuta

**Precondiciones:**
- El supervisor selecciona un reporte desde la lista de reportes pendientes.

**Criterios de aceptación:**
- Dado un supervisor autorizado y un reporte visible en su lista de revisión, cuando selecciona el reporte, el sistema muestra el detalle completo.
- El detalle incluye nombre del terapeuta, nombre del paciente, fecha de la sesión, duración de la sesión y observaciones.
- Si el reporte no pertenece al supervisor o no existe, el sistema no muestra el detalle y devuelve un mensaje de acceso no autorizado o recurso no encontrado.


### RF-08 Aprobación y rechazo de reporte de sesión

**Actor:** Supervisor

**Descripción:** El sistema debe permitir al supervisor aprobar o rechazar un reporte de sesión revisado.
En caso de ser rechazado, el sistema debe permitir registrar comentarios asociados al rechazo del reporte.

**Precondiciones:**
- El reporte de sesión se encuentra en estado **pendiente de revisión**.

**Criterios de aceptación:**
- Dado un reporte en estado **pendiente de revisión**, cuando el supervisor lo aprueba, el sistema cambia el estado a **aprobado** y notifica o deja disponible el resultado para el terapeuta asignado.
- Dado un reporte en estado **pendiente de revisión**, cuando el supervisor lo rechaza con comentarios, el sistema cambia el estado a **rechazado** y registra los comentarios del rechazo.
- El sistema no permite aprobar ni rechazar reportes que no estén en estado **pendiente de revisión**.
- Si no se registran comentarios al rechazar, el sistema impide completar el rechazo.

### RF-09 Registro de entrevista socioeconómica

**Actor:** Administrador

**Descripción:** El sistema debe permitir al administrador registrar la información 
correspondiente a la entrevista socioeconómica de un paciente.
La información registrada debe quedar asociada al expediente clínico del paciente.

**Precondiciones:**
- El paciente existe dentro del sistema.

**Criterios de aceptación:**
- Dado un paciente existente, cuando el administrador captura y guarda los datos de la entrevista socioeconómica, el sistema los asocia al expediente clínico del paciente.
- El sistema no permite guardar la entrevista si el paciente no existe o si faltan campos obligatorios definidos para la entrevista.
- Al guardar correctamente, la información queda disponible para consulta dentro del expediente del paciente.


### RF-10 Registro de acuerdo de consentimiento

**Actor:** Administrador

**Descripción:** El sistema debe permitir al administrador registrar la información correspondiente al acuerdo de consentimiento firmado por un paciente.
La información registrada debe quedar asociada al expediente clínico del paciente.

**Precondiciones:**
- El paciente existe dentro del sistema.

**Criterios de aceptación:**
- Dado un paciente existente, cuando el administrador captura y guarda los datos del acuerdo de consentimiento, el sistema los asocia al expediente clínico del paciente.
- El sistema no permite guardar el acuerdo si el paciente no existe o si faltan campos obligatorios definidos para el consentimiento.
- Al guardar correctamente, el documento queda visible dentro del expediente del paciente.

### RF - 11 Registro de expediente clínico:

**Descripción:** 
El sistema deberá permitir el registro y almacenaje de expedientes por paciente siguiendo las normativas y leyes pertinentes.

**Restricciones:**
- Cada paciente registrado deberá contar un expediente.
- Solo los terapeutas autorizados podrán acceder a la información clínica respectiva.
- El expediente deberá contar con los campos señalados por las normativas y leyes pertinentes.

**Criterios de aceptación:**
- Dado un paciente registrado que aún no cuenta con expediente, cuando el sistema ejecuta el alta del expediente, crea exactamente un expediente asociado a ese paciente.
- El expediente creado incluye los campos obligatorios definidos por la normativa aplicable.
- Si ya existe un expediente para el paciente, el sistema impide crear uno duplicado.
- El expediente solo puede ser consultado por el terapeuta asignado; cualquier otro usuario recibe un rechazo de acceso.
