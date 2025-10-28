package com.example.clubdeportivomb.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ClubDeportivoDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "club_deportivo.db"
        private const val DATABASE_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON;")

        // Personas
        db.execSQL("""
            CREATE TABLE personas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                dni TEXT NOT NULL UNIQUE,
                fecha_nacimiento TEXT,
                telefono TEXT,
                direccion TEXT,
                email TEXT,
                fecha_alta TEXT
            )
        """)

        // Usuarios
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL,
                rol TEXT CHECK(rol IN ('Administración','Profesores','Nutricionistas')) NOT NULL,
                persona_id INTEGER,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE SET NULL
            )
        """)

        // Socios
        db.execSQL("""
            CREATE TABLE socios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                fecha_alta TEXT NOT NULL,
                estado INTEGER DEFAULT 1,
                certificado TEXT NOT NULL,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // No socios
        db.execSQL("""
            CREATE TABLE no_socios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                certificado TEXT NOT NULL,
                fecha_alta TEXT NOT NULL,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Profesores
        db.execSQL("""
            CREATE TABLE profesores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                especialidad TEXT,
                fecha_alta TEXT NOT NULL,
                sueldo REAL,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Nutricionistas
        db.execSQL("""
            CREATE TABLE nutricionistas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                fecha_alta TEXT NOT NULL,
                matricula TEXT NOT NULL,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Actividades
        db.execSQL("""
            CREATE TABLE actividades (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                descripcion TEXT,
                cupo_maximo INTEGER NOT NULL,
                dia TEXT CHECK(dia IN ('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo')) NOT NULL,
                hora TEXT CHECK(hora IN ('08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00')) NOT NULL,
                salon TEXT CHECK(salon IN ('AZUL','VERDE','ROJO','AMARILLO','VIOLETA','NARANJA','NEGRO','GRIS','BLANCO','CELESTE')) NOT NULL,
                profesor_id INTEGER,
                FOREIGN KEY (profesor_id) REFERENCES profesores(id) ON DELETE SET NULL
            )
        """)
        db.execSQL("CREATE UNIQUE INDEX unq_dia_hora_salon ON actividades(dia, hora, salon)")

        // Actividad participantes
        db.execSQL("""
            CREATE TABLE actividad_participantes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                actividad_id INTEGER NOT NULL,
                persona_id INTEGER NOT NULL,
                tipo_afiliado TEXT CHECK(tipo_afiliado IN ('SOCIO','NO SOCIO')) NOT NULL,
                fecha_inscripcion TEXT NOT NULL,
                FOREIGN KEY (actividad_id) REFERENCES actividades(id) ON DELETE CASCADE,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Asistencias socios
        db.execSQL("""
            CREATE TABLE asistencias_socios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                profesor_id INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                tipo TEXT CHECK(tipo IN ('INGRESO','EGRESO')) NOT NULL,
                hora_entrada TEXT NOT NULL,
                hora_salida TEXT,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Asistencias personal
        db.execSQL("""
            CREATE TABLE asistencias_personal (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                tipo TEXT CHECK(tipo IN ('INGRESO','EGRESO')) NOT NULL,
                clase TEXT CHECK(clase IN ('PROFESOR','NUTRICIONISTA','ADMINISTRATIVO')) NOT NULL,
                fecha TEXT NOT NULL,
                hora_entrada TEXT NOT NULL,
                hora_salida TEXT,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Empleados administrativos
        db.execSQL("""
            CREATE TABLE empleados_administrativos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                fecha_alta TEXT NOT NULL,
                puesto TEXT,
                sueldo REAL,
                FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
            )
        """)

        // Sueldos
        db.execSQL("""
            CREATE TABLE sueldos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER NOT NULL,
                mes INTEGER NOT NULL,
                anio INTEGER NOT NULL,
                horas_trabajadas REAL,
                monto REAL,
                FOREIGN KEY (persona_id) REFERENCES personas(id)
            )
        """)

        // Cuotas
        db.execSQL("""
            CREATE TABLE cuotas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                socio_id INTEGER NOT NULL,
                fecha_vencimiento TEXT NOT NULL,
                fecha_pago TEXT,
                monto REAL,
                estado TEXT CHECK(estado IN ('PAGA','IMPAGA')) DEFAULT 'IMPAGA',
                FOREIGN KEY (socio_id) REFERENCES socios(id) ON DELETE CASCADE
            )
        """)

        // Turnos nutricionista
        db.execSQL("""
            CREATE TABLE turnos_nutricionista (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                socio_id INTEGER NOT NULL,
                nutricionista_id INTEGER NOT NULL,
                fecha_turno TEXT NOT NULL,
                comprobante_emitido INTEGER DEFAULT 0,
                FOREIGN KEY (socio_id) REFERENCES socios(id) ON DELETE CASCADE,
                FOREIGN KEY (nutricionista_id) REFERENCES nutricionistas(id) ON DELETE CASCADE
            )
        """)

        // Pagos
        db.execSQL("""
            CREATE TABLE pagos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni_cliente TEXT,
                tipo_pago TEXT,
                importe REAL,
                motivo TEXT,
                medio_pago TEXT,
                cuotas TEXT,
                fecha_pago TEXT
            )
        """)

        // 🔹 Insertar datos dummy para pruebas
        insertarDatosDummy(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pagos")
        db.execSQL("DROP TABLE IF EXISTS turnos_nutricionista")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        db.execSQL("DROP TABLE IF EXISTS sueldos")
        db.execSQL("DROP TABLE IF EXISTS empleados_administrativos")
        db.execSQL("DROP TABLE IF EXISTS asistencias_personal")
        db.execSQL("DROP TABLE IF EXISTS asistencias_socios")
        db.execSQL("DROP TABLE IF EXISTS actividad_participantes")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS nutricionistas")
        db.execSQL("DROP TABLE IF EXISTS profesores")
        db.execSQL("DROP TABLE IF EXISTS no_socios")
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS personas")
        onCreate(db)
    }

    // ----------------------------------------------------
    // Datos Dummy
    // ----------------------------------------------------
    private fun insertarDatosDummy(db: SQLiteDatabase) {
        try {
            val nombres = listOf("Juan", "María", "Carlos", "Lucía", "Pedro", "Laura", "José", "Ana", "Diego", "Sofía")
            val apellidos = listOf("Pérez", "Gómez", "López", "Fernández", "Ruiz", "Torres", "Silva", "Molina", "Acosta", "Herrera")

            val personaIds = mutableListOf<Long>()

            // PERSONAS
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("nombre", nombres[i])
                    put("apellido", apellidos[i])
                    put("dni", "40000${100 + i}")
                    put("fecha_nacimiento", "199${i}-05-10")
                    put("telefono", "26140000${i}")
                    put("direccion", "Calle Falsa ${i * 10}")
                    put("email", "${nombres[i].lowercase()}@mail.com")
                    put("fecha_alta", "2025-01-01")
                }
                personaIds.add(db.insert("personas", null, cv))
            }

            // SOCIOS
            val socioIds = mutableListOf<Long>()
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("persona_id", personaIds[i])
                    put("fecha_alta", "2025-02-01")
                    put("estado", 1)
                    put("certificado", "CertSocio-${100 + i}")
                }
                socioIds.add(db.insert("socios", null, cv))
            }

            // NO SOCIOS
            val noSocioIds = mutableListOf<Long>()
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("persona_id", personaIds[i])
                    put("certificado", "CertNoSocio-${200 + i}")
                    put("fecha_alta", "2025-02-05")
                }
                noSocioIds.add(db.insert("no_socios", null, cv))
            }

            // PROFESORES
            val profesorIds = mutableListOf<Long>()
            val especialidades = listOf("Spinning", "Yoga", "Crossfit", "Pilates", "Zumba", "Natación", "Boxeo", "Funcional", "GAP", "HIIT")
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("persona_id", personaIds[i])
                    put("especialidad", especialidades[i])
                    put("fecha_alta", "2025-01-10")
                    put("sueldo", 50000 + (i * 1500))
                }
                profesorIds.add(db.insert("profesores", null, cv))
            }

            // NUTRICIONISTAS
            val nutricionistaIds = mutableListOf<Long>()
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("persona_id", personaIds[i])
                    put("fecha_alta", "2025-01-15")
                    put("matricula", "MAT-${300 + i}")
                }
                nutricionistaIds.add(db.insert("nutricionistas", null, cv))
            }

            // ACTIVIDADES
            val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
            val horas = listOf("18:00", "19:00", "20:00", "21:00")
            val salones = listOf("AZUL", "VERDE", "ROJO", "AMARILLO", "VIOLETA", "NARANJA", "NEGRO", "GRIS", "BLANCO", "CELESTE")

            val actividadIds = mutableListOf<Long>()
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("nombre", "Actividad ${i + 1}")
                    put("descripcion", "Entrenamiento ${especialidades[i]} nivel ${i + 1}")
                    put("cupo_maximo", 20 + i)
                    put("dia", dias[i % dias.size])
                    put("hora", horas[i % horas.size])
                    put("salon", salones[i % salones.size])
                    put("profesor_id", profesorIds[i % profesorIds.size])
                }
                actividadIds.add(db.insert("actividades", null, cv))
            }

            // PARTICIPANTES
            for (i in 0 until 10) {
                val cvSocio = ContentValues().apply {
                    put("actividad_id", actividadIds[i])
                    put("persona_id", personaIds[i])
                    put("tipo_afiliado", if (i % 2 == 0) "SOCIO" else "NO SOCIO")
                    put("fecha_inscripcion", "2025-03-${10 + i}")
                }
                db.insert("actividad_participantes", null, cvSocio)
            }

            // PAGOS
            for (i in 0 until 10) {
                val cv = ContentValues().apply {
                    put("dni_cliente", "40000${100 + i}")
                    put("tipo_pago", "Cuota")
                    put("importe", 5000 + i * 100)
                    put("motivo", "Pago cuota mensual")
                    put("medio_pago", if (i % 2 == 0) "Efectivo" else "Tarjeta")
                    put("cuotas", "1")
                    put("fecha_pago", "2025-03-${10 + i}")
                }
                db.insert("pagos", null, cv)
            }

            Log.d("DBHelper", "✅ Datos dummy insertados correctamente")

        } catch (e: Exception) {
            Log.e("DBHelper", "❌ Error insertando datos dummy", e)
        }
    }
}
