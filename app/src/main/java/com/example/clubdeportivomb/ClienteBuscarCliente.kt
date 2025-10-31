package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivomb.databinding.ActivityClienteBuscarClienteBinding
import com.example.clubdeportivomb.db.ClubDeportivoDBHelper
import com.example.clubdeportivomb.repository.ClubDeportivoRepository // ✅ Asegúrate de tener este import
import com.example.clubdeportivomb.utils.AppUtils
import com.google.android.material.button.MaterialButton

class ClienteBuscarCliente : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBuscarClienteBinding
    private lateinit var repository: ClubDeportivoRepository // ✅ Declarar repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBuscarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ INICIALIZAR REPOSITORY
        val dbHelper = ClubDeportivoDBHelper(this)
        repository = ClubDeportivoRepository(dbHelper)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener datos del usuario
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Mostrar datos del usuario en el header
        binding.tvUsuario.text = "$nombreUsuario - $rolUsuario"

        // ANIMACIÓN DE LA PELOTA
        AppUtils.startBallAnimation(binding.imgPelota, this)

        // Botón volver atrás (simple finish)
        binding.iconBack.setOnClickListener {
            finish()
        }

        // Botón buscar cliente - CONEXIÓN CON PANTALLA DE RESULTADOS
        binding.btnBuscarCliente.setOnClickListener {
            val textoBusqueda = binding.etBuscarCliente.text.toString().trim()
            if (textoBusqueda.isEmpty()) {
                Toast.makeText(this, "Ingrese un nombre o DNI para buscar", Toast.LENGTH_SHORT).show()
            } else {
                // ✅ OPCIONAL: Puedes hacer una búsqueda rápida aquí para validar
                // val resultados = repository.buscarSocios(textoBusqueda) // Esto debería funcionar ahora

                // Navegar a la pantalla de resultados
                val intent = Intent(this, ClienteBuscarClienteResultado::class.java)
                // Pasar los datos de búsqueda y del usuario
                intent.putExtra("TEXTO_BUSQUEDA", textoBusqueda)
                intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
                intent.putExtra("ROL_USUARIO", rolUsuario)
                startActivity(intent)
            }
        }

        // FOOTER AYUDA
        binding.tvAyuda.setOnClickListener {
            showHelpDialog()
        }
    }

    // Botón físico BACK - vuelve directamente
    override fun onBackPressed() {
        finish()
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