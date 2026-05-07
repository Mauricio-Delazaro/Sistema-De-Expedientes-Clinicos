# Issues del Proyecto — Sistema de Expedientes Clínicos

---

## Issue #1 — Diagrama de Casos de Uso Visual Ausente

### Contexto

El documento `Casos_de_uso.md` describe textualmente 12 casos de uso distribuidos entre tres actores (Terapeuta, Supervisor, Administrador), pero no existe ningún diagrama UML visual de casos de uso. Sin este diagrama es difícil visualizar el alcance del sistema, las relaciones entre actores y las dependencias entre casos de uso (include/extend).

### Actividad

Elaborar el diagrama UML de casos de uso del sistema que muestre a los tres actores y sus interacciones con el sistema, incluyendo relaciones `«include»` y `«extend»` donde corresponda.

### Criterios

Debe cumplir:

- Incluir los tres actores definidos: Terapeuta, Supervisor y Administrador
- Representar los 12 casos de uso documentados (CU-01 a CU-12)
- Mostrar relaciones `«include»` entre casos de uso dependientes (ej. CU-04 incluye CU-03)
- Mostrar relaciones `«extend»` donde aplique (ej. CU-05 extiende CU-03)
- El diagrama debe ser coherente con `Casos_de_uso.md` y `Requisitos_Funcionales.md`
- Guardarse en la carpeta `Documentación/Diagramas/`

---

## Issue #2 — Ausencia de Diagrama Entidad-Relación (ER)

### Contexto

El sistema tiene un diccionario de datos (`Diccionario_Datos.md`) y una definición de clases (`Definicion_Clases_Del_Sistema.md`), pero no existe ningún Diagrama Entidad-Relación que muestre la estructura de la base de datos relacional. Esto es un vacío crítico antes de comenzar la implementación, ya que el diccionario de datos describe atributos pero no las cardinalidades ni las claves foráneas necesarias para el esquema de persistencia.

### Actividad

Crear el Diagrama Entidad-Relación (ER) del sistema de expedientes clínicos que sirva como especificación directa para el esquema de base de datos relacional.

### Criterios

Debe cumplir:

- Incluir todas las entidades: Usuario, Paciente, Terapeuta, Supervisor, Administrador, Expediente, ReporteSesion, InformeConsentimiento, EntrevistaSocioeconomica, RegistroAuditoria
- Definir cardinalidades correctas para cada relación (1:1, 1:N, N:M)
- Especificar claves primarias (PK) y claves foráneas (FK) para cada entidad
- Ser consistente con el `Diccionario_Datos.md` en tipos y restricciones de campos
- Incluir la tabla de auditoría (`RegistroAuditoria`) como entidad independiente
- Guardarse en la carpeta `Documentación/Diagramas/`

---

## Issue #3 — Ausencia de Diagramas de Secuencia para Flujos Críticos

### Contexto

El proyecto documenta casos de uso y arquitectura en capas (Controller → Service → Repository), pero no cuenta con ningún diagrama de secuencia UML. Estos diagramas son esenciales para especificar cómo interactúan los componentes del sistema en tiempo de ejecución para los flujos más importantes, especialmente aquellos que involucran autorización ABAC y auditoría.

### Actividad

Elaborar diagramas de secuencia UML para los flujos críticos del sistema, mostrando la interacción entre capas (Controller, Service, Repository) y los actores.

### Criterios

Debe cumplir:

- Crear diagrama de secuencia para: acceso a expediente clínico (incluyendo validación ABAC y registro de auditoría)
- Crear diagrama de secuencia para: ciclo de vida completo del reporte de sesión (creación → envío → aprobación/rechazo)
- Crear diagrama de secuencia para: intento de acceso no autorizado (respuesta del sistema y registro en auditoría)
- Cada diagrama debe mostrar los participantes: Actor, Controller, Service, Repository, BD, y el componente de Auditoría
- Los diagramas deben ser coherentes con `Arquitectura_y_patrón.md` y `Auditoria.md`
- Guardarse en la carpeta `Documentación/Diagramas/`

---

## Issue #4 — Ausencia de Diagrama de Componentes y Despliegue

### Contexto

El documento `Arquitectura_y_patrón.md` describe una arquitectura en capas con el patrón Controller-Service-Repository, pero no existe ningún diagrama de componentes ni de despliegue que muestre cómo se estructura físicamente el sistema. Dado que se menciona Spring Boot y Spring Security como tecnologías base, es necesario documentar los componentes de software y cómo se despliegan.

### Actividad

Crear un diagrama de componentes y un diagrama de despliegue que materialicen la arquitectura descrita en `Arquitectura_y_patrón.md`.

### Criterios

Debe cumplir:

