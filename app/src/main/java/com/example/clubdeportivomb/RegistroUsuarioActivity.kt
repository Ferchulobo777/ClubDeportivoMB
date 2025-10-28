package com.example.clubdeportivomb

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.Calendar


class RegistroUsuarioActivity : AppCompatActivity() {

    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        // === Inicializar Repositorio (maneja la BD)
        repository = ClubDeportivoRepository(this)

        // === Referencias a los campos del formulario ===
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val etDni = findViewById<EditText>(R.id.etDni)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etFechaInscripcion = findViewById<EditText>(R.id.etFechaInscripcion)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmarContrasena)
        val autoCompleteArea = findViewById<MaterialAutoCompleteTextView>(R.id.autoCompleteArea)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val tvIniciaSesion = findViewById<TextView>(R.id.tvIniciaSesion)
        val iconBack = findViewById<ImageView>(R.id.iconBack)

        // === Poblamos las áreas (dropdown)
        val areas = resources.getStringArray(R.array.areas_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areas)
        autoCompleteArea.setAdapter(adapter)

        // === Botones de navegación ===
        btnCancelar.setOnClickListener {
            finish() // simplemente cierra el activity
        }

        tvIniciaSesion.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        iconBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // === DatePickers para fechas ===
        val calendar = Calendar.getInstance()

        etFechaNacimiento.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    etFechaNacimiento.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        etFechaInscripcion.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    etFechaInscripcion.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // === Lógica de registro ===
        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val fechaNacimiento = etFechaNacimiento.text.toString().trim()
            val dni = etDni.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val fechaAlta = etFechaInscripcion.text.toString().trim()
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val confirmarContrasena = etConfirmarContrasena.text.toString().trim()
            val area = autoCompleteArea.text.toString().trim()



            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() ||
                usuario.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }




            if (contrasena != confirmarContrasena) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1️⃣ Insertar persona
            val personaId = repository.insertarPersona(
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                fechaNacimiento = fechaNacimiento,
                telefono = telefono,
                direccion = direccion,
                email = email,
                fechaAlta = fechaAlta
            )

            if (personaId == -1L) {
                Toast.makeText(this, "Error al registrar persona", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2️⃣ Insertar usuario
            val passwordHash = contrasena.hashCode().toString() // ejemplo simple
            val usuarioId = repository.insertarUsuario(
                username = usuario,
                passwordHash = passwordHash,
                rol = area, // acá podés usar el área o un rol fijo
                personaId = personaId
            )

            if (usuarioId == -1L) {
                Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Usuario registrado correctamente ✅", Toast.LENGTH_LONG).show()

            // Redirige al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
