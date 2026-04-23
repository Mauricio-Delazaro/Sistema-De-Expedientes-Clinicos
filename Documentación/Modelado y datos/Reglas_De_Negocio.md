# Reglas de negocio

## Reglas por entidad y estado

### Expediente clínico

| Estado | Reglas aplicables |
|---|---|
| Activo | ⚬ Se permite consultar expediente. <br> ⚬ Se permite modificar expediente. <br> ⚬ Se permite registrar sesión clínica. |
| Archivado | ⚬ Se permite consultar expediente. <br> ⚬ No se permite modificar expediente. <br> ⚬ No se permite registrar sesión clínica. |

### Reporte de sesión

| Estado | Reglas aplicables |
|---|---|
| Creado | ⚬ Se permite modificar el reporte. <br> ⚬ Se permite enviar el reporte a revisión. |
| Pendiente | ⚬ No se permite modificar el reporte. <br> ⚬ Se permite aprobar el reporte. <br> ⚬ Se permite rechazar el reporte. |
| Aprobado | ⚬ No se permite modificar el reporte. |
| Rechazado | ⚬ Se permite modificar el reporte. <br> ⚬ Se permite reenviar el reporte a revisión. |

## Reglas generales del sistema

| ID | Regla |
|---|---|
| RN-01 | Solo terapeutas con registro activo en **AsignacionTerapeutaPaciente** (estado = "activa")  puede consultar el expediente. |
| RN-02 | Solo terapeutas con registro activo en **AsignacionTerapeutaPaciente** (estado = "activa")  puede modificar el expediente. |
| RN-03 | Solo terapeutas con registro activo en **AsignacionTerapeutaPaciente** (estado = "activa")  puede registrar sesiones. |
| RN-04 | Solo usuarios autorizados pueden aprobar o rechazar reportes. |
| RN-05 | No se puede modificar un expediente archivado. |
| RN-06 | No se puede modificar un reporte aprobado. |
| RN-07 | Toda modificación debe generar un registro de auditoría. |
| RN-08 | Todo acceso a expediente debe cumplir las políticas de control de acceso definidas en el [_Requisito No Funcional RNF-02_](Requisitos_No_Funcionales.md#rnf---02-control-de-acceso-a-expedientes-clinicos-basado-en-atributos). |

## Reglas de transición

| ID | Condición de transición |
|---|---|
| RT-01 | Solo reportes en estado Pendiente pueden ser aprobados o rechazados. |
| RT-02 | Solo reportes en estado Rechazado pueden volver a enviarse a revisión. |
| RT-03 | Un expediente debe estar en estado Activo para permitir registrar sesiones. |
| RT-04 | Un expediente en estado Archivado no permite modificaciones. |
