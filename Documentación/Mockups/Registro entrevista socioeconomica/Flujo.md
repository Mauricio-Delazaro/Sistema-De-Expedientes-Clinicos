## Mockup 1 — Lista de entrevistas socioeconómicas

Descripción:
Pantalla principal donde el terapeuta visualiza todas las entrevistas registradas.

Acciones disponibles:

- Buscar entrevista por nombre o ID de paciente
- Nueva entrevista
→ Lleva al Mockup 2
- Ver detalle
→ Lleva al Mockup 5
- Editar entrevista
→ Lleva al Mockup 2 (con datos precargados)
---
## Mockup 2 — Formulario de nueva entrevista socioeconómica
Descripción:
Pantalla donde se registra la información socioeconómica del paciente.

Secciones del formulario:

- Ingreso familiar
- Alimentación
- Lugar de procedencia
- Vivienda
- Estado de salud familiar
- Observaciones adicionales

Acciones disponibles:

- Guardar entrevista
(Valida campos obligatorios)
- Si todo está correcto:
→ Lleva al Mockup 4 (confirmación)
- Si hay errores:
→ Lleva al Mockup 3 (validaciones)
- Cancelar
→ Regresa al Mockup 1 sin guardar cambios

## Mockup 3 — Validaciones de campos obligatorios

**Descripción:**
Se muestra cuando faltan campos obligatorios en el formulario.

**Comportamiento:**

Campos incompletos se marcan en rojo
Mensajes como:
“Este campo es obligatorio”

**Acciones disponibles:**

- Corregir información
Permite completar los campos faltantes

Luego:
→ Regresa al flujo de guardado 

## Mockup 4 — Confirmación de guardado

**Descripción:**
Se muestra cuando la entrevista se guarda correctamente.

**Mensaje:** “Entrevista guardada correctamente”

**Acciones disponibles:**

- Aceptar
→ Regresa al Mockup 1 

La nueva entrevista aparece en la lista

## Mockup 5 — Visualización en expediente clínico

**Descripción:**
Muestra la entrevista ya guardada dentro del expediente del paciente.

**Contenido:**

Todas las secciones capturadas:
Ingreso familiar
Alimentación
Vivienda
etc.

**Acciones disponibles:**

- Editar entrevista
→ Lleva al Mockup 2 con datos cargados
- Volver
→ Regresa al Mockup 1