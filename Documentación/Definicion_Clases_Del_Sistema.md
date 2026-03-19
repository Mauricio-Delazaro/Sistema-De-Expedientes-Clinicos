## Definición de clases del sistema

### *Usuario*
+ id: Long
+ nombreCompleto: String

### Paciente (hereda de Usuario)
Posee **Expediente**
+ edad: int
+ fechaNacimiento: Date
+ numeroTelefonico: String

### Terapeuta (hereda de Usuario)
Atiende a **Paciente** y elabora **ReporteSesion**
+ idPacienteAsignado: Long
---
+ visualizarLista()
+ seleccionarExpediente()
+ editarExpediente()

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
+ id: Long
+ fecha: Date
---
+ almacenar()

### Expediente (hereda de Documento)
Contiene a **ReporteSesion**, **InformeConsentimiento** y a **EntrevistaSocioEconomica**
+ nombreCompleto: String
+ edad: Int
+ fechaNacimiento: Date
+ correoElectronico: String
+ numeroTelefonico: String
+ terapeutaAsociado: String
+ fechaProxCita: DateTime

### ReporteSesion (hereda de Documento)
+ duracionSesion: String
+ observacionesClinicas: String
+ estado:String
+ comentarios: String
+ idTerapeuta: Long

### InformeConsentimiento (hereda de Documento)
+ cuerpoDelTexto: String
+ acuerdoConfidencial: String

### EntrevistaSocioeconomica (hereda de Documento)
+ ingresoFamiliar: double
+ gastAlimentacion: double
+ lugarProcedencia: String
+ vivienda: String
+ estadoSaludFamiliar: String

## Diagrama de relación de clases

![Diagrama](/Documentación/Diagramas/DCDS.png)