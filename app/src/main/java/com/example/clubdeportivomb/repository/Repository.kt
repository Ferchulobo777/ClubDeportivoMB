package com.example.clubdeportivomb.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.clubdeportivomb.db.ClubDeportivoDBHelper
import com.example.clubdeportivomb.mapper.CursorMapper
import com.example.clubdeportivomb.model.Usuario

class ClubDeportivoRepository(context: Context) {

    private val dbHelper = ClubDeportivoDBHelper(context)

    // ----------------------------
    // PERSONAS
    // ----------------------------
    fun insertarPersona(nombre: String, apellido: String, dni: String, fechaNacimiento: String?, telefono: String?, direccion: String?, email: String?, fechaAlta: String?): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
            put("dni", dni)
            put("fecha_nacimiento", fechaNacimiento)
            put("telefono", telefono)
            put("direccion", direccion)
            put("email", email)
            put("fecha_alta", fechaAlta)
        }
        return db.insert("personas", null, values)
    }

    fun obtenerPersonaPorId(id: Long): Cursor {
        val db = dbHelper.readableDatabase
        return db.query("personas", null, "id = ?", arrayOf(id.toString()), null, null, null)
    }

    fun actualizarPersona(id: Long, nombre: String, apellido: String, telefono: String?, direccion: String?, email: String?): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
            put("telefono", telefono)
            put("direccion", direccion)
            put("email", email)
        }
        return db.update("personas", values, "id = ?", arrayOf(id.toString()))
    }

    fun eliminarPersona(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete("personas", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // USUARIOS
    // ----------------------------
    fun insertarUsuario(username: String, passwordHash: String, rol: String, personaId: Long?): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password_hash", passwordHash)
            put("rol", rol)
            put("persona_id", personaId)
        }
        return db.insert("usuarios", null, values)
    }

    fun obtenerUsuarios(): Cursor {
        return dbHelper.readableDatabase.query("usuarios", null, null, null, null, null, null)
    }

    fun obtenerUsuario(username: String, passwordHash: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "usuarios",
            arrayOf("id", "username", "rol", "persona_id"),
            "username = ? AND password_hash = ?",
            arrayOf(username, passwordHash),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"))
            val personaId = cursor.getLong(cursor.getColumnIndexOrThrow("persona_id"))

            // Obtener el nombre de la persona
            var nombre = ""
            val personaCursor = db.query(
                "personas",
                arrayOf("nombre"),
                "id = ?",
                arrayOf(personaId.toString()),
                null,
                null,
                null
            )
            if (personaCursor.moveToFirst()) {
                nombre = personaCursor.getString(personaCursor.getColumnIndexOrThrow("nombre"))
            }
            personaCursor.close()
            cursor.close()

            return Usuario(id, username, passwordHash, rol, personaId )
        } else {
            cursor.close()
            return null
        }
    }

    fun actualizarUsuario(id: Long, username: String, rol: String): Int {
        val values = ContentValues().apply {
            put("username", username)
            put("rol", rol)
        }
        return dbHelper.writableDatabase.update("usuarios", values, "id = ?", arrayOf(id.toString()))
    }

    fun eliminarUsuario(id: Long): Int {
        return dbHelper.writableDatabase.delete("usuarios", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // SOCIOS
    // ----------------------------
    fun insertarSocio(personaId: Long, fechaAlta: String, certificado: String, estado: Int = 1): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("fecha_alta", fechaAlta)
            put("certificado", certificado)
            put("estado", estado)
        }
        return db.insert("socios", null, values)
    }

    fun obtenerTodosLosSocios(): Cursor {
        return dbHelper.readableDatabase.query("socios", null, null, null, null, null, null)
    }

    fun actualizarSocio(id: Long, estado: Int): Int {
        val values = ContentValues().apply { put("estado", estado) }
        return dbHelper.writableDatabase.update("socios", values, "id = ?", arrayOf(id.toString()))
    }

    fun eliminarSocio(id: Long): Int {
        return dbHelper.writableDatabase.delete("socios", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // NO_SOCIOS
    // ----------------------------
    fun insertarNoSocio(personaId: Long, certificado: String, fechaAlta: String): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("certificado", certificado)
            put("fecha_alta", fechaAlta)
        }
        return dbHelper.writableDatabase.insert("no_socios", null, values)
    }

    fun obtenerNoSocios(): Cursor {
        return dbHelper.readableDatabase.query("no_socios", null, null, null, null, null, null)
    }

    fun eliminarNoSocio(id: Long): Int {
        return dbHelper.writableDatabase.delete("no_socios", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // PROFESORES
    // ----------------------------
    fun insertarProfesor(personaId: Long, especialidad: String?, fechaAlta: String, sueldo: Double?): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("especialidad", especialidad)
            put("fecha_alta", fechaAlta)
            put("sueldo", sueldo)
        }
        return dbHelper.writableDatabase.insert("profesores", null, values)
    }

    fun obtenerProfesores(): Cursor {
        return dbHelper.readableDatabase.query("profesores", null, null, null, null, null, null)
    }

    fun eliminarProfesor(id: Long): Int {
        return dbHelper.writableDatabase.delete("profesores", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // NUTRICIONISTAS
    // ----------------------------
    fun insertarNutricionista(personaId: Long, fechaAlta: String, matricula: String): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("fecha_alta", fechaAlta)
            put("matricula", matricula)
        }
        return dbHelper.writableDatabase.insert("nutricionistas", null, values)
    }

    fun obtenerNutricionistas(): Cursor {
        return dbHelper.readableDatabase.query("nutricionistas", null, null, null, null, null, null)
    }

    fun eliminarNutricionista(id: Long): Int {
        return dbHelper.writableDatabase.delete("nutricionistas", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // ACTIVIDADES
    // ----------------------------
    fun insertarActividad(nombre: String, descripcion: String?, cupoMaximo: Int, dia: String, hora: String, salon: String, profesorId: Long?): Long {
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("cupo_maximo", cupoMaximo)
            put("dia", dia)
            put("hora", hora)
            put("salon", salon)
            put("profesor_id", profesorId)
        }
        return dbHelper.writableDatabase.insert("actividades", null, values)
    }

    fun obtenerActividades(): Cursor {
        return dbHelper.readableDatabase.query("actividades", null, null, null, null, null, "dia, hora")
    }

    fun eliminarActividad(id: Long): Int {
        return dbHelper.writableDatabase.delete("actividades", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // ACTIVIDAD PARTICIPANTES
    // ----------------------------
    fun insertarActividadParticipante(actividadId: Long, personaId: Long, tipoAfiliado: String, fechaInscripcion: String): Long {
        val values = ContentValues().apply {
            put("actividad_id", actividadId)
            put("persona_id", personaId)
            put("tipo_afiliado", tipoAfiliado)
            put("fecha_inscripcion", fechaInscripcion)
        }
        return dbHelper.writableDatabase.insert("actividad_participantes", null, values)
    }

    fun obtenerParticipantesActividad(actividadId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "actividad_participantes",
            null,
            "actividad_id = ?",
            arrayOf(actividadId.toString()),
            null,
            null,
            null
        )
    }

    fun eliminarParticipanteActividad(id: Long): Int {
        return dbHelper.writableDatabase.delete("actividad_participantes", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // ASISTENCIAS SOCIOS
    // ----------------------------
    fun insertarAsistenciaSocio(personaId: Long, profesorId: Long, fecha: String, tipo: String, horaEntrada: String, horaSalida: String?): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("profesor_id", profesorId)
            put("fecha", fecha)
            put("tipo", tipo)
            put("hora_entrada", horaEntrada)
            put("hora_salida", horaSalida)
        }
        return dbHelper.writableDatabase.insert("asistencias_socios", null, values)
    }

    fun obtenerAsistenciasSocio(personaId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "asistencias_socios",
            null,
            "persona_id = ?",
            arrayOf(personaId.toString()),
            null,
            null,
            "fecha DESC"
        )
    }

    // ----------------------------
    // ASISTENCIAS PERSONAL
    // ----------------------------
    fun insertarAsistenciaPersonal(personaId: Long, tipo: String, clase: String, fecha: String, horaEntrada: String, horaSalida: String?): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("tipo", tipo)
            put("clase", clase)
            put("fecha", fecha)
            put("hora_entrada", horaEntrada)
            put("hora_salida", horaSalida)
        }
        return dbHelper.writableDatabase.insert("asistencias_personal", null, values)
    }

    fun obtenerAsistenciasPersonal(personaId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "asistencias_personal",
            null,
            "persona_id = ?",
            arrayOf(personaId.toString()),
            null,
            null,
            "fecha DESC"
        )
    }

    // ----------------------------
    // EMPLEADOS ADMINISTRATIVOS
    // ----------------------------
    fun insertarEmpleadoAdministrativo(personaId: Long, fechaAlta: String, puesto: String?, sueldo: Double?): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("fecha_alta", fechaAlta)
            put("puesto", puesto)
            put("sueldo", sueldo)
        }
        return dbHelper.writableDatabase.insert("empleados_administrativos", null, values)
    }

    fun obtenerEmpleadosAdministrativos(): Cursor {
        return dbHelper.readableDatabase.query("empleados_administrativos", null, null, null, null, null, null)
    }

    fun eliminarEmpleadoAdministrativo(id: Long): Int {
        return dbHelper.writableDatabase.delete("empleados_administrativos", "id = ?", arrayOf(id.toString()))
    }

    // ----------------------------
    // SUELDOS
    // ----------------------------
    fun insertarSueldo(personaId: Long, mes: Int, anio: Int, horasTrabajadas: Double?, monto: Double?): Long {
        val values = ContentValues().apply {
            put("persona_id", personaId)
            put("mes", mes)
            put("anio", anio)
            put("horas_trabajadas", horasTrabajadas)
            put("monto", monto)
        }
        return dbHelper.writableDatabase.insert("sueldos", null, values)
    }

    fun obtenerSueldos(personaId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "sueldos",
            null,
            "persona_id = ?",
            arrayOf(personaId.toString()),
            null,
            null,
            "anio DESC, mes DESC"
        )
    }

    // ----------------------------
    // CUOTAS
    // ----------------------------
    fun insertarCuota(socioId: Long, fechaVencimiento: String, fechaPago: String?, monto: Double?, estado: String = "IMPAGA"): Long {
        val values = ContentValues().apply {
            put("socio_id", socioId)
            put("fecha_vencimiento", fechaVencimiento)
            put("fecha_pago", fechaPago)
            put("monto", monto)
            put("estado", estado)
        }
        return dbHelper.writableDatabase.insert("cuotas", null, values)
    }

    fun obtenerCuotas(socioId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "cuotas",
            null,
            "socio_id = ?",
            arrayOf(socioId.toString()),
            null,
            null,
            "fecha_vencimiento DESC"
        )
    }

    // ----------------------------
    // TURNOS NUTRICIONISTA
    // ----------------------------
    fun insertarTurnoNutricionista(socioId: Long, nutricionistaId: Long, fechaTurno: String, comprobanteEmitido: Int = 0): Long {
        val values = ContentValues().apply {
            put("socio_id", socioId)
            put("nutricionista_id", nutricionistaId)
            put("fecha_turno", fechaTurno)
            put("comprobante_emitido", comprobanteEmitido)
        }
        return dbHelper.writableDatabase.insert("turnos_nutricionista", null, values)
    }

    fun obtenerTurnosNutricionista(nutricionistaId: Long): Cursor {
        return dbHelper.readableDatabase.query(
            "turnos_nutricionista",
            null,
            "nutricionista_id = ?",
            arrayOf(nutricionistaId.toString()),
            null,
            null,
            "fecha_turno DESC"
        )
    }

    // ----------------------------
    // PAGOS
    // ----------------------------
    fun insertarPago(dniCliente: String?, tipoPago: String?, importe: Double?, motivo: String?, medioPago: String?, cuotas: String?, fechaPago: String?): Long {
        val values = ContentValues().apply {
            put("dni_cliente", dniCliente)
            put("tipo_pago", tipoPago)
            put("importe", importe)
            put("motivo", motivo)
            put("medio_pago", medioPago)
            put("cuotas", cuotas)
            put("fecha_pago", fechaPago)
        }
        return dbHelper.writableDatabase.insert("pagos", null, values)
    }

    fun obtenerPagos(): Cursor {
        return dbHelper.readableDatabase.query("pagos", null, null, null, null, null, "fecha_pago DESC")
    }
}
