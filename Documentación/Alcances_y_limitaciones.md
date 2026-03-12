# Alcances y limitaciones

## Descripción del sistema 

El  proyecto consiste en un módulo para la gestión de expedientes clínicos en una clínica de psicología. Este módulo forma parte de un sistema más amplio y tiene como finalidad digitalizar el manejo de expedientes de pacientes, los cuales son administrados actualmente de manera física.
El sistema permitirá consultar y gestionar la información relacionada con los pacientes y sus expedientes clínicos, facilitando el acceso a la información por parte del personal autorizado y agilizando los procesos dentro de la clínica.

## Objetivo del sistema

El objetivo del sistema es digitalizar la gestión de expedientes clínicos de los pacientes de la clínica de psicología, permitiendo a los terapeutas y al personal administrativo acceder a la información necesaria de forma rápida, organizada y segura.
Con ello, se busca reemplazar el manejo físico de expedientes y facilitar la consulta y gestión de la información clínica dentro de la clínica.

## Usuarios

Dentro del alcance del sistema se contemplan tres tipos de usuarios, cada uno con responsabilidades y permisos atribuidos a su rol dentro de la plataforma: 

### Terapeuta

Su función principal es la gestión y control de las sesiones con los pacientes. El terapeuta únicamente puede acceder a la información de los pacientes que le han sido asignados. Además, puede generar reportes de sesión, los cuales son enviados al terapeuta supervisor para su revisión.

### Supervisor

Es responsable de supervisar el trabajo de los terapeutas bajo su cargo. Tiene la capacidad de revisar, modificar, aprobar o rechazar los reportes de sesión enviados por los terapeutas asignados. Este rol no genera reportes de sesión nuevos desde cero.

### Administrador

Se encarga de registrar y gestionar la información correspondiente a documentos administrativos del sistema, tales como la entrevista socioeconómica y el acuerdo de consentimiento.

Cada tipo de usuario cuenta con permisos y accesos limitados a las funciones correspondientes a su rol, con el fin de garantizar un adecuado control y gestión de la información dentro del sistema.

## Funcionalidades del sistema

El sistema proporcionará diferentes funcionalidades dependiendo del tipo de usuario que acceda a la plataforma. Cada rol tendrá acceso únicamente a las acciones correspondientes a sus responsabilidades dentro del sistema.

### Terapeuta

- Consultar la lista de pacientes que le han sido asignados.
- Acceder a la información relevante de dichos pacientes.
- Registrar y elaborar reportes de sesión correspondientes a las terapias realizadas.
- Enviar los reportes de sesión para revisión del terapeuta supervisor.
- Consultar el estado de los reportes enviados (pendiente, aprobado o rechazado).

### Supervisor

- Consultar los reportes de sesión enviados por los terapeutas bajo su supervisión.
- Revisar el contenido de los reportes de sesión.
- Modificar los reportes cuando sea necesario.
- Aprobar o rechazar los reportes enviados por los terapeutas.

### Administrador 

- Registrar y gestionar información correspondiente a documentos administrativos.
    - Capturar y almacenar la información de la entrevista socioeconómica.
    - Registrar la información correspondiente al acuerdo de consentimiento.

## Limitaciones del sistema

- El módulo no implementa un sistema de autenticación o inicio de sesión, pues esta funcionalidad pertenece a otro módulo del sistema general.
- La asignación de pacientes a terapeutas no se realiza dentro de este módulo, sino que depende de un módulo externo de agenda.
- El sistema contempla únicamente tres tipos de usuarios: terapeuta, terapeuta supervisor y administrador.

## Restricciones del sistema

- El acceso a la información y modificación de la misma se encuentra restringido según el rol del usuario dentro del sistema.
- Una sesión clínica no puede agregarse al expediente del paciente sin haber sido aprobada por el supervisor.
- Los reportes de sesión deben pasar por un estado de revisión antes de ser aceptados o rechazados.
- Los supervisores solo pueden revisar sesiones de los terapeutas que tienen asignados.
- El sistema debe garantizar la confidencialidad de la información clínica de los pacientes.