- El diagrama de componentes debe identificar: módulos de la capa Controller, Service y Repository, componente de autorización ABAC (Spring Security), componente de auditoría
- El diagrama de despliegue debe mostrar: servidor de aplicaciones (Spring Boot), base de datos relacional, cliente (frontend/consumidor de API)
- Ambos diagramas deben usar notación UML estándar
- Deben ser coherentes con `Arquitectura_y_patrón.md`, `Auditoria.md` y `Justificacion_del_enfoque_RNF-02.md`
- Guardarse en la carpeta `Documentación/Diagramas/`

---

## Issue #5 — Ausencia de Diagrama de Estados Visual

### Contexto

El documento `Modelo_De_Estados.md` describe en formato de tabla los estados de Expediente (Activo/Archivado) y de ReporteSesion (Creado/Pendiente/Aprobado/Rechazado), incluyendo las transiciones válidas. Sin embargo, no existe ningún diagrama de máquina de estados UML que visualice estas transiciones de forma gráfica, lo cual es un artefacto estándar del diseño de software.

### Actividad

Elaborar diagramas de máquina de estados UML para las dos entidades con ciclo de vida definido en el sistema.

### Criterios

Debe cumplir:

- Crear diagrama de estados para Expediente Clínico: estados Activo y Archivado, con sus transiciones y operaciones habilitadas en cada estado
- Crear diagrama de estados para ReporteSesion: estados Creado, Pendiente, Aprobado y Rechazado, con guardas y eventos de transición
- Indicar el estado inicial y los estados finales (si aplican)
- Incluir las operaciones permitidas por estado como notas en el diagrama
- Ser coherentes con `Modelo_De_Estados.md` y `Reglas_De_Negocio.md`
- Guardarse en la carpeta `Documentación/Diagramas/`

---

## Issue #6 — Inconsistencia en la Nomenclatura del Estado Inicial del Reporte

### Contexto

Existe una contradicción entre documentos en cuanto al nombre del estado inicial de un ReporteSesion. El documento `Requisitos_No_Funcionales.md` (RNF-09) menciona el estado `borrador` como estado inicial, mientras que `Modelo_De_Estados.md` y `Definicion_Clases_Del_Sistema.md` utilizan `Creado`. Esta ambigüedad puede generar inconsistencias en la implementación de la máquina de estados y en la base de datos.

### Actividad

Unificar la nomenclatura del estado inicial del ReporteSesion en todos los documentos del proyecto, eligiendo un único término canónico y aplicándolo consistentemente.

### Criterios

Debe cumplir:

- Definir un único término para el estado inicial del reporte y documentar la decisión con justificación
- Actualizar todos los documentos afectados: `Requisitos_No_Funcionales.md`, `Modelo_De_Estados.md`, `Definicion_Clases_Del_Sistema.md`, `Diccionario_Datos.md`, `Reglas_De_Negocio.md`
- El valor del campo `estado` en `Diccionario_Datos.md` debe listar todos los estados válidos como un enum explícito
- No debe haber ninguna referencia a términos alternativos en ningún documento
- Actualizar los diagramas BPMN si el término aparece en ellos

---

## Issue #7 — Correcciones al Diccionario de Datos

### Contexto

El documento `Diccionario_Datos.md` presenta múltiples errores estructurales y omisiones que impiden usarlo como fuente de verdad para el diseño de la base de datos. Los errores identificados son:

- La entidad `Terapeuta` define un único `idPacienteAsignado: Long`, limitando incorrectamente la relación con Paciente a un solo registro, cuando en el dominio real un terapeuta atiende a múltiples pacientes (RF-01)
- La entidad `Expediente` declara herencia simultánea de `Usuario`, `Documento` y `Paciente`, lo cual es conceptualmente incorrecto ya que un expediente no es un usuario ni un paciente, sino que pertenece a uno
- El campo de gasto en alimentación en `EntrevistaSocioeconomica` está nombrado de forma incompleta como `alimentacion`
- La relación Supervisor-Terapeuta, necesaria para que RF-06 funcione correctamente, no está definida en el modelo
- El campo `estado` de la entidad `Expediente` no está documentado, pese a que `Modelo_De_Estados.md` define dos estados (Activo/Archivado) con reglas de negocio distintas
- La entidad `ReporteSesion` omite campos requeridos por los requisitos funcionales: `fechaSesion` (RF-03, RF-06), separación entre comentarios del terapeuta y del supervisor (RF-08) y marcas de tiempo de trazabilidad (RNF-07)
- El campo `terapeutaAsociado` en `Expediente` almacena el nombre del terapeuta como texto libre, lo que impide la validación de acceso ABAC (RNF-02) y rompe la integridad referencial

