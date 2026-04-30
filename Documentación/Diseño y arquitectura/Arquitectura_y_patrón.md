# Arquitectura del sistema 

El sistema seguirá un estilo de **arquitectura en capas**, que organiza sus componentes en niveles con responsabilidades definidas. Este enfoque nos permitirá separar la lógica de presentación, la lógica de negocio y el acceso a datos, facilitando la mantenibilidad, escalabilidad y comprensión del sistema.

Ya que el sistema está orientado a la gestión de expedientes clínicos y reportes de sesiones clínicas, esta separación resulta importante para asegurar la correcta aplicación de reglas de negocio y la trazabilidad de las operaciones mediante auditoría.

Para su implementación, se adoptará el patrón **Controller-Service-Repository (CSR)**, que define una estructura organizada dentro de la arquitectura en capas. En esta implementación, la capa de presentación interactúa con el sistema a través de controladores (Controller), que reciben y gestionan las solicitudes del frontend. Estas solicitudes serán procesadas por la capa de lógica de negocio implementada mediante servicios (Service), donde se aplican las reglas del sistema, incluyendo la gestión de reportes clínicos, documentos y acciones de supervisión. Por último, la capa de acceso a datos está representada por repositorios (Repository), los cuales se encargan de la interacción con la base de datos, permitiendo la persistencia y recuperación de la información.

# Justificación

La elección de la arquitectura y el patrón responde a las necesidades del sistema de gestión de expedientes clinicos, pues este requiere manejar reglas de negocio, restricciones de acceso y mecanismos de auditoría.

Comparado con un modelo MVC tradicional, la arquitectura que se usará ofrece una mejor separación de las responsabilidades. En MVC, la lógica de negocio suele recaer en el modelo o los controladores, generando un alto grado de acoplamiento. En cambio, el patrón CSR introduce una capa de servicios dedicada exclusivamente a la lógica de negocio, permitiendo centralizar validaciones, reglas clínicas y controles de acceso.

Por otro lado, consideramos que arquitecturas mas complejas como microservicios son innecesarias para el alcance actual del proyecto, porque introducirian una sobrecarga en la complejidad del mismo.

# Aplicación 

La arquitectura del sistema estará implementada mediante una estructura en capas, donde cada una cumple responsabilidades específicas y definidas:

- **Capa de presentación (Frontend/Controller):** Esta capa se encargará de gestionar la interacción con el usuario, recibiendo solicitudes y devolviendo respuestas a través de la interfaz. No contiene lógica de negocio, sino que delega el procesamiento de las operaciones a la capa de servicios mediante controladores, que actúan como punto de entrada al sistema.

- **Capa de lógica de negocio (Service):** Esta capa concentrará las reglas del sistema, incluyendo validaciones, control del estado de los reportes y documentos, así como la aplicación del control de acceso basado en atributos (ABAC). Es responsable de evaluar las reglas de autorización antes de ejecutar cualquier operación sensible y de generar los registros de auditoría con resultado `PERMITIDO` o `DENEGADO`, conforme a lo definido en `Auditoria.md`. El Controller únicamente recibe la solicitud y la delega al Service sin ejecutar lógica de negocio ni de autorización.

- **Capa de acceso a datos (Repository):** Esta capa abstraerá la interacción con la base de datos, proporcionando métodos para consultar, almacenar y actualizar la información relacionada con expedientes, reportes, documentos clínicos y registros de auditoría. Incluye tanto los repositorios de datos de negocio como el repositorio de auditoría. De esta forma, se evita exponer detalles al resto del sistema.

- **Capa de persistencia (Base de datos):** Corresponde al sistema de base de datos donde se almacena de forma estructurada toda la información del sistema. Esta capa garantizará la integridad y disponibilidad de los datos, incluyendo los registros necesarios para la trazabilidad de las operaciones.

## Identificación de componentes (módulos) y capas

### 1. Capa de Presentación (frontend)

**Módulos:**
- Módulo de expedientes clínicos
- Módulo de reportes de sesión clínica
- Módulo de documentos clínicos (ej. entrevista socioeconómica)
- Módulo de supervisión (revisión de reportes)
- Módulo de auditoría (visualización de logs)

