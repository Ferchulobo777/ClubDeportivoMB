package com.example.clubdeportivomb

// Personas generales (base para socios, profesores, etc.)
data class Persona(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val fechaNacimiento: String?,
    val telefono: String?,
    val direccion: String?
)

// Usuarios del sistema (rol administrativo)
data class Usuario(
    val id: Long,
    val username: String,
    val passwordHash: String,
    val rol: String,
    val personaId: Long?
)

// Socios
data class Socio(
    val id: Long,
    val personaId: Long,
    val fechaAlta: String,
    val estado: Int,
    val certificado: String
)

// No socios
data class NoSocio(
    val id: Long,
    val personaId: Long,
    val certificado: String,
    val fechaAlta: String
)

// Profesores
data class Profesor(
    val id: Long,
    val personaId: Long,
    val especialidad: String?,
    val fechaAlta: String,
    val sueldo: Double?
)

// Nutricionistas
data class Nutricionista(
    val id: Long,
    val personaId: Long,
    val fechaAlta: String,
    val matricula: String
)

// Actividades
data class Actividad(
    val id: Long,
    val nombre: String,
    val descripcion: String?,
    val cupoMaximo: Int,
    val dia: String,
    val hora: String,
    val salon: String,
    val profesorId: Long?
)

// Participantes de actividades (socios o no socios)
data class ActividadParticipante(
    val id: Long,
    val actividadId: Long,
    val personaId: Long,
    val tipoAfiliado: String,
    val fechaInscripcion: String
)

// Asistencias de socios
data class AsistenciaSocio(
    val id: Long,
    val personaId: Long,
    val profesorId: Long,
    val fecha: String,
    val tipo: String,
    val horaEntrada: String,
    val horaSalida: String?
)

// Asistencias de personal
data class AsistenciaPersonal(
    val id: Long,
    val personaId: Long,
    val tipo: String,
    val clase: String,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String?
)

// Empleados administrativos
data class EmpleadoAdministrativo(
    val id: Long,
    val personaId: Long,
    val fechaAlta: String,
    val puesto: String?,
    val sueldo: Double?
)

// Sueldos
data class Sueldo(
    val id: Long,
    val personaId: Long,
    val mes: Int,
    val anio: Int,
    val horasTrabajadas: Double?,
    val monto: Double?
)

// Cuotas para socios
data class Cuota(
    val id: Long,
    val socioId: Long,
    val fechaVencimiento: String,
    val fechaPago: String?,
    val monto: Double?,
    val estado: String
)

// Turnos de nutricionista (solo socios)
data class TurnoNutricionista(
    val id: Long,
    val socioId: Long,
    val nutricionistaId: Long,
    val fechaTurno: String,
    val comprobanteEmitido: Boolean
)

// Pagos
data class Pago(
    val id: Long,
    val dniCliente: String?,
    val tipoPago: String?,
    val importe: Double?,
    val motivo: String?,
    val medioPago: String?,
    val cuotas: String?,
    val fechaPago: String?
)