### Actividad

Corregir todos los errores identificados directamente en `Diccionario_Datos.md`.

### Criterios

Debe cumplir:

- Reemplazar `idPacienteAsignado: Long` en `Terapeuta` por una tabla de asociación `terapeuta_paciente` con campos `idTerapeuta` (FK) e `idPaciente` (FK) y clave primaria compuesta
- Eliminar la herencia múltiple de `Expediente`; redefinirla como entidad independiente con `idExpediente` (PK), `idPaciente` (FK hacia Paciente) e `idTerapeuta` (FK hacia Terapeuta)
- Renombrar `alimentacion` a `gastoAlimentacion` en `EntrevistaSocioeconomica` para consistencia con `ingresoFamiliar`
- Agregar la tabla de asociación `supervisor_terapeuta` con campos `idSupervisor` (FK) e `idTerapeuta` (FK) y clave primaria compuesta
- Agregar el campo `estado` (ENUM: `ACTIVO`, `ARCHIVADO`, valor por defecto `ACTIVO`, NOT NULL) a la entidad `Expediente`
- En `ReporteSesion`: agregar `fechaSesion` (Date, NOT NULL); renombrar `comentarios` a `comentariosTerapeuta`; agregar `comentariosSupervisor` (String, nullable); agregar `fechaCreacion` y `fechaModificacion` (DateTime, NOT NULL)
- Reemplazar `terapeutaAsociado: String` por `idTerapeuta: Long` (FK hacia Terapeuta) en `Expediente`
- Todos los cambios deben ser coherentes con `Modelo_De_Estados.md`, `Reglas_De_Negocio.md` y `Auditoria.md`

---

## Issue #8 — Correcciones a la Definición de Clases del Sistema

### Contexto

El documento `Definicion_Clases_Del_Sistema.md` presenta los mismos errores estructurales que `Diccionario_Datos.md`, además de inconsistencias propias del modelo de clases: `duracionSesion` está tipado como `String` en lugar de `Int`, y el campo de alimentación en `EntrevistaSocioeconomica` está mal nombrado como `gastAlimentacion`. El diagrama `DCDS.png` referenciado al final del documento también deberá actualizarse para reflejar el modelo corregido.

### Actividad

Aplicar las correcciones al modelo de clases en `Definicion_Clases_Del_Sistema.md` y actualizar el diagrama `DCDS.png`.

### Criterios

Debe cumplir:

