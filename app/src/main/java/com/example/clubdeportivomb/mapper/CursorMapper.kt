package com.example.clubdeportivomb.mapper

import android.database.Cursor
import com.example.clubdeportivomb.model.*

object CursorMapper {

    // Para Persona base
    fun cursorToPersona(cursor: Cursor): Persona {
        return object : Persona(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta"))
        ) {}
    }

    // Para Usuario (administración)
    fun cursorToUsuario(cursor: Cursor): Usuario {
        return Usuario(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
            passwordHash = cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
            rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"))
        )
    }

    // Para Socio
    fun cursorToSocio(cursor: Cursor): Socio {
        return Socio(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            estado = cursor.getInt(cursor.getColumnIndexOrThrow("estado")),
            certificado = cursor.getString(cursor.getColumnIndexOrThrow("certificado"))
        )
    }

    // Para NoSocio
    fun cursorToNoSocio(cursor: Cursor): NoSocio {
        return NoSocio(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            certificado = cursor.getString(cursor.getColumnIndexOrThrow("certificado"))
        )
    }

    // Para Nutricionista
    fun cursorToNutricionista(cursor: Cursor): Nutricionista {
        return Nutricionista(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            matricula = cursor.getString(cursor.getColumnIndexOrThrow("matricula"))
        )
    }

    // Para Profesor
    fun cursorToProfesor(cursor: Cursor): Profesor {
        return Profesor(
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta")),
            especialidad = cursor.getString(cursor.getColumnIndexOrThrow("especialidad")),
            sueldo = cursor.getDouble(cursor.getColumnIndexOrThrow("sueldo"))
        )
    }

    // Para Actividad
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

    // Para Pago
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

    // 🔁 Método genérico para convertir cursor a lista
    fun <T> cursorToList(cursor: Cursor, mapper: (Cursor) -> T): List<T> {
        val lista = mutableListOf<T>()
        if (cursor.moveToFirst()) {
            do {
                lista.add(mapper(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}