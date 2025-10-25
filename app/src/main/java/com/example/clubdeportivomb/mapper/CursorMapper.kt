package com.example.clubdeportivomb.mapper

import android.database.Cursor
import com.example.clubdeportivomb.model.*

object CursorMapper {

    fun cursorToPersona(cursor: Cursor): Persona {
        return Persona(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"))
        )
    }

    fun cursorToSocio(cursor: Cursor): Socio {
        return Socio(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            personaId = cursor.getLong(cursor.getColumnIndexOrThrow("persona_id")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            estado = cursor.getInt(cursor.getColumnIndexOrThrow("estado")),
            certificado = cursor.getString(cursor.getColumnIndexOrThrow("certificado"))
        )
    }

    fun cursorToUsuario(cursor: Cursor): Usuario {
        return Usuario(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
            passwordHash = cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
            rol = cursor.getString(cursor.getColumnIndexOrThrow("rol")),
            personaId = cursor.getLong(cursor.getColumnIndexOrThrow("persona_id"))
        )
    }

    fun cursorToActividad(cursor: Cursor): Actividad {
        return Actividad(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
            cupoMaximo = cursor.getInt(cursor.getColumnIndexOrThrow("cupo_maximo")),
            dia = cursor.getString(cursor.getColumnIndexOrThrow("dia")),
            hora = cursor.getString(cursor.getColumnIndexOrThrow("hora")),
            salon = cursor.getString(cursor.getColumnIndexOrThrow("salon")),
            profesorId = cursor.getLong(cursor.getColumnIndexOrThrow("profesor_id"))
        )
    }

    fun cursorToPago(cursor: Cursor): Pago {
        return Pago(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            dniCliente = cursor.getString(cursor.getColumnIndexOrThrow("dni_cliente")),
            tipoPago = cursor.getString(cursor.getColumnIndexOrThrow("tipo_pago")),
            importe = cursor.getDouble(cursor.getColumnIndexOrThrow("importe")),
            motivo = cursor.getString(cursor.getColumnIndexOrThrow("motivo")),
            medioPago = cursor.getString(cursor.getColumnIndexOrThrow("medio_pago")),
            cuotas = cursor.getString(cursor.getColumnIndexOrThrow("cuotas")),
            fechaPago = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))
        )
    }

    // 🔁 Ejemplo para convertir todos los registros de un cursor en una lista
    fun cursorToList(cursor: Cursor, mapper: (Cursor) -> Any): List<Any> {
        val lista = mutableListOf<Any>()
        if (cursor.moveToFirst()) {
            do {
                lista.add(mapper(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}


