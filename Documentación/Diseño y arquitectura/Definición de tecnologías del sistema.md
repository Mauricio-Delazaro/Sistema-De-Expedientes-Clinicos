# Tecnologías del sistema

## Frontend

- **Tecnología:** HTML, CSS y JavaScript

- **Descripción:**
Se utilizarán para desarrollar la interfaz de usuario que permitirá a médicos y personal interactuar con el sistema de expedientes clínicos a través de un navegador web.

- **Justificación:**
Dado que el sistema está planteado como una arquitectura por capas, es necesario contar con una capa de presentación. HTML, CSS y JavaScript son tecnologías estándar, accesibles y suficientes para implementar una interfaz funcional sin agregar complejidad innecesaria.

## Backend

- **Tecnología:** Java

- **Descripción:**
Se utilizará Java para implementar la lógica del sistema, incluyendo la gestión de pacientes, expedientes clínicos, validaciones y reglas de negocio.

- **Justificación:**
El sistema cuenta con una definición clara de clases, reglas de negocio y modelo orientado a objetos, lo cual es altamente compatible con Java. Además, permite estructurar adecuadamente la arquitectura en capas y facilita el mantenimiento del sistema.

## Base de datos

- **Tecnología:** MySQL

- **Descripción:**
Se utilizará para almacenar la información de pacientes, expedientes clínicos, usuarios y registros del sistema.

- **Justificación:**
El sistema maneja datos estructurados con relaciones claras, como se observa en el diccionario de datos y la estructura del expediente clínico. MySQL es una base de datos relacional adecuada para este tipo de información.

## Herramientas de apoyo

**Herramientas:**

- Git y GitHub / Visual Studio Code

- **Descripción:**
Se utilizarán para el control de versiones, desarrollo del código y colaboración en equipo.

- **Justificación:**
El proyecto ya se encuentra en un repositorio GitHub, lo que hace coherente el uso de estas herramientas para mantener organización y control del desarrollo.

## Coherencia con la arquitectura

Las tecnologías seleccionadas son coherentes con la arquitectura del sistema, ya que:

- Se respeta la separación por capas (presentación, lógica y datos)
- Se alinean con el modelo orientado a objetos definido
- Permiten implementar las reglas de negocio documentadas
- Facilitan la escalabilidad y mantenimiento del sistema