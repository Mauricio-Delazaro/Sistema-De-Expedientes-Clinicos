## Definición de clases del sistema

### *Usuario*
+ idUsuario: Long
+ nombreCompleto: String

### Paciente (hereda de Usuario)
Posee **Expediente**
+ edad: int
+ fechaNacimiento: Date
+ correoElectronico: String
+ numeroTelefonico: String

### Terapeuta (hereda de Usuario)
Atiende a múltiples **Paciente** (relación N:M) y elabora **ReporteSesion**
+ pacientesAsignados: List\<Long\>
---
+ visualizarLista()
+ seleccionarExpediente()
+ editarExpediente()

### Supervisor (hereda de Usuario)
Supervisa a múltiples **Terapeuta** (relación N:M) y revisa **ReporteSesion**
+ terapeutasAsignados: List\<Long\>
---
+ aceptarReporte()
+ rechazarReporte()
+ reenviarReporte()
+ agregarNotaCorrectiva()
+ seleccionarReporte()

### Administrador (hereda de Usuario)
Registra **InformeConsentimiento** y **EntrevistaSocioEconomica**
---
+ anexarEntrevista()
+ anexarConsentimiento()
+ anexarExpediente()

### *Documento*
+ idDocumento: Long
+ fecha: Date
---
+ almacenar()

### Expediente
Pertenece a **Paciente** (relación 1:1). Asignado a **Terapeuta** (relación N:1).
Contiene **ReporteSesion**, **InformeConsentimiento** y **EntrevistaSocioEconomica**
+ idExpediente: Long
+ idPaciente: Long
+ idTerapeuta: Long
+ estado: String  // ACTIVO | ARCHIVADO
+ fechaProxCita: DateTime

### ReporteSesion (hereda de Documento)
+ idTerapeuta: Long
+ fechaSesion: Date
+ duracionSesion: Int
+ observacionesClinicas: String
+ estado: String  // CREADO | PENDIENTE | APROBADO | RECHAZADO
+ comentariosTerapeuta: String
+ comentariosSupervisor: String
+ fechaCreacion: DateTime
+ fechaModificacion: DateTime

### InformeConsentimiento (hereda de Documento)
+ cuerpoDelTexto: String
+ acuerdoConfidencial: String

### EntrevistaSocioeconomica (hereda de Documento)
+ ingresoFamiliar: double
+ gastoAlimentacion: double
+ lugarProcedencia: String
+ vivienda: String
+ estadoSaludFamiliar: String

## Diagrama de relación de clases

![Diagrama](/Documentación/Diagramas/DCDS.jpg)