- En `Terapeuta`: reemplazar `idPacienteAsignado: Long` por `pacientesAsignados: List<Long>`; actualizar la descripción de la relación con `Paciente` a N:M
- En `Supervisor`: agregar el atributo `terapeutasAsignados: List<Long>`; actualizar la descripción de la relación con `Terapeuta` a N:M
- En `Expediente`: eliminar la herencia de `Documento`; redefinirla como entidad independiente con `idExpediente: Long`, `idPaciente: Long`, `idTerapeuta: Long`, `estado: String` (ACTIVO | ARCHIVADO) y `fechaProxCita: DateTime`; eliminar los atributos heredados de `Paciente` y `Usuario` que no le pertenecen (nombreCompleto, edad, fechaNacimiento, correoElectronico, numeroTelefonico)
- En `ReporteSesion`: agregar `fechaSesion: Date`, `comentariosSupervisor: String`, `fechaCreacion: DateTime`, `fechaModificacion: DateTime`; renombrar `comentarios` a `comentariosTerapeuta`; corregir el tipo de `duracionSesion` de `String` a `Int`
- En `EntrevistaSocioeconomica`: renombrar `gastAlimentacion` a `gastoAlimentacion`
- Actualizar el diagrama `DCDS.png` para reflejar: relación N:M Terapeuta-Paciente, relación N:M Supervisor-Terapeuta, `Expediente` como entidad independiente con FKs explícitas
- Los cambios deben ser coherentes con los aplicados en `Diccionario_Datos.md` (Issue #7)

---

## Issue #9 — Falta de Especificación del Control de Acceso a los Registros de Auditoría

### Contexto

El sistema define un robusto esquema de auditoría en `Auditoria.md` y `Requisitos_No_Funcionales.md` (RNF-04 a RNF-07), especificando qué eventos se registran y cómo. Sin embargo, en ningún documento se especifica quién puede *consultar* estos registros de auditoría, bajo qué condiciones, ni a través de qué interfaz. Esto es un vacío de seguridad y diseño: sin especificarlo, la implementación podría dejar los logs inaccesibles o, peor, accesibles para usuarios no autorizados.

### Actividad

Definir y documentar la política de acceso a los registros de auditoría, incluyendo los actores autorizados, el alcance de consulta permitido y los mecanismos de acceso.

### Criterios

Debe cumplir:

- Especificar qué roles tienen permiso de leer registros de auditoría (ej. ¿solo Administrador?, ¿un rol de auditor externo?)
- Definir si la consulta de auditoría es parte del alcance de este módulo o de otro módulo del sistema
- Especificar qué filtros de consulta están disponibles (por usuario, por fecha, por tipo de acción, por recurso)
- Agregar un requisito funcional o caso de uso para la consulta de auditoría si corresponde al alcance
- Documentar que los registros de auditoría son de solo lectura y no eliminables, incluso para roles con acceso
- Actualizar `Auditoria.md` y `Requisitos_No_Funcionales.md` con esta especificación

---

## Issue #11 — Ausencia de Esquema Relacional (DDL) de la Base de Datos

### Contexto

El sistema cuenta con un diccionario de datos (`Diccionario_Datos.md`) y una definición de clases (`Definicion_Clases_Del_Sistema.md`) que describen las entidades, sus atributos y relaciones. Sin embargo, no existe ningún documento que formalice el esquema físico de la base de datos relacional: las tablas concretas, sus columnas tipadas, las claves primarias y foráneas, las restricciones de integridad (`CHECK`, `UNIQUE`, `NOT NULL`) ni los índices necesarios para el rendimiento. Este vacío impide iniciar la implementación de la capa de persistencia de manera coordinada y correcta.

La elección de la estrategia de herencia (Single Table, Joined Table o Table per Concrete Class) tampoco está documentada, siendo una decisión de diseño que impacta directamente en el ORM y en la estructura de la base de datos.

### Actividad

Crear el documento `Diseño_Base_De_Datos.md` en `Documentación/Diseño y arquitectura/` que contenga el esquema relacional completo del sistema, incluyendo el diagrama Entidad-Relación en notación Mermaid y las sentencias DDL SQL de definición de todas las tablas.

### Criterios

Debe cumplir:

- Justificar y documentar la estrategia de herencia utilizada para las jerarquías `Usuario` y `Documento`
- Incluir el diagrama ER completo en notación Mermaid con todas las entidades físicas, sus atributos (tipo + restricciones clave), PKs, FKs y cardinalidades
- Definir en DDL SQL todas las tablas: `usuario`, `paciente`, `terapeuta`, `supervisor`, `administrador`, `terapeuta_paciente`, `supervisor_terapeuta`, `expediente`, `documento`, `reporte_sesion`, `informe_consentimiento`, `entrevista_socioeconomica`, `registro_auditoria`
- Cada tabla debe declarar explícitamente: clave primaria (`PRIMARY KEY`), claves foráneas (`FOREIGN KEY`), restricciones de unicidad (`UNIQUE`) y restricciones de valor (`CHECK`) derivadas del `Diccionario_Datos.md`
- Incluir los índices recomendados para las consultas más frecuentes del sistema (búsqueda por terapeuta, por estado de reporte, filtros de auditoría)
- La tabla `registro_auditoria` no debe declarar FK hacia `usuario` para preservar registros históricos ante eliminación de usuarios
- El esquema debe ser coherente con `Diccionario_Datos.md`, `Modelo_De_Estados.md`, `Reglas_De_Negocio.md` y `Auditoria.md`
- Guardarse en `Documentación/Diseño y arquitectura/Diseño_Base_De_Datos.md`

---

## Issue #10 — Ausencia de Especificación de la API REST

### Contexto

La arquitectura del sistema define una capa Controller que recibe solicitudes y devuelve respuestas, lo que implica una API REST. Sin embargo, en ningún documento se especifican los endpoints, métodos HTTP, parámetros, cuerpos de request/response, ni códigos de respuesta. Esta falta de especificación impide la implementación coordinada entre diferentes desarrolladores y la validación del cumplimiento de requisitos funcionales.

### Actividad

Crear un documento de especificación de la API REST del sistema, cubriendo todos los endpoints necesarios para satisfacer los 12 casos de uso documentados.

### Criterios

Debe cumplir:

- Documentar al menos un endpoint por cada caso de uso (CU-01 a CU-12)
- Para cada endpoint especificar: método HTTP, ruta, parámetros de path/query, cuerpo del request (JSON), cuerpo del response (JSON) y códigos de respuesta HTTP (200, 201, 400, 403, 404, etc.)
- Incluir los endpoints de auditoría si el módulo los expone
- Indicar qué rol tiene autorización para cada endpoint
- El documento debe guardarse en `Documentación/Diseño y arquitectura/API_REST.md`
- Debe ser coherente con `Arquitectura_y_patrón.md`, `Requisitos_Funcionales.md` y `Auditoria.md`
