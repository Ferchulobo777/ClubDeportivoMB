package com.example.clubdeportivomb.model

data class Pago(
    val id: Long,
    val dniCliente: String,
    val tipoPago: String,
    val importe: Double,
    val motivo: String,
    val medioPago: String,
    val cuotas: String?,
    val fechaPago: String
)
