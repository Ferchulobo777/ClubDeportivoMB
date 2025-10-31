package com.example.clubdeportivomb.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivomb.db.ClubDeportivoDBHelper
import com.example.clubdeportivomb.mapper.CursorMapper
import com.example.clubdeportivomb.model.*

class ClubDeportivoRepository(private val dbHelper: ClubDeportivoDBHelper) {

    private val db: SQLiteDatabase get() = dbHelper.writableDatabase

    // ===== OPERACIONES PERSONAS =====
    fun insertarPersona(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String
    ): Long {
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

    // ===== OPERACIONES SOCIOS =====
    fun insertarSocioCompleto(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String,
        certificado: String // ✅ OBLIGATORIO para socio
    ): Long {
        return try {
            db.beginTransaction()

            // 1. Insertar persona
            val personaId = insertarPersona(
                nombre, apellido, dni, fechaNacimiento,
                telefono, direccion, email, fechaAlta
            )

            if (personaId == -1L) {
                db.endTransaction()
                return -1
            }

            // 2. Insertar socio (CON CERTIFICADO OBLIGATORIO)
            val socioValues = ContentValues().apply {
                put("persona_id", personaId)
                put("fecha_alta", fechaAlta)
                put("estado", 1) // Activo por defecto
                put("certificado", certificado) // ✅ REQUERIDO
            }

            val socioId = db.insert("socios", null, socioValues)
            db.setTransactionSuccessful()
            socioId
        } catch (e: Exception) {
            -1
        } finally {
            db.endTransaction()
        }
    }

    fun obtenerSocioPorId(id: Long): Socio? {
        val query = """
            SELECT p.*, s.estado, s.certificado 
            FROM personas p 
            INNER JOIN socios s ON p.id = s.persona_id 
            WHERE s.id = ?
        """.trimIndent()

        return db.rawQuery(query, arrayOf(id.toString())).use { cursor ->
            if (cursor.moveToFirst()) CursorMapper.cursorToSocio(cursor) else null
        }
    }

    fun existeSocioConDNI(dni: String): Boolean {
        val query = """
            SELECT COUNT(*) FROM personas p 
            INNER JOIN socios s ON p.id = s.persona_id 
            WHERE p.dni = ?
        """.trimIndent()

        return db.rawQuery(query, arrayOf(dni)).use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0) > 0
        }
    }

    fun obtenerTodosLosSocios(): List<Socio> {
        val query = """
            SELECT p.*, s.estado, s.certificado 
            FROM personas p 
            INNER JOIN socios s ON p.id = s.persona_id 
            ORDER BY p.apellido, p.nombre
        """.trimIndent()

        return db.rawQuery(query, null).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToSocio)
        }
    }

    // ===== OPERACIONES NO SOCIOS =====
    fun insertarNoSocioCompleto(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String,
        certificado: String? // Opcional para no socio
    ): Long {
        return try {
            db.beginTransaction()

            val personaId = insertarPersona(
                nombre, apellido, dni, fechaNacimiento,
                telefono, direccion, email, fechaAlta
            )

            if (personaId == -1L) {
                db.endTransaction()
                return -1
            }

            val noSocioValues = ContentValues().apply {
                put("persona_id", personaId)
                put("certificado", certificado ?: "") // Puede ser vacío
                put("fecha_alta", fechaAlta)
            }

            val noSocioId = db.insert("no_socios", null, noSocioValues)
            db.setTransactionSuccessful()
            noSocioId
        } catch (e: Exception) {
            -1
        } finally {
            db.endTransaction()
        }
    }

    // ===== OPERACIONES USUARIOS =====
    fun insertarUsuarioCompleto(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String,
        username: String,
        passwordHash: String,
        rol: String
    ): Long {
        return try {
            db.beginTransaction()

            val personaId = insertarPersona(
                nombre, apellido, dni, fechaNacimiento,
                telefono, direccion, email, fechaAlta
            )

            if (personaId == -1L) {
                db.endTransaction()
                return -1
            }

            val usuarioValues = ContentValues().apply {
                put("persona_id", personaId)
                put("username", username)
                put("password_hash", passwordHash)
                put("rol", rol)
            }

            val usuarioId = db.insert("usuarios", null, usuarioValues)
            db.setTransactionSuccessful()
            usuarioId
        } catch (e: Exception) {
            -1
        } finally {
            db.endTransaction()
        }
    }

    fun obtenerUsuarioPorUsername(username: String): Usuario? {
        val query = """
            SELECT p.*, u.username, u.password_hash, u.rol 
            FROM personas p 
            INNER JOIN usuarios u ON p.id = u.persona_id 
            WHERE u.username = ?
        """.trimIndent()

        return db.rawQuery(query, arrayOf(username)).use { cursor ->
            if (cursor.moveToFirst()) CursorMapper.cursorToUsuario(cursor) else null
        }
    }

    // ===== OPERACIONES PROFESORES =====
    fun insertarProfesorCompleto(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String,
        especialidad: String?,
        sueldo: Double
    ): Long {
        return try {
            db.beginTransaction()

            val personaId = insertarPersona(
                nombre, apellido, dni, fechaNacimiento,
                telefono, direccion, email, fechaAlta
            )

            if (personaId == -1L) {
                db.endTransaction()
                return -1
            }

            val profesorValues = ContentValues().apply {
                put("persona_id", personaId)
                put("especialidad", especialidad)
                put("fecha_alta", fechaAlta)
                put("sueldo", sueldo)
            }

            val profesorId = db.insert("profesores", null, profesorValues)
            db.setTransactionSuccessful()
            profesorId
        } catch (e: Exception) {
            -1
        } finally {
            db.endTransaction()
        }
    }

    fun obtenerTodosLosProfesores(): List<Profesor> {
        val query = """
            SELECT p.*, pr.especialidad, pr.sueldo 
            FROM personas p 
            INNER JOIN profesores pr ON p.id = pr.persona_id 
            ORDER BY p.apellido, p.nombre
        """.trimIndent()

        return db.rawQuery(query, null).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToProfesor)
        }
    }

    // ===== OPERACIONES NUTRICIONISTAS =====
    fun insertarNutricionistaCompleto(
        nombre: String,
        apellido: String,
        dni: String,
        fechaNacimiento: String,
        telefono: String,
        direccion: String,
        email: String,
        fechaAlta: String,
        matricula: String
    ): Long {
        return try {
            db.beginTransaction()

            val personaId = insertarPersona(
                nombre, apellido, dni, fechaNacimiento,
                telefono, direccion, email, fechaAlta
            )

            if (personaId == -1L) {
                db.endTransaction()
                return -1
            }

            val nutricionistaValues = ContentValues().apply {
                put("persona_id", personaId)
                put("fecha_alta", fechaAlta)
                put("matricula", matricula)
            }

            val nutricionistaId = db.insert("nutricionistas", null, nutricionistaValues)
            db.setTransactionSuccessful()
            nutricionistaId
        } catch (e: Exception) {
            -1
        } finally {
            db.endTransaction()
        }
    }

    // ===== OPERACIONES ACTIVIDADES =====
    fun insertarActividad(
        nombre: String,
        descripcion: String,
        cupoMaximo: Int,
        dia: String,
        hora: String,
        salon: String,
        profesorId: Long
    ): Long {
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("cupo_maximo", cupoMaximo)
            put("dia", dia)
            put("hora", hora)
            put("salon", salon)
            put("profesor_id", profesorId)
        }
        return db.insert("actividades", null, values)
    }

    fun obtenerTodasLasActividades(): List<Actividad> {
        val query = "SELECT * FROM actividades ORDER BY dia, hora"
        return db.rawQuery(query, null).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToActividad)
        }
    }

    // ===== OPERACIONES PAGOS =====
    fun insertarPago(
        dniCliente: String,
        tipoPago: String,
        importe: Double,
        motivo: String,
        medioPago: String,
        cuotas: String,
        fechaPago: String
    ): Long {
        val values = ContentValues().apply {
            put("dni_cliente", dniCliente)
            put("tipo_pago", tipoPago)
            put("importe", importe)
            put("motivo", motivo)
            put("medio_pago", medioPago)
            put("cuotas", cuotas)
            put("fecha_pago", fechaPago)
        }
        return db.insert("pagos", null, values)
    }

    fun obtenerTodosLosPagos(): List<Pago> {
        val query = "SELECT * FROM pagos ORDER BY fecha_pago DESC"
        return db.rawQuery(query, null).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToPago)
        }
    }

    // ===== OPERACIONES GENERALES =====
    fun existePersonaConDNI(dni: String): Boolean {
        val query = "SELECT COUNT(*) FROM personas WHERE dni = ?"
        return db.rawQuery(query, arrayOf(dni)).use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0) > 0
        }
    }

    fun obtenerPersonaPorDNI(dni: String): Persona? {
        val query = "SELECT * FROM personas WHERE dni = ?"
        return db.rawQuery(query, arrayOf(dni)).use { cursor ->
            if (cursor.moveToFirst()) CursorMapper.cursorToPersona(cursor) else null
        }
    }

    // ===== VALIDACIÓN ESPECÍFICA PARA SOCIOS =====
    fun puedeRegistrarSocio(dni: String): Boolean {
        // Verifica que no exista ya un socio con ese DNI
        return !existeSocioConDNI(dni)
    }
    // ===== BÚSQUEDA DE CLIENTES MEJORADA =====

    // Buscar socios por nombre, apellido o DNI (SOLO socios)
    fun buscarSocios(textoBusqueda: String): List<Socio> {
        val query = """
        SELECT DISTINCT p.*, s.estado, s.certificado 
        FROM personas p 
        INNER JOIN socios s ON p.id = s.persona_id 
        WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR p.dni LIKE ?
        ORDER BY p.apellido, p.nombre
    """.trimIndent()

        val parametroBusqueda = "%$textoBusqueda%"

        return db.rawQuery(query, arrayOf(parametroBusqueda, parametroBusqueda, parametroBusqueda)).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToSocio)
        }
    }

    // Buscar no socios por nombre, apellido o DNI (SOLO no socios)
    fun buscarNoSocios(textoBusqueda: String): List<NoSocio> {
        val query = """
        SELECT DISTINCT p.*, ns.certificado 
        FROM personas p 
        INNER JOIN no_socios ns ON p.id = ns.persona_id 
        WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR p.dni LIKE ?
        ORDER BY p.apellido, p.nombre
    """.trimIndent()

        val parametroBusqueda = "%$textoBusqueda%"

        return db.rawQuery(query, arrayOf(parametroBusqueda, parametroBusqueda, parametroBusqueda)).use { cursor ->
            CursorMapper.cursorToList(cursor, CursorMapper::cursorToNoSocio)
        }
    }

    // Buscar todos los clientes SIN DUPLICADOS (prioriza socio sobre no socio)
    fun buscarTodosLosClientes(textoBusqueda: String): List<Any> {
        val resultados = mutableListOf<Any>()
        val personasProcesadas = mutableSetOf<String>() // Para evitar duplicados por DNI

        // Primero buscar socios
        val socios = buscarSocios(textoBusqueda)
        socios.forEach { socio ->
            resultados.add(socio)
            personasProcesadas.add(socio.dni) // Marcar DNI como procesado
        }

        // Luego buscar no socios, excluyendo los que ya están como socios
        val noSocios = buscarNoSocios(textoBusqueda)
        noSocios.forEach { noSocio ->
            if (!personasProcesadas.contains(noSocio.dni)) {
                resultados.add(noSocio)
            }
        }

        return resultados
    }

    // Búsqueda más inteligente que verifica duplicados
    fun buscarClientesInteligente(textoBusqueda: String): List<Any> {
        val query = """
        -- Buscar socios
        SELECT p.*, s.estado, s.certificado, 'SOCIO' as tipo
        FROM personas p 
        INNER JOIN socios s ON p.id = s.persona_id 
        WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR p.dni LIKE ?
        
        UNION
        
        -- Buscar no socios que NO son socios
        SELECT p.*, ns.certificado, 1 as estado, 'NO_SOCIO' as tipo
        FROM personas p 
        INNER JOIN no_socios ns ON p.id = ns.persona_id 
        WHERE (p.nombre LIKE ? OR p.apellido LIKE ? OR p.dni LIKE ?)
        AND p.id NOT IN (SELECT persona_id FROM socios)
        
        ORDER BY apellido, nombre
    """.trimIndent()

        val parametroBusqueda = "%$textoBusqueda%"

        return db.rawQuery(query, arrayOf(
            parametroBusqueda, parametroBusqueda, parametroBusqueda,
            parametroBusqueda, parametroBusqueda, parametroBusqueda
        )).use { cursor ->
            val resultados = mutableListOf<Any>()
            if (cursor.moveToFirst()) {
                do {
                    val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                    when (tipo) {
                        "SOCIO" -> resultados.add(CursorMapper.cursorToSocio(cursor))
                        "NO_SOCIO" -> resultados.add(CursorMapper.cursorToNoSocio(cursor))
                    }
                } while (cursor.moveToNext())
            }
            resultados
        }
    }

    // Verificar si una persona es socio
    fun esSocio(dni: String): Boolean {
        val query = """
        SELECT COUNT(*) FROM personas p 
        INNER JOIN socios s ON p.id = s.persona_id 
        WHERE p.dni = ?
    """.trimIndent()

        return db.rawQuery(query, arrayOf(dni)).use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0) > 0
        }
    }

    // Verificar si una persona es no socio
    fun esNoSocio(dni: String): Boolean {
        val query = """
        SELECT COUNT(*) FROM personas p 
        INNER JOIN no_socios ns ON p.id = ns.persona_id 
        WHERE p.dni = ?
    """.trimIndent()

        return db.rawQuery(query, arrayOf(dni)).use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0) > 0
        }
    }
}