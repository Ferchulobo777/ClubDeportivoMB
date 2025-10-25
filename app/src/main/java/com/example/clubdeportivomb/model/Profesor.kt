package com.example.clubdeportivomb.model

data class Profesor(
    val id: Long,
    val personaId: Long,
    val especialidad: String?,
    val fechaAlta: String,
    val sueldo: Double
)

