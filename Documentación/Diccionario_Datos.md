# Diccionario de datos

### Usuario
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| idUsuario     | Long          | Número único para identificar al individuo| Único. 19 dígitos máximos y Debe existir        |
| nombreCompleto| String        | Primer nombre, segundo nombre (si tiene), apellido paterno y apellido materno del usuario    | Solo letras, un máximo de 100 letras y Debe existir    |

### Paciente
Hereda de: Usuario
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| edad       | int    | Edad del paciente en el momento | edad no menor a 1 ni mayor a 120 y Debe existir  |
| fechaNacimiento       | Date     | Fecha de nacimiento del paciente| fecha no menor a 1910 ni mayor a la fecha actual |
| correoElectronico     | String     | Correo electrónico del paciente o tutor | Único. Máximo de 100 carácteres y Formato correo válido (usuario@dominio.com) |
| numeroTelefonico      | String     | Número telefónico del paciente o tutor  |Único. Solo dígitos, 20 máximos y Debe existir|

### Terapeuta
Hereda de: Usuario
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| idPacienteAsignado | Long | Número único para la identificación del paciente asignado al terapeuta respectivo |  Único. 19 Dígitos máximos y Debe existir   |

### Supervisor
* Sin atributos propios
* Hereda de: Usuario
* Atributos heredados: 
    + id
    + nombreCompleto

### Administrador
* Sin atributos propios
* Hereda de: Usuario
* Atributos heredados: 
    + id
    + nombreCompleto


### Documento
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| idDocumento   | Long          | Número único de identificación por documento| Único. 19 dígitos máximos y Debe existir |
| fecha         | Date          | Fecha para identificar última modificación  | Fecha no mayor a la fecha actual, tampoco menor a la fecha de creación del documento y Debe existir|

### Expediente
Hereda de: Usuario 

Hereda de: Documento

Hereda de: Paciente


| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| terapeutaAsociado     | String     | Nombre del terapeuta encargado del paciente | No mayor a 100 carácteres y Debe existir |
| fechaProxCita      | DateTime     | Fecha de la próxima cita entre paciente y terapeuta | Fecha no menor a la fecha actual |

### ReporteSesion
Hereda de: Documento
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| duracionSesion      | Int | Tiempo total que duró la sesión en minutos | Tiempo mayor a cero |
| observacionesClinicas      | String | Notas clínicas correspondientes al paciente por parte del profesional encargado | Debe existir |
| estado      | String | Estado psico-emocional actual del paciente | Debe existir |
| comentarios      | String | Notas del profesional encargado que notó durante la sesión sobre el paciente  y considero relevante | Debe existir |
| idTerapeuta      | Long | Número de identificación del terapeuta encargado | Único. Consta de 19 dígitos máximos y Debe existir |

### InformeConsentimiento
Hereda de: Documento
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| cuerpoDelTexto | String | Texto legal completo que le indica al paciente los acuerdos que deberpa aceptar para recibir tratamiento | Debe existir |
| acuerdoConfidencial | String | Texto que contiene el deber ético y la obligación legal que tiene todo el personal involucrado de proteger la información privada del paciente | Debe existir |

### EntrevistaSocioecnomica
Hereda de: Documento
| Nombre        | Tipo de dato  | Descripción   | Restricciones |
| ------------- |:-------------:| ------------- |:-------------:|
| ingresoFamiliar | Decimal | Que refiere a la suma de ingresos de cada integrante de la familia de acuerdo a cuantos son | Valor no menor a 0 y Debe existir |
| alimentacion | Decimal | Parte del ingreso familiar destinado a la comida y es tomado como un egreso económico | Valor no menor a 0 y Debe existir |
| lugarProcedencia | String | Indica el área geográfica de residencia del paciente; considerado como egreso | Máximo 100 carácteres y Debe existir |
| vivienda | String | Lugar físico donde habita el paciente y su familia, se tomas en cuenta las características físicas | Máximo 1000 carácteres |
| estadoSaludFamiliar | String | Número de enfermos crónicos en la familia al momento de la evaluación | Valor no mayor a 50 y Debe existir |