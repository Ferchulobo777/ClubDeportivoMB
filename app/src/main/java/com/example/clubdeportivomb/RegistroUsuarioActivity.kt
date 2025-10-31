package com.example.clubdeportivomb

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityRegistroUsuarioBinding
import com.example.clubdeportivomb.db.ClubDeportivoDBHelper
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import java.util.Calendar

class RegistroUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroUsuarioBinding
    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ CORREGIDO: Crear instancia del DBHelper primero
        val dbHelper = ClubDeportivoDBHelper(this)
        repository = ClubDeportivoRepository(dbHelper)

        // Configurar DatePickers
        setupDatePickers()

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val dni = binding.etDni.text.toString().trim()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            // ✅ CORREGIDO: Usar etFechaInscripcion que existe en el XML
            val fechaAlta = binding.etFechaInscripcion.text.toString().trim()
            // ✅ CORREGIDO: Usar etUsuario que existe en el XML
            val username = binding.etUsuario.text.toString().trim()
            // ✅ CORREGIDO: Usar etContrasena que existe en el XML
            val password = binding.etContrasena.text.toString().trim()
            // ✅ CORREGIDO: Usar autoCompleteArea que existe en el XML
            val rol = binding.autoCompleteArea.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || username.isEmpty() || password.isEmpty() || rol.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que las contraseñas coincidan
            val confirmarPassword = binding.etConfirmarContrasena.text.toString().trim()
            if (password != confirmarPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordHash = password.hashCode().toString()

            val usuarioId = repository.insertarUsuarioCompleto(
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                fechaNacimiento = fechaNacimiento,
                telefono = telefono,
                direccion = direccion,
                email = email,
                fechaAlta = fechaAlta,
                username = username,
                passwordHash = passwordHash,
                rol = rol
            )

            if (usuarioId != -1L) {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ CORREGIDO: Usar tvIniciaSesion que existe en el XML
        binding.tvIniciaSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ✅ CORREGIDO: Usar btnCancelar que existe en el XML
        binding.btnCancelar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ✅ CORREGIDO: Usar iconBack que existe en el XML
        binding.iconBack.setOnClickListener {
            finish()
        }
    }

    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()

        binding.etFechaNacimiento.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.etFechaNacimiento.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.etFechaInscripcion.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.etFechaInscripcion.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}