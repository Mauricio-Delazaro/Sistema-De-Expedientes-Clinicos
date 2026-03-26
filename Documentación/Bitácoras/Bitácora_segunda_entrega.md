# Segunda Entrega

**Fecha de entrega:** 31 de marzo
# Semana 1  
**Periodo:** 5 de marzo – 11 de marzo

## Participación del equipo

### Mijail Manrique
**Actividad:** Estructura del expediente clínico  

1. Se investigaron las normas y reglas que deben seguirse para la elaboración de la estructura de un expediente clínico. A partir de esta investigación se agregaron los apartados pertinentes en la estructura del expediente, tomando en cuenta las regulaciones y lineamientos identificados.

**Estado de la tarea:** 
1. Sí (PR #19)

### Christopher May
**Actividad:** RNF-02 – Control de acceso a expedientes clínicos basado en atributos  

1. Se realizó la reescritura del requisito no funcional con el objetivo de profundizar en las herramientas y técnicas que podrían emplearse para su correcta implementación dentro del sistema.  
Adicionalmente, se elaboró un diagrama BPMN como apoyo visual para representar el funcionamiento del control de acceso a los expedientes clínicos.

**Estado de la tarea:** 
1. Sí (PR #24)


### Ángel Zúñiga
**Actividad:** RNF-01 – Conservación de expedientes clínicos  

1. Se trabajó en profundizar y redefinir el requisito no funcional relacionado con la conservación de los expedientes clínicos, detallando las acciones que el sistema deberá realizar para cumplir con este requisito en futuras etapas de implementación.  
Además, se inició la elaboración de un diagrama BPMN para visualizar el funcionamiento del proceso.

**Estado de la tarea:** 
1. Sí (PR #21)

### Mauricio De lázaro
**Actividad:** Redefinición del alcance y requisitos funcionales  

1. Se realizó una definición del alcance y las limitaciones del proyecto, con el objetivo de clarificar los límites del sistema y su integración dentro del proyecto general. 
Además, se redefinieron los requisitos funcionales tomando en cuenta los nuevos actores identificados dentro del sistema.

**Estado de la tarea:** 
1. Sí (PR #10, #23)

# Semana 2  
**Periodo:** 12 de marzo – 18 de marzo

## Participación del equipo

### Mauricio De lázaro
**Actividades:** Definición de casos de uso para actores clínicos y arquitectura a usar

1. Se encargará de definir los casos de uso correspondientes a los actores Terapeuta y Terapeuta Supervisor, describiendo las interacciones que estos actores tendrán con el sistema y las funcionalidades que podrán ejecutar dentro del mismo.

2. Se encargará de definir la arquitectura del módulo de expedientes clínicos dentro del sistema, estableciendo la organización interna del código y la separación de responsabilidades.

**¿Se completó?:** 
1. Sí (Issue #29, PR #36)
2. Sí (Issue #28, PR #36)


### Adrián Chuc
**Actividad:** Definición de casos de uso para el actor administrativo  

1. Se encargará de definir los casos de uso relacionados con el actor Administrativo, estableciendo las acciones y operaciones que este actor podrá realizar dentro del sistema para la gestión y consulta de la información.

**¿Se completó?:** 
1. Sí (Issue #30, PR#37)

### Christopher May
**Actividades:** Justificación del enfoque del RNF-02 y diseño de logs y auditoría

1. Se encargará de desarrollar una justificación del enfoque seleccionado para el requisito no funcional RNF-02 (Control de acceso basado en atributos).  
Esto incluirá la explicación de las razones para adoptar este enfoque y la forma en que será representado e implementado dentro del sistema.

2. Se encargará de diseñar e implementar el mecanismo de logs y auditoría en backend para registrar eventos sensibles relacionados con el acceso y gestión de expedientes clínicos, definiendo los eventos auditables, los datos asociados a cada registro y las restricciones de persistencia e integridad, garantizando trazabilidad sobre accesos y modificaciones de expedientes clínicos, así como el registro de intentos de acceso permitidos y denegados.

**¿Se completó?:** 
1. Sí (Issue #31, PR #35)
2. Sí (Issue #34, PR #35)

### Mijail Manrique
**Actividad:** Definición de clases del sistema  

1. Se encargará de realizar la definición de las clases del sistema, tomando como base los enfoques, estructuras y requisitos que se han integrado al proyecto hasta el momento.  
Este trabajo permitirá establecer una base para el modelo estructural del sistema.

**¿Se completó?:** 
1. Sí (Issue #32, PR #39)

### Ángel Zúñiga
**Actividad:** definición y diagramas BPNM RNF-01 y 03

1. Terminar el Diagrama BPMN del RFN01 Conservación de expedientes clínicos y justificación del enfoque seleccionado para el requisito no funcional RNF-01 esto conlleva a definir donde sale la secuencia de pasos y tener información suficiente. Al igual apoyara en la creación inicial del diagrama del RNF03. 
Este trabajo permitirá establecer una base visual y grafica para los requisitos no funcionales del sistema esta semana.

**Estado de la tarea:** 
1. Sí (Issue #33, PR #38)
 
# Semana 3

**Periodo:** 19 de marzo – 25 de marzo

## Participación del equipo

### Angél Zúñiga
**Actividad:** Definición de RNF enfocados en integridad  

1. Se encargará de definir los requisitos no funcionales orientados al atributo de calidad de integridad, asegurando que los datos del sistema mantengan consistencia y validez.  
Este trabajo contempla la validación de datos, las restricciones por estado y la consistencia entre entidades, alineado con las reglas de negocio del sistema.

**¿Se completó?:** 
1. Sí (Issue #43, PR #49)

### Mijail Manrique
**Actividad:** Elaboración del diccionario de datos  

1. Se encargará de elaborar el diccionario de datos del sistema, documentando los atributos de las entidades junto con su tipo de dato, descripción y restricciones correspondientes.  
Con esto se busca mantener consistencia en la nomenclatura, evitar duplicidades y conservar la alineación con el modelo de clases definido.

**¿Se completó?:** 
1. Sí (Issue #44, PR #50)

### Christopher May
**Actividad:** Definición de reglas de negocio y modelo de estados  

1. Se encargará de definir los estados de las entidades principales, sus transiciones y las reglas de negocio asociadas a cada estado, documentando acciones permitidas y restringidas dentro del sistema.  
Este trabajo permite formalizar el comportamiento del sistema por entidad y estado para mantener coherencia funcional.

**¿Se completó?:** 
1. Sí (Issue #45, PR #46)

### Mauricio De lázaro
**Actividad:** Requisitos no funcionales basados en auditoría del sistema  

1. Se encargará de definir los requisitos no funcionales relacionados con auditoría, considerando la estructura de los mecanismos de registro y las acciones auditables dentro del sistema, incluyendo acceso a expedientes, registro, envío y modificación de reportes de sesión, así como su aprobación o rechazo.

**¿Se completó?:** 
1. Sí (Issue #42, PR #47)
