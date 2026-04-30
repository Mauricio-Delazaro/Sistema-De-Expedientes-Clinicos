# Casos de uso
## Actor: Terapeuta

### CU-01: Visualizar pacientes asignados

#### Descripción:
El terapeuta consulta la lista de pacientes que tiene asignados.

### Flujo principal:
1. El terapeuta accede al módulo de pacientes.
2. El sistema valida que el usuario autenticado tenga rol Terapeuta y que exista asignación registrada entre el terapeuta y los pacientes.
3. El sistema filtra los pacientes por asignación.
4. El sistema muestra:
    - Nombre del paciente
    - Número de expediente
### Postcondiciones:
- Solo se muestran pacientes asignados al terapeuta.

### RF relacionados: 
- RF-01
---
### CU-02: Acceder a expediente clínico

### Descripción:
El terapeuta consulta el expediente de un paciente asignado.

### Flujo principal:
1. El terapeuta selecciona un paciente asignado.
2. El sistema valida:
    - Rol autorizado = Terapeuta
    - Relación terapeuta-expediente válida
3. El sistema autoriza el acceso.
4. El sistema muestra el expediente y sus apartados.

### Flujo alterno 
- Paciente no asignado -> acceso denegado.

### Postcondiciones:
- Acceso permitido solo si está autorizado

### RF relacionados: 
- RF-02 
--- 

### CU-03: Registrar reporte de sesión

### Descripción: 
El terapeuta registra un reporte en un expediente autorizado.

### Flujo principal:
1. El terapeuta accede al expediente
2. El sistema valida la autorización sobre el paciente
3. El terapeuta accede al control de sesiones
4. El terapeuta ingresa datos en el reporte de sesión:
    - Fecha
    - Duración
    - Observaciones
5. El sistema almacena el reporte

### Flujo alterno:
- Intento de registrar un reporte vacío -> registro denegado.

### Postcondiciones:
- Reporte se registra correctamente

### RF relacionados:
- RF-03
---

### CU-04: Enviar reporte a revisión

### Descripción:
El terapeuta envía un reporte al supervisor.

### Flujo principal:
1. El terapeuta selecciona un reporte propio.
2. El sistema valida:
    - Propiedad del reporte válida
3. El terapeuta envía el reporte a revisión.
4. El sistema envía el reporte al supervisor
5. El sistema cambia el estado del reporte a "pendiente de revisión".

### Flujo alterno:
- Reporte no pertenece al terapeuta -> envío denegado.

### Postcondiciones: 
- El reporte se envía al supervisor y queda en estado "pendiente de revisión".

### RF relacionados:
- RF-04 
---

### CU-05: Modificar reporte rechazado

### Descripción:
El terapeuta edita un reporte rechazado.

### Flujo principal:
1. El terapeuta accede a sus reportes
2. El sistema filtra reportes por:
    - Estado = rechazado
    - Propietario = terapeuta
3. El terapeuta selecciona el reporte
4. El terapeuta modifica el reporte
5. El terapeuta guarda los cambios del reporte
6. El terapeuta reenvía el reporte a revisión
7. El sistema envía el reporte al supervisor
8. El sistema cambia el estado del reporte a "pendiente de revisión".

### Postcondiciones:
- El reporte se envía al supervisor y queda en estado "pendiente de revisión".

### RF relacionados:
- RF-05

## Actor: Supervisor 

Restricción: El sistema solo debe permitir acceso únicamente a reportes de terapeutas bajo su supervisión.

### CU-06: Visualizar reportes pendientes de revisión

### Descripción:
El supervisor consulta la lista de reportes en estado pendiente enviados por terapeutas a su cargo.

### Flujo principal:
1. El supervisor accede al módulo de revisión
2. El sistema valida que el usuario tenga el rol de Supervisor.
3. El sistema filtra reportes por:
    - Estado = pendiente de revisión
    - Terapeutas bajo supervisión

4. El sistema muestra:
    - Identificador del reporte
    - Nombre del terapeuta
    - Fecha de la sesión

### Postcondiciones: 
- Solo se muestran reportes autorizados
### RF relacionados:
- RF-06
---

### CU-07: Visualizar contenido de reporte

### Descripción:
El supervisor consulta el detalle de un reporte de sesión.

### Flujo principal: 
1. El supervisor selecciona un reporte autorizado.
2. El sistema valida:
    - Rol autorizado = Supervisor
    - Relación supervisor-terapeuta válida
