# Logs y auditoría
El sistema incorporará un mecanismo de auditoría en backend para registrar eventos sensibles relacionados con el acceso y gestión de expedientes clínicos. La auditoría tendrá como finalidad mantener trazabilidad sobre las acciones realizadas por los usuarios autorizados y detectar intentos de acceso no permitidos.

### Eventos auditables
- Consulta de expediente clínico
- Acceso denegado a expediente clínico
- Registro de reporte de sesión
- Modificación de reporte de sesión
- Envío de reporte para revisión
- Aprobación o rechazo de reporte

### Datos registrados por evento
- Id del usuario
- Rol del usuario
- Fecha y hora del evento
- Acción realizada
- Recurso afectado
- Id del recurso
- Resultado de la operación

### Restricciones de diseño
- La auditoría deberá ejecutarse en backend.
- Todo intento de acceso a expedientes deberá generar un registro, haya sido permitido o denegado.
- Las operaciones de modificación sobre información clínica deberán dejar evidencia persistente.
- Los logs de auditoría no podrán ser modificados desde la interfaz del sistema.