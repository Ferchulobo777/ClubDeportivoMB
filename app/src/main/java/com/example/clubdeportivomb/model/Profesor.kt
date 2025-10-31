package com.example.clubdeportivomb.model

data class Profesor(
    override val id: Long,
    override val nombre: String,
    override val apellido: String,
    override val dni: String,
    override val fechaNacimiento: String,
    override val telefono: String,
    override val direccion: String,
    override val email: String,
    override val fechaAlta: String,
    val especialidad: String?,
    val sueldo: Double
) : Persona(id, nombre, apellido, dni, fechaNacimiento, telefono, direccion, email, fechaAlta)