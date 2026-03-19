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

**Criterios de aceptación:**
- El sistema muestra la lista de pacientes asignados al terapeuta.

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
- El sistema permite el acceso del terapeuta al expediente del paciente seleccionado.
- El sistema muestra los documentos y apartados pertenicientes al expediente del paciente definidos con anterioridad.

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
- El sistema almacena el reporte de sesión en el apartado de control de sesiones.


### RF-04 Envío de reporte de sesión para revisión

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta enviar un reporte de sesión al supervisor correspondiente para su revisión.

**Precondiciones:**
- El reporte de sesión existe en el sistema.

**Criterios de aceptación:**
- El sistema actualiza el estado del reporte a **pendiente de revisión**.


### RF-05 Modificación de reportes

**Actor:** Terapeuta

**Descripción:** El sistema debe permitir al terapeuta modificar la información de un reporte de sesión asociado a un paciente asignado.

**Precondiciones:**
- El reporte de sesión tiene estado **rechazado** (se envió para revisión y fue rechazado por el supervisor del terapeuta). 

**Criterios de aceptación:**
- El sistema permite al terapeuta modificar el reporte que regresó y enviarlo nuevamente a revisión.

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
- El sistema presenta la lista de reportes pendientes de revisión.

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
- El sistema presenta el contenido completo del reporte de sesión.


### RF-08 Aprobación y rechazo de reporte de sesión

**Actor:** Supervisor

**Descripción:** El sistema debe permitir al supervisor aprobar o rechazar un reporte de sesión revisado.
En caso de ser rechazado, el sistema debe permitir registrar comentarios asociados al rechazo del reporte.

**Precondiciones:**
- El reporte de sesión se encuentra en estado **pendiente de revisión**.

**Criterios de aceptación:**
- En caso de ser aceptado, el sistema cambia el estado del reporte a **aprobado** y es enviado al terapeuta asignado.
- En caso de ser rechazado, el sistema cambia el estado del reporte a **rechazado** y es enviado de nuevo al terapeuta asignado.
- El sistema almacena los comentarios del supervisor.

### RF-09 Registro de entrevista socioeconómica

**Actor:** Administrador

**Descripción:** El sistema debe permitir al administrador registrar la información 
correspondiente a la entrevista socioeconómica de un paciente.
La información registrada debe quedar asociada al expediente clínico del paciente.

**Precondiciones:**
- El paciente existe dentro del sistema.

**Criterios de aceptación:**
- El sistema almacena la información de la entrevista socioeconómica en el expediente clínico del paciente.


### RF-10 Registro de acuerdo de consentimiento

**Actor:** Administrador

**Descripción:** El sistema debe permitir al administrador registrar la información correspondiente al acuerdo de consentimiento firmado por un paciente.
La información registrada debe quedar asociada al expediente clínico del paciente.

**Precondiciones:**
- El paciente existe dentro del sistema.

**Criterios de aceptación:**
- El sistema almacena la información del acuerdo de consentimiento en el expediente clínico del paciente.

### RF - 11 Registro de expediente clínico:

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
