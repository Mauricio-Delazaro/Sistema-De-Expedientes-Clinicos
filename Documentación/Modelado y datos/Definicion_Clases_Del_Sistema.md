## Definición de clases del sistema

### *Usuario*
+ idUsuario: Long
+ nombreCompleto: String

### Paciente (hereda de Usuario)
Posee **Expediente**
+ edad: Int
+ fechaNacimiento: Date
+ correoElectronico: String
+ numeroTelefonico: String

### Terapeuta (hereda de Usuario)
Atiende a múltiples **Paciente** (a través de **AsignacionTerapeutaPaciente**) y elabora **ReporteSesion**
---
+ visualizarLista()
+ seleccionarExpediente()
+ editarExpediente()

**Cardinalidad:** 1:N (Un terapeuta tiene múltiples pacientes asignados)
### *AsignacionTerapeutaPaciente*
Entidad que modela la relación N:M entre Terapeuta y Paciente
+ idAsignacion: Long
+ idTerapeuta: Long
+ idPaciente: Long
+ fechaAsignacion: DateTime
+ estado: String
+ observaciones: String
+ fechaFinalizacion: DateTime
---
+ crearAsignacion()
+ modificarAsignacion()
+ finalizarAsignacion()
+ obtenerPacientesAsignados()

**Relación:** Terapeuta (1) ──→ AsignacionTerapeutaPaciente (N) ←─── Paciente (1)

**Propósito:** Permite que un terapeuta tenga múltiples pacientes asignados. El atributo `estado` es clave para RNF-02 (ABAC) - control de acceso basado en asignación activa.

### Supervisor (hereda de Usuario)
Supervisa a **Terapeuta** y revisa a **ReporteSesion**
+ aceptarReporte()
+ rechazarReporte()
+ reenviarReporte()
+ agregarNotaCorrectiva()
+ seleccionarReporte()

### Administrador (hereda de Usuario)
Registra a **InformeConsentimiento** y a **EntrevistaSocioEconomica**
+ anexarEntrevista()
+ anexarConsentimiento()
+ anexarExpediente()

### *Documento*
+ idDocumento: Long
+ fecha: Date
---
+ almacenar()

### Expediente (hereda de Documento)
Asociado a: **Paciente**
Contiene: **ReporteSesion**, **InformeConsentimiento**, **EntrevistaSocioEconomica**
+ terapeutaAsociado: String
+ fechaProxCita: DateTime
+ idPaciente: Long

### ReporteSesion (hereda de Documento)
+ duracionSesion: Int
+ observacionesClinicas: String
+ estado:String
+ comentarios: String
+ idTerapeuta: Long

### InformeConsentimiento (hereda de Documento)
+ cuerpoDelTexto: String
+ acuerdoConfidencial: String

### EntrevistaSocioEconomica (hereda de Documento)
+ ingresoFamiliar: Decimal
+ alimentacion: Decimal
+ lugarProcedencia: String
+ vivienda: String
+ estadoSaludFamiliar: Int

## Diagrama de relación de clases

![Diagrama](/Documentación/Diagramas/DCDS.png)
