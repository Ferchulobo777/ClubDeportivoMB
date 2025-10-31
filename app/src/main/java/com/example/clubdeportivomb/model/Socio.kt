package com.example.clubdeportivomb.model

data class Socio(
    override val id: Long,
    override val nombre: String,
    override val apellido: String,
    override val dni: String,
    override val fechaNacimiento: String,
    override val telefono: String,
    override val direccion: String,
    override val email: String,
    override val fechaAlta: String,
    val estado: Int,
    val certificado: String?  // Obligatorio para Socio
) : Persona(id, nombre, apellido, dni, fechaNacimiento, telefono, direccion, email, fechaAlta)