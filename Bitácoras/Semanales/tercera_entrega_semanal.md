# Tercera Entrega

**Fecha de entrega:** 30 de abril

## Semana 1
Periodo: 2 de abril - 8 de abril

### Tabla de avance semanal

| Nombre del integrante   | Issues asignados | Issues completados a tiempo | Avance semanal (%) | Resumen de actividades |
|-------------------------|------------------|-----------------------------|--------------------|------------------------|
| Christopher May         | 1                | 1                           | 20%                | Se diseñó el sistema de auditoría del proyecto, definiendo eventos auditables, estructura de registros, flujo de generación de logs, ejemplos de salida y consideraciones de seguridad para preservar integridad y confidencialidad de la información. |
| Ángel Zúñiga            | 1                | 1                           | 20%                | Se elaboró el diagrama de casos de uso del sistema, identificando actores relevantes, casos principales y relaciones entre actores y funcionalidades, manteniendo coherencia con los requerimientos funcionales definidos. |
| Mijail Manrique         | 1                | 1                           | 20%                | Se desarrolló el diseño del modelo entidad-relación, detallando entidades, atributos y relaciones conforme a las reglas de negocio para estructurar de forma conceptual la base de datos del sistema. |
| Mauricio De lázaro      | 1                | 1                           | 20%                | Se definió la arquitectura del sistema, incluyendo componentes principales, capas, responsabilidades y justificación del enfoque arquitectónico seleccionado. |
| Adrián Chuc             | 1                | 0                           | 0%                 | Se realizó la definición de tecnologías del sistema por capa (frontend, backend y base de datos), con descripción de uso, justificación técnica y coherencia con la arquitectura propuesta; la entrega se completó fuera de tiempo. |

### Checklist de actividades

- Christopher May
	- [x] Diseño del sistema de auditoría, incluyendo lista de eventos auditables, estructura del registro (usuario, acción, fecha y metadatos), flujo de generación de logs, ejemplos de registros y consideraciones de seguridad orientadas a integridad y confidencialidad.

- Ángel Zúñiga
	- [x] Elaboración del diagrama de casos de uso con actores relevantes, casos principales, relaciones entre actores y casos, y coherencia con los requerimientos del sistema.

- Mijail Manrique
	- [x] Diseño del modelo entidad-relación con entidades claramente definidas, atributos por entidad y relaciones correctamente identificadas.

- Mauricio De lázaro
	- [x] Definición de la arquitectura del sistema, incluyendo diagrama de arquitectura, identificación de componentes y capas, responsabilidades por elemento y justificación de la arquitectura seleccionada.

- Adrián Chuc
	- [x] Definición de tecnologías del sistema, con tecnologías por capa, descripción de uso, justificación de elección y coherencia con la arquitectura del sistema (entrega realizada fuera de tiempo).

## Semana 2
Periodo: 9 de abril - 15 de abril

No se realizaron actividades durante esta semana.

## Semana 3
Periodo: 16 de abril - 22 de abril

### Tabla de avance semanal

| Nombre del integrante   | Issues asignados | Issues completados a tiempo | Avance semanal (%) | Resumen de actividades |
|-------------------------|------------------|-----------------------------|--------------------|------------------------|
| Adrián Chuc             | 1                | 1                           | 20%                | Se corrigieron y unificaron las tecnologías del sistema para asegurar consistencia entre el mecanismo de control de acceso, el backend, la base de datos, la arquitectura y la justificación técnica en todos los documentos del proyecto. |
| Mijail Manrique         | 1                | 1                           | 20%                | Se alinearon el modelo de clases y el diccionario de datos para corregir inconsistencias en tipos de datos, nombres de atributos y restricciones, garantizando coherencia entre ambos documentos. |
| Christopher May         | 1                | 1                           | 20%                | Se centralizó la definición del sistema de auditoría para eliminar duplicaciones y fragmentación, asegurando uniformidad en los campos del log, los valores de resultado y la estructura general de la documentación. |
| Ángel Zúñiga            | 1                | 1                           | 20%                | Se corrigió la cardinalidad de la relación terapeuta-paciente, definiendo una estructura compatible con múltiples pacientes por terapeuta y alineada con el enfoque ABAC del RNF-02. |
| Mauricio De lázaro      | 1                | 1                           | 20%                | Se reescribieron los criterios de aceptación de los requisitos funcionales para que fueran medibles, verificables y testables, con entradas, salidas y condiciones de éxito o fallo claramente definidas. |

### Checklist de actividades

- Adrián Chuc
	- [x] Corrección y unificación de las tecnologías del sistema, asegurando consistencia entre el mecanismo de seguridad, el backend, la base de datos, la arquitectura y la justificación técnica.

- Mijail Manrique
	- [x] Alineación del modelo de clases y el diccionario de datos para resolver inconsistencias en tipos de datos, nombres de atributos y restricciones.

- Christopher May
	- [x] Centralización de la definición del sistema de auditoría para eliminar duplicaciones, uniformar los campos del log y mantener consistencia entre documentos.

- Ángel Zúñiga
	- [x] Corrección de la cardinalidad de la relación terapeuta-paciente, definiendo una estructura compatible con múltiples pacientes por terapeuta y alineada con RNF-02.

- Mauricio De lázaro
	- [x] Reescritura de criterios de aceptación de los requisitos funcionales para hacerlos medibles, verificables y testables.

## Semana 4
Periodo: 23 de abril - 30 de abril

### Tabla de avance semanal

| Nombre del integrante   | Issues asignados | Issues completados a tiempo | Avance semanal (%) | Resumen de actividades |
|-------------------------|------------------|-----------------------------|--------------------|------------------------|
| Christopher May         | 1                | 1                           | 20%                | Se definió la política de acceso a los registros de auditoría, especificando actores autorizados, alcance de consulta, filtros disponibles y condiciones de solo lectura para mantener la seguridad del sistema. |
| Mauricio De lázaro      | 1                | 1                           | 20%                | Se elaboró la especificación de la API REST del sistema, documentando endpoints, métodos HTTP, parámetros, cuerpos de request y response, códigos de estado y autorizaciones por rol. |
| Mijail Manrique         | 1                | 1                           | 20%                | Se elaboraron diagramas de secuencia UML para los flujos críticos del sistema, incluyendo acceso a expediente clínico, ciclo de vida del reporte de sesión e intento de acceso no autorizado. |
| Ángel Zúñiga            | 1                | 1                           | 20%                | Se corrigió la definición de clases del sistema y se actualizó el diagrama DCDS.png para reflejar las relaciones N:M, la entidad Expediente independiente y los atributos requeridos por el modelo. |
| Adrián Chuc             | 1                | 1                           | 20%                | Se corrigió el diccionario de datos para eliminar inconsistencias estructurales, ajustar relaciones entre entidades y alinear atributos, estados y restricciones con las reglas de negocio. |

### Checklist de actividades

- Christopher May
	- [x] Definición de la política de acceso a los registros de auditoría, incluyendo actores autorizados, alcance de consulta, filtros disponibles y condiciones de solo lectura.

- Mauricio De lázaro
	- [x] Elaboración de la especificación de la API REST, con endpoints, métodos HTTP, parámetros, cuerpos de request/response, códigos de estado y autorización por rol.

- Mijail Manrique
	- [x] Elaboración de diagramas de secuencia UML para los flujos críticos del sistema.

- Ángel Zúñiga
	- [x] Corrección de la definición de clases del sistema y actualización del diagrama DCDS.png para reflejar el modelo corregido.

- Adrián Chuc
	- [x] Corrección del diccionario de datos para resolver inconsistencias estructurales y alinear el modelo con las reglas de negocio.