3. El sistema muestra:
    - Terapeuta
    - Paciente
    - Fecha
    - Duración
    - Observaciones

### Postcondiciones
- La información del reporte es mostrada correctamente

### RF relacionados:
- RF-07
--- 

### CU-08: Aprobar reporte de sesión

### Descripción: 
El supervisor aprueba un reporte revisado.

### Flujo principal:
1. El supervisor accede a un reporte pendiente
2. El sistema valida:
    - Estado = pendiente de revisión
    - Autorización sobre el reporte válida
3. El supervisor selecciona "Aprobar"
4. El sistema actualiza el estado a "aprobado"
5. El sistema registra la acción

### Postcondiciones:
- El reporte se registra como aprobado

### RF relacionados: 
- RF-08
---
### CU-09: Rechazar reporte de sesión

### Descripción:
El supervisor rechaza un reporte y registra comentarios.

### Flujo principal:
1. El supervisor accede a un reporte pendiente
2. El sistema valida autorización
3. El supervisor selecciona "Rechazar"
4. Ingresa comentarios
5. El sistema:
    - Guarda comentarios
    - Cambia estado a "rechazado"
    - Notifica al terapeuta

### Postcondiciones:
- El reporte es rechazado y enviado de nuevo al terapeuta

### RF relacionados:
- RF-08

---
## Actor: Administrador

### CU-10: Registrar expediente clínico

### Descripción:
Permite al administrador crear el expediente clínico asociado a un paciente registrado en el sistema.

### Flujo principal:
1. El administrador accede al módulo de expedientes clínicos.
2. El administrador selecciona la opción crear expediente clínico.
3. El sistema solicita el identificador del paciente.
4. El administrador ingresa la información requerida.
5. El sistema valida que el paciente exista en el sistema.
6. El sistema crea el expediente clínico asociado al paciente.
7. El sistema confirma que el expediente fue registrado correctamente.
### Postcondiciones: 
- El paciente queda asociado a un expediente clínico dentro del sistema

### RF relacionados:
- RF-09
---
### CU-11: Registrar entrevista socioeconómica

### Descripción:
Permite al administrador registrar la información correspondiente a la entrevista socioeconómica de un paciente.

### Flujo principal:
1. El administrador accede al expediente clínico del paciente
2. El administrador selecciona la opción registrar entrevista socioeconómica.
3. El sistema muestra el formulario correspondiente.
4. El administrador captura la información de la entrevista
5. El administrador guarda la información.
6. El sistema almacena los datos en el expediente clínico del paciente.

### Postcondiciones:
- La información de la entrevista socioeconómica queda almacenada en el expediente del paciente.

### RF relacionados:
- RF-10
---
### CU-12: Registrar consentimiento informado

### Descripción:
Permite al administrador registrar la información correspondiente al acuerdo de consentimiento firmado por el paciente.

### Flujo principal:
1. El administrador accede al expediente clínico del paciente.
2. El administrador selecciona la opción registrar consentimiento informado
3. El sistema muestra el formulario de registro.
4. El administrador captura la información del documento firmado.
5. El administrador guarda la información.
6. El sistema almacena el documento dentro del expediente clínico del paciente.

### Postcondiciones:
- El consentimiento informado queda registrado en el expediente clínico del paciente.

### RF relacionados:
- RF-11
---
### CU-13: Consultar registros de auditoría

### Descripción:
El Administrador consulta el historial de eventos registrados por el sistema de auditoría.

### Flujo principal:
1. El Administrador accede al módulo de auditoría.
2. El sistema valida rol = Administrador.
3. El Administrador aplica de forma opcional filtros de búsqueda:
    - Identificador de usuario
    - Rango de fechas
    - Tipo de acción
    - Recurso e identificador de recurso
    - Resultado (`PERMITIDO` / `DENEGADO`)
4. El sistema retorna la lista de registros que cumplen los criterios.
5. El sistema muestra por cada registro:
    - Identificador del log
    - Usuario y rol
    - Acción realizada
    - Recurso e identificador del recurso
    - Fecha y hora
    - Resultado

### Flujo alterno:
- Usuario con rol Terapeuta o Supervisor intenta acceder al módulo → acceso denegado.

### Postcondiciones:
- Los registros mostrados son de solo lectura; no es posible modificarlos ni eliminarlos desde la interfaz.

### RF relacionados:
- RF-12
