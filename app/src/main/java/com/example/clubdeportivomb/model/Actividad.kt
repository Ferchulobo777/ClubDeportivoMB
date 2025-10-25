package com.example.clubdeportivomb.model

data class Actividad(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val cupoMaximo: Int,
    val dia: String,
    val hora: String,
    val salon: String,
    val profesorId: Long
)