**Responsabilidades:**
- Proveer interfaces para la consulta y gestión de expedientes clínicos  
- Permitir la captura de reportes derivados de sesiones presenciales  
- Permitir la captura de información en documentos clínicos específicos  
- Validar datos a nivel de interfaz  
- Consumir servicios del backend mediante API REST  
- Mostrar información estructurada de expedientes, reportes y documentos  
- Permitir la revisión de información por parte de supervisores  
- Permitir la visualización de eventos de auditoría  

---

### 2. Capa de Lógica de Negocio (backend)

**Módulos:**
- Módulo de gestión de expedientes clínicos  
- Módulo de gestión de reportes de sesión clínica  
- Módulo de gestión de documentos clínicos  
- Módulo de supervisión  
- Módulo de auditoría  

**Responsabilidades:**
- Implementar las reglas de negocio del sistema  
- Gestionar el ciclo de vida de los expedientes clínicos  
- Permitir el registro de reportes generados a partir de sesiones presenciales  
- Gestionar la captura y actualización de documentos clínicos específicos  
- Validar la consistencia lógica de la información  
- Evaluar las reglas de autorización ABAC (rol del usuario y atributos de asignación) antes de ejecutar operaciones sensibles  
- Gestionar el proceso de revisión de reportes (aprobación, observación, rechazo)  
- Generar y persistir los registros de auditoría con resultado `PERMITIDO` o `DENEGADO` tras cada operación evaluada  
- Exponer servicios mediante API REST para el consumo del frontend  
- Coordinar la interacción con la capa de acceso a datos  

---

### 3. Capa de Acceso a Datos (backend)

**Módulos:**
- Repositorio de expedientes clínicos  
- Repositorio de reportes de sesión  
- Repositorio de documentos clínicos  
- Repositorio de auditoría (logs)  

**Responsabilidades:**
- Encapsular el acceso a la base de datos  
- Ejecutar operaciones CRUD (Create, Read, Update, Delete)  
- Gestionar consultas
- Manejar transacciones para garantizar integridad  
- Proveer acceso a la información de usuarios para validación de roles y trazabilidad  

---

### 4. Capa de Persistencia (base de datos)

**Componentes:**
- Base de datos relacional  

**Entidades principales:**
- Expedientes clínicos  
- Reportes de sesión clínica  
- Documentos clínicos  
- Registros de auditoría  
- Usuarios 

**Responsabilidades:**
- Almacenamiento seguro y estructurado de la información  
- Garantizar integridad referencial y consistencia de datos  
- Persistencia de reportes clínicos generados por terapeutas  
- Persistencia de documentos capturados en el sistema  
- Persistencia de eventos de auditoría para trazabilidad  

## Comunicación entre componentes

El flujo de comunicación del sistema se define de la siguiente manera:

1. El frontend envía solicitudes HTTP/HTTPS al backend mediante API REST (por ejemplo, para registrar un reporte de sesión o registrar un documento).  
2. El backend (lógica de negocio) procesa la solicitud, aplica las reglas de autorización ABAC y las reglas del sistema.  
3. El Service genera el registro de auditoría correspondiente (con resultado `PERMITIDO` o `DENEGADO`) y lo persiste a través del Repository de auditoría.  
4. El backend delega la operación a la capa de acceso a datos, la cual interactúa con la base de datos.  
5. La información es recuperada o almacenada y enviada de regreso al frontend para su visualización.  

## Flujo de operación del sistema

El flujo de operación del sistema está representado en el siguiente esquema:

```
Controller → Service → Repository → Base de datos
                 ↓
             Auditoría
```

En este flujo, cada solicitud es delegada por el Controller al Service, donde se evalúan las reglas de autorización ABAC y las reglas de negocio antes de interactuar con la base de datos. El Service también genera el registro de auditoría correspondiente (con resultado `PERMITIDO` o `DENEGADO`) que se persiste a través del Repository de auditoría, garantizando consistencia, seguridad y trazabilidad en cada operación.
