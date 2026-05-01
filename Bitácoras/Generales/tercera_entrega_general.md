# Tercera Entrega – Resumen General

**Fecha de entrega:** 30 de abril

## Resumen de actividades por periodo

- **Semana 1 (2 de abril – 8 de abril):**
	- Se diseñó el sistema de auditoría del proyecto, definiendo eventos auditables, estructura de registros, flujo de generación de logs, ejemplos de salida y consideraciones de seguridad para preservar integridad y confidencialidad de la información.
	- Se elaboró el diagrama de casos de uso del sistema, identificando actores relevantes, casos principales y relaciones entre actores y funcionalidades, manteniendo coherencia con los requerimientos funcionales definidos.
	- Se desarrolló el diseño del modelo entidad-relación, detallando entidades, atributos y relaciones conforme a las reglas de negocio para estructurar de forma conceptual la base de datos del sistema.
	- Se definió la arquitectura del sistema, incluyendo componentes principales, capas, responsabilidades y justificación del enfoque arquitectónico seleccionado.
	- Se realizó la definición de tecnologías del sistema por capa, con descripción de uso, justificación técnica y coherencia con la arquitectura propuesta.

- **Semana 2 (9 de abril – 15 de abril):**
	- No se realizaron actividades durante esta semana.

- **Semana 3 (16 de abril – 22 de abril):**
	- Se corrigieron y unificaron las tecnologías del sistema para asegurar consistencia entre el mecanismo de control de acceso, el backend, la base de datos, la arquitectura y la justificación técnica en todos los documentos del proyecto.
	- Se alinearon el modelo de clases y el diccionario de datos para corregir inconsistencias en tipos de datos, nombres de atributos y restricciones, garantizando coherencia entre ambos documentos.
	- Se centralizó la definición del sistema de auditoría para eliminar duplicaciones y fragmentación, asegurando uniformidad en los campos del log, los valores de resultado y la estructura general de la documentación.
	- Se corrigió la cardinalidad de la relación terapeuta-paciente, definiendo una estructura compatible con múltiples pacientes por terapeuta y alineada con el enfoque ABAC del RNF-02.
	- Se reescribieron los criterios de aceptación de los requisitos funcionales para que fueran medibles, verificables y testables, con entradas, salidas y condiciones de éxito o fallo claramente definidas.

- **Semana 4 (23 de abril – 30 de abril):**
	- Se definió la política de acceso a los registros de auditoría, especificando actores autorizados, alcance de consulta, filtros disponibles y condiciones de solo lectura para mantener la seguridad del sistema.
	- Se elaboró la especificación de la API REST del sistema, documentando endpoints, métodos HTTP, parámetros, cuerpos de request y response, códigos de estado y autorizaciones por rol.
	- Se elaboraron diagramas de secuencia UML para los flujos críticos del sistema, incluyendo acceso a expediente clínico, ciclo de vida del reporte de sesión e intento de acceso no autorizado.
	- Se corrigió la definición de clases del sistema y se actualizó el diagrama DCDS.png para reflejar las relaciones N:M, la entidad Expediente independiente y los atributos requeridos por el modelo.
	- Se corrigió el diccionario de datos para eliminar inconsistencias estructurales, ajustar relaciones entre entidades y alinear atributos, estados y restricciones con las reglas de negocio.

## Avances respecto a la segunda entrega

En comparación con la segunda entrega, la tercera consolidó el proyecto como una documentación mucho más completa, coherente y cercana a una implementación real.

- **Consolidación de la seguridad y la auditoría:**
	- El sistema de auditoría pasó de una definición inicial a una política más completa, incorporando la consulta controlada de registros, la restricción de acceso, la trazabilidad y el tratamiento de los logs como información de solo lectura.

- **Definición formal de la integración del sistema:**
	- Se elaboró la especificación de la API REST, lo que permite vincular de forma clara los casos de uso con los endpoints, los métodos HTTP y los roles autorizados.

- **Fortalecimiento del modelado estructural:**
	- Se corrigieron y alinearon el diccionario de datos y la definición de clases, resolviendo inconsistencias de relaciones, nombres de atributos, estados y dependencias entre entidades.

- **Mejora del modelado dinámico:**
	- Se añadieron diagramas de secuencia para los flujos críticos del sistema, reforzando la comprensión de la interacción entre actores, controladores, servicios, repositorios, base de datos y auditoría.

- **Mayor coherencia documental:**
	- Se unificaron tecnologías, arquitectura, reglas de negocio, requisitos funcionales y no funcionales para que todos los documentos describan el sistema desde una misma línea conceptual.

## Cierre consolidado de la tercera entrega

Durante la tercera entrega se completaron todas las tareas asignadas al equipo. La única actividad que inicialmente quedó fuera de tiempo en la primera semana fue posteriormente concluida, por lo que al cierre de la entrega todo el trabajo planificado quedó realizado.

En conjunto, la tercera entrega deja definidos los elementos clave del sistema: arquitectura, tecnologías, modelo de datos, clases, reglas de negocio, auditoría, API REST y diagramas de secuencia para los flujos principales. Esto consolida una base documental sólida para avanzar hacia la implementación y validación del sistema.
