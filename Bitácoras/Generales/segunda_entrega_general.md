# Segunda Entrega – Resumen General

**Fecha de entrega:** 31 de marzo

## Resumen de actividades por periodo

- **Semana 1 (5 de marzo – 11 de marzo):**
	- Se definió y documentó la estructura del expediente clínico con base en normas y lineamientos aplicables.
	- Se reescribió el requisito no funcional RNF-02 (control de acceso basado en atributos) y se elaboró un diagrama BPMN para representar el flujo de acceso a expedientes.
	- Se profundizó y redefinió el requisito no funcional RNF-01 (conservación de expedientes clínicos) y se inició un diagrama BPMN del proceso de conservación.
	- Se redefinieron el alcance y los requisitos funcionales considerando nuevos actores y límites del sistema.

- **Semana 2 (12 de marzo – 18 de marzo):**
	- Se definieron los casos de uso para los actores Terapeuta y Terapeuta Supervisor y se propuso la arquitectura del módulo de expedientes clínicos.
	- Se definieron los casos de uso del actor Administrativo para la gestión y consulta de la información.
	- Se justificó el enfoque del RNF-02 y se diseñó el mecanismo de logs y auditoría en backend para registrar eventos sensibles de acceso y gestión de expedientes.
	- Se definieron las clases principales del sistema, sentando la base del modelo estructural.
	- Se avanzó en los diagramas BPMN de RNF-01 y RNF-03 como apoyo visual a los requisitos no funcionales.

- **Semana 3 (19 de marzo – 25 de marzo):**
	- Se definieron requisitos no funcionales orientados a integridad (validación de datos, restricciones por estado y consistencia entre entidades).
	- Se elaboró el diccionario de datos, documentando atributos, tipos y restricciones de las entidades.
	- Se definieron reglas de negocio y modelo de estados para las entidades principales, incluyendo transiciones y acciones permitidas/restringidas.
	- Se definieron requisitos no funcionales relacionados con auditoría (acciones auditables, estructura de registro y alcance de la trazabilidad).

## Avances respecto a la primera entrega

En la primera entrega semanal se habían establecido únicamente:

- La identificación, redacción y organización de los **requisitos funcionales** del sistema.
- La identificación y redacción de los **requisitos no funcionales** de manera general.

Durante la segunda entrega, a partir de esa base, se lograron los siguientes avances concretos:

- **Profundización y especialización de requisitos no funcionales:**
	- RNF-01 (conservación de expedientes) y RNF-02 (control de acceso basado en atributos) se redefinieron y detallaron, y se comenzó el trabajo sobre otros RNF enfocados en integridad y auditoría.
	- Se justificaron formalmente los enfoques seleccionados (particularmente RNF-02) y su impacto en el diseño del sistema.

- **Formalización del modelo de negocio y comportamiento del sistema:**
	- Se definieron las reglas de negocio y el modelo de estados de las entidades principales, clarificando transiciones y restricciones operacionales.

- **Definición de la estructura de la información:**
	- Se definió la estructura del expediente clínico conforme a normas y lineamientos.
	- Se elaboró un diccionario de datos que normaliza atributos, tipos y restricciones, reduciendo ambigüedades y duplicidades respecto a la primera entrega.

- **Avances en diseño de arquitectura y modelo estructural:**
	- Se definió la arquitectura del módulo de expedientes clínicos y la organización interna del código.
	- Se definieron las clases del sistema, construyendo un modelo estructural alineado con requisitos y reglas de negocio.

- **Diseño de mecanismos de seguridad, acceso y auditoría:**
	- Se diseñó el mecanismo de logs y auditoría en backend para eventos sensibles de acceso y gestión de expedientes.
	- Se definieron requisitos no funcionales específicos de auditoría y trazabilidad.

- **Apoyo visual y de procesos mediante diagramas:**
	- Se elaboraron y extendieron diagramas BPMN para RNF-01, RNF-02 y RNF-03, fortaleciendo la comprensión de los procesos de conservación, acceso y otros aspectos no funcionales.

## Análisis consolidado de participación (segunda entrega)

A partir de las tablas semanales de la segunda entrega, se obtiene la siguiente vista consolidada de issues asignados y completados por integrante (suma de las tres semanas):

| Nombre del integrante | Issues asignados (total) | Issues completados (total) | Participación relativa en issues (%) | Resumen consolidado de actividades principales |
|-----------------------|--------------------------|----------------------------|--------------------------------------|-----------------------------------------------|
| Christopher May       | 4                        | 4                          | 26.7%                                | Reescritura y profundización de RNF-02, justificación del enfoque de control de acceso basado en atributos, diseño del mecanismo de logs y auditoría en backend, definición de reglas de negocio y modelo de estados para las entidades principales. |
| Mauricio De lázaro    | 4                        | 4                          | 26.7%                                | Redefinición de alcance y requisitos funcionales, definición de casos de uso de Terapeuta y Terapeuta Supervisor, definición de la arquitectura del módulo de expedientes clínicos y definición de requisitos no funcionales relacionados con auditoría del sistema. |
| Mijail Manrique       | 3                        | 3                          | 20.0%                                | Definición de la estructura del expediente clínico, definición de clases del sistema y elaboración del diccionario de datos alineado con el modelo estructural. |
| Ángel Zúñiga          | 3                        | 3                          | 20.0%                                | Profundización y redefinición de RNF-01 sobre conservación de expedientes, apoyo en los diagramas BPMN de RNF-01 y RNF-03, y definición de requisitos no funcionales orientados a integridad. |
| Adrián Chuc           | 1                        | 1                          | 6.7%                                 | Definición de casos de uso para el actor Administrativo, cubriendo acciones de gestión y consulta de información. |

En total, durante la segunda entrega se completaron **15 issues** distribuidos entre cinco integrantes. Christopher May y Mauricio De lázaro concentran juntos un poco más de la mitad de los issues (53.4%), principalmente en la definición de requisitos detallados, arquitectura, reglas de negocio y mecanismos de seguridad/auditoría. Mijail Manrique y Ángel Zúñiga aportan cada uno el 20% de los issues, enfocados en la estructura de la información (expediente clínico, clases y diccionario de datos) y en la definición y visualización de requisitos no funcionales. Adrián Chuc completa el 6.7% restante con el modelado de casos de uso administrativos.

