# Alcances y limitaciones

## Descripción del sistema 

El  proyecto consiste en un módulo para la gestión de expedientes clínicos en una clínica de psicología. Este forma parte de un sistema más amplio y tiene como finalidad digitalizar el manejo de expedientes de pacientes, los cuales son administrados actualmente de manera física.
El sistema permitirá archivar, consultar y gestionar la información relacionada con los pacientes y sus expedientes clínicos, facilitando el acceso a la información por parte del personal autorizado y agilizando los procesos administrativos dentro de la clínica.
## Objetivo del sistema

El objetivo del sistema es digitalizar la gestión de expedientes clínicos de los pacientes de la clínica de psicología, permitiendo a los terapeutas y al personal administrativo acceder a la información necesaria de forma rápida, organizada y segura.
Con ello, se busca reemplazar reemplazar el manejo físico de expedientes y facilitar la consulta y gestión de la información clínica dentro de la clínica.
## Usuarios

El módulo está dirigido a dos tipos de usuarios:

### Terapeutas

Los terapeutas podrán:
- Consultar la lista de pacientes que les han sido asignados.
- Acceder a los expedientes clínicos de sus pacientes.
- Consultar la información registrada en dichos expedientes.

El acceso de los terapeutas se limita únicamente a los pacientes que les han sido asignados dentro del sistema.
### Personal administrativo

El personal administrativo podrá:
- Acceder a la información general de los terapeutas.
- Consultar información general de los expedientes de los pacientes y a qué terapeutas están asignados.
- Gestionar el registro y organización de los expedientes dentro del sistema.

El personal administrativo no tendrá acceso directo al contenido clínico detallado de los expedientes, con el fin de preservar la confidencialidad de la información médica.
## Funcionalidades del sistema
- Los terapeutas podrán consultar la lista de pacientes que les han sido asignados dentro del sistema.
- Los terapeutas podrán acceder a los expedientes clínicos de los pacientes que tengan asignados.
- Los terapeutas podrán consultar la información registrada en los expedientes de sus pacientes.
- Los terapeutas podrán modificar la información relacionada con el control y seguimiento de las sesiones terapéuticas.
- El personal administrativo podrá consultar la lista de terapeutas activos en la clínica
- El personal administrativo podrá acceder a la información general de los terapeutas registrados en el sistema.
- El personal administrativo podrá consultar qué pacientes se encuentran asignados a cada terapeuta.
- El personal administrativo podrá acceder a los expedientes de los pacientes con fines gestión en el sistema. 
## Limitaciones del sistema

- El módulo no implementa un sistema de autenticación o inicio de sesión, pues esta funcionalidad pertenece a otro módulo del sistema general.
- La asignación de pacientes a terapeutas no se realiza dentro de este módulo, sino que depende de un módulo externo de agenda.
- El módulo está diseñado únicamente para la gestión y consulta de expedientes clínicos dentro de la clínica de psicología.
## Restricciones del sistema

- El acceso a la información y modificación de la misma se encuentra restringido según el rol del usuario dentro del sistema.
- El sistema debe garantizar la confidencialidad de la información clínica de los pacientes.