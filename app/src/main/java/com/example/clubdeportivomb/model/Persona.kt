package com.example.clubdeportivomb.model

abstract class Persona(
    open val id: Long,
    open val nombre: String,
    open val apellido: String,
    open val dni: String,
    open val fechaNacimiento: String,
    open val telefono: String,
    open val direccion: String,
    open val email: String,
    open val fechaAlta: String
)