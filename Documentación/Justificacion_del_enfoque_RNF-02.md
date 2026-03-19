# Justificación del enfoque
Se adopta un enfoque de control de acceso basado en atributos (ABAC) debido a que múltiples usuarios comparten el mismo rol, pero requieren acceso restringido a recursos específicos (expedientes clínicos).

Asimismo, se emplea Spring Security como framework de seguridad para evaluar las decisiones de autorización de forma centralizada antes de la ejecución de cada operación. Su elección se debe a varios puntos:

- **Integración nativa con el ecosistema Spring:** permite gestionar de forma consistente la autorización en el sistema.
    
- **Definición de reglas de acceso:** fundamental para implementar un enfoque basado en atributos (ABAC).
    
- **Centralización de la lógica:** permite centralizar la lógica de seguridad y evaluar las reglas de autorización antes de ejecutar las operaciones.

A diferencia de otros frameworks como _Apache Shiro_ o _JAAS_, Spring Security ofrece un amplio conjunto de funcionalidades, robustez y facilidad de integración, además de contar con una comunidad activa.

De esta manera, el sistema evaluará la autorización a nivel de operación, considerando atributos del usuario autenticado, como su identificador y rol, así como atributos del expediente clínico, como el terapeuta asignado.

Las reglas de acceso se aplicarán antes de ejecutar cualquier operación sobre expedientes, permitiendo únicamente aquellas en las que exista correspondencia entre el usuario autenticado y la asignación del expediente.