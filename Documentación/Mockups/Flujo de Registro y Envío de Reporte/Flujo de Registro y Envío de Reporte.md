# Flujo de navegación - Registro y Envío de Reportes Clínicos (Terapeuta)

## Mockup 1 — Formulario principal de reporte clínico

Pantalla principal donde el terapeuta registra la información clínica del paciente y la sesión realizada.

### Secciones visibles

- Información del paciente
  - Nombre completo
  - Número de expediente
  - Edad
  - Diagnóstico principal

- Información de la sesión
  - Fecha
  - Hora
  - Duración
  - Tipo de sesión

- Observaciones clínicas
  - Evolución del paciente
  - Conductas observadas
  - Síntomas relevantes

- Plan terapéutico
  - Recomendaciones
  - Medicación indicada
  - Próxima sesión

### Validaciones visuales

- Campos obligatorios marcados con (*)
- Mensajes de error debajo de campos incompletos
- Indicadores de validación correcta
- Barra de progreso del formulario

### Estados visibles

- Estado “BORRADOR”
- Fecha de última modificación

### Acciones disponibles

- Botón “Guardar borrador”
  - Lleva al Mockup 3

- Botón “Vista previa”
  - Lleva al Mockup 4

- Botón “Enviar reporte”
  - Lleva al Mockup 5

---

## Mockup 2 — Estado de validación incompleta

Representa el formulario cuando faltan datos obligatorios o existen errores de validación.

### Indicaciones visuales

- Campos vacíos resaltados en rojo
- Mensaje:
  - “Completa los campos obligatorios antes de continuar”
- Lista de errores detectados
- Barra de progreso incompleta

### Flujo

- Una vez corregidos los errores:
  - Regresa al Mockup 1

---

## Mockup 3 — Estado guardado como borrador

Representa el reporte almacenado temporalmente antes del envío definitivo.

### Estados visibles

- Etiqueta “BORRADOR”
- Fecha y hora de guardado
- Información editable

### Acciones disponibles

- Botón “Continuar edición”
  - Regresa al Mockup 1

- Botón “Vista previa”
  - Lleva al Mockup 4

---

## Mockup 4 — Vista previa del reporte clínico

Pantalla donde el terapeuta revisa toda la información antes de enviarla.

### Información mostrada

- Datos del paciente
- Información de la sesión
- Observaciones clínicas
- Plan terapéutico
- Nombre del terapeuta
- Fecha de creación
- Estado actual del reporte

### Acciones disponibles

- Botón “Editar reporte”
  - Regresa al Mockup 1

- Botón “Descargar PDF”
  - Permite descargar el reporte

- Botón “Confirmar envío”
  - Lleva al Mockup 5

---

## Mockup 5 — Confirmación de envío

Pantalla final donde el sistema confirma que el reporte clínico fue enviado correctamente.

### Estados visibles

- Estado “ENVIADO”
- Confirmación visual de éxito
- Fecha y hora de envío
- Número de reporte generado

### Acciones disponibles

- Botón “Volver al expediente”
  - Regresa al expediente clínico del paciente

- Botón “Crear nuevo reporte”
  - Lleva nuevamente al Mockup 1
