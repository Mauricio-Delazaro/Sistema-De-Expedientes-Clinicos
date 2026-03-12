### RNF - 01 Conservación de expedientes clínicos
**Requisito:** Preservación en lugar de eliminación total de pacientes en el sistema.

**Descripción:**
El sistema deberá diseñarse para que la "eliminación" de pacientes no implique la eliminación completa de sus datos en la base de datos, por lo que será archivado, preservando el expediente clínico del paciente conservando: organizacón visual en el manejo de pacientes activos  del terapeuta y un expediente archivado y listo en caso de regreso del paciente.

**Restricciones:**
- No se permitirá la eliminación permanente de expedientes clínicos desde la interfaz del sistema, este mismo solo será agregado a archivación.
- El diseño deberá tener un atributo de estado (activo/archivado) con el fin de mantener separados los pacientes activos de los que ya fueron archivados.
- Los pacientes archivados no deberán aparecer en listados activos, por lo que maneja una lista de pacientes activos y otra la cual será de los pacientes archivados.
- La información archivada no podrá ser modificada, por lo que para poder sermodificada debera volver la lista de pacientes activos.

**Criterios de aceptación:**
- El sistema muestra un atributo de estado para el expediente de cada paciente.
- el sistema maneja listas para pacientes activos y archivados.
- El sistema impide la modificación de la información en el expediente del paciente una vez este ha sido archivado.
- El sistema debera de permitir el cambio de estado del paciente de activo a archivado y viceversa 

---

### RNF - 02 Control de acceso a expedientes clínicos basado en atributos
**Requisito:** El sistema deberá restringir el acceso a expedientes clínicos únicamente a usuarios con asignación válida al expediente correspondiente.

**Descripción:** 
Es de importancia mantener la confidencialidad de los expedientes para evitar el acceso, divulgación o consulta indebida de datos clínicos y personales, garantizando el cumplimiento de la _Norma Oficial Mexicana  NOM-024-SSA3-2010_. Para ello, se usará el framework _Java: Spring Security_ como mecanismo de seguridad para controlar la autorización antes de la ejecución de cada operación. Asimismo, se adoptará un enfoque _ABAC (attribute-based access control)_ dado que el acceso a los expedientes no depende únicamente del rol del usuario (terapeuta), sino de atributos específicos de este, como la asignación registrada entre el terapeuta y el expediente clínico. Por lo tanto, se definen las siguientes reglas de autorización:
- Se permitirá la consulta y modificación de expedientes únicamente cuando el terapeuta se encuentre asignado al identificador del expediente correspondiente.
- Se permitirá registrar nuevas sesiones clínicas únicamente a terapeutas asignados a dicho expediente.

**Restricciones:**
- La autorización debe hacerse antes de la ejecución de cualquier operación que conlleve tanto la consulta como modificación de los expedientes.
- Únicamente el rol permitido para interactuar con los expedientes será el terapeuta.

**Criterios de aceptación:**
- Cuando el usuario solicite consultar un expediente asignado a su identificador, el sistema deberá permitir su visualización.
- Si un usuario intenta acceder a un expediente que no esté asignado a su identificador, el sistema deberá denegar el acceso.
- Toda operación que implique tanto el acceso como la modificación de expedientes deberá validar las reglas de autorización antes de ejecutar la acción solicitada.

---

### RNF - 03 Aislamiento de datos clínicos por asignación

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