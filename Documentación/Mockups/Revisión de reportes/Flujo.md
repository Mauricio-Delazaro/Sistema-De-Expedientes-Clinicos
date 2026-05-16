## Mockup 1 — Listado de reportes pendientes

Pantalla principal donde el supervisor visualiza todos los reportes de sesión en estado pendiente de revisión.

Acciones disponibles:
- Botón “Ver detalle”:
Lleva al Mockup 2 (vista detallada del reporte)

## Mockup 2 — Vista detallada del reporte

Pantalla donde el supervisor puede consultar toda la información del reporte seleccionado.

Información mostrada:
- Nombre del terapeuta
- Nombre del paciente
- Fecha de la sesión
- Duración de la sesión
- Observaciones registradas
Acciones disponibles:
- Botón “Aprobar”: Cambia el estado del reporte a aprobado
→ Lleva al Mockup 4 (estado actualizado)
- Botón “Rechazar”:
Abre el Mockup 3 (modal de rechazo)
- Botón “Volver”:
Regresa al Mockup 1
## Mockup 3 — Modal de rechazo de reporte

Ventana emergente donde el supervisor debe ingresar comentarios para rechazar el reporte.

Acciones disponibles:
- Campo “Comentarios del rechazo” (obligatorio):
El usuario debe escribir el motivo del rechazo
- Botón “Confirmar rechazo”:
Valida que el campo no esté vacío
Cambia el estado a rechazado
→ Lleva al Mockup 4
- Botón “Cancelar”:
Cierra el modal
Regresa al Mockup 2
## Mockup 4 — Estado del reporte actualizado

Pantalla donde se refleja el resultado de la acción realizada por el supervisor.

Escenarios:
- Reporte aprobado
- Estado: Aprobado
→ Se muestra mensaje de confirmación
- Reporte rechazado
- Estado: Rechazado
→ Se muestran los comentarios ingresados