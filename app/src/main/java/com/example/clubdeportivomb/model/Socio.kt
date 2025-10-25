package com.example.clubdeportivomb.model

data class Socio(
    val id: Long,
    val personaId: Long,
    val fechaAlta: String,
    val estado: Int,
    val certificado: String?
)
