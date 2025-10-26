package com.example.clubdeportivomb.model

data class Usuario(
    val id: Long,
    val username: String,
    val passwordHash: String,
    val rol: String,
    val personaId: Long
)
