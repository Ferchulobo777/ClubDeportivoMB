package com.example.clubdeportivomb

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityRegistroClienteSocioBinding
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import com.example.clubdeportivomb.utils.AppUtils
import com.google.android.material.button.MaterialButton
import java.util.Calendar

class RegistroClienteSocio : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteSocioBinding
    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteSocioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // === Inicializar Repositorio ===
        repository = ClubDeportivoRepository(this)

        // === Obtener datos del usuario ===
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // === Mostrar datos del usuario en el header ===
        binding.tvUsuario.text = "$nombreUsuario - $rolUsuario"

        // === ANIMACIÓN DE LA PELOTA ===
        AppUtils.startBallAnimation(binding.imgPelota, this)

        // === TÍTULO CON "SOCIO" DESTACADO
        AppUtils.setStyledTextWithHighlight(
            binding.titleTipoClienteAgregar,
            "Completa todos los campos de SOCIO que quieres registrar",
            "SOCIO",
            this
        )

        // === Botón volver atrás ===
        binding.iconBack.setOnClickListener {
            finish()
        }

        // === DatePickers ===
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

        // === Lógica de guardado de socio ===
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString().trim()
            val dni = binding.etDni.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val fechaAlta = binding.etFechaInscripcion.text.toString().trim()

            // === Validación básica ===
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // === Insertar socio en la base de datos ===
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
                Toast.makeText(this, "Error al registrar socio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Socio registrado correctamente ✅", Toast.LENGTH_LONG).show()
            finish() // vuelve al menú anterior
        }

        // === Botón Apto Físico (opcional) ===
        binding.btnAptoFisico.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de Apto Físico", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar la lógica para el apto físico
        }

        // === FOOTER AYUDA ===
        binding.tvAyuda.setOnClickListener {
            showHelpDialog()
        }
    }

    // FUNCIÓN PARA MOSTRAR EL MODAL PERSONALIZADO DE AYUDA
    private fun showHelpDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_ayuda, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.6f)

        val btnVolver = dialogView.findViewById<MaterialButton>(R.id.button)
        btnVolver.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}