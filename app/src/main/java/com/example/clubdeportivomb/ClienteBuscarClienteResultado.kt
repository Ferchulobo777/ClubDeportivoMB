package com.example.clubdeportivomb

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivomb.databinding.ActivityClienteBuscarClienteResultadoBinding
import com.example.clubdeportivomb.utils.AppUtils
import com.google.android.material.button.MaterialButton

class ClienteBuscarClienteResultado : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBuscarClienteResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBuscarClienteResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener datos del usuario y búsqueda
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"
        val textoBusqueda = intent.getStringExtra("TEXTO_BUSQUEDA") ?: ""

        // Mostrar datos del usuario en el header
        binding.tvUsuario.text = "$nombreUsuario - $rolUsuario"

        // Actualizar título con el texto de búsqueda
        binding.titleResultadoBusquedaCliente.text = "Resultados para: $textoBusqueda"

        // ANIMACIÓN DE LA PELOTA
        AppUtils.startBallAnimation(binding.imgPelota, this)

        // Botón volver atrás
        binding.iconBack.setOnClickListener {
            finish()
        }

        // Botones de acción
        binding.btnModificar.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de Modificar", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar la lógica para modificar cliente
        }

        binding.btnEliminar.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de Eliminar", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar la lógica para eliminar cliente
        }

        // FOOTER AYUDA
        binding.tvAyuda.setOnClickListener {
            showHelpDialog()
        }

        // === LLAMAR A LA BASE DE DATOS PARA BUSCAR ===
        buscarClientesEnDB(textoBusqueda)
    }

    // FUNCIÓN PARA BUSCAR EN LA BASE DE DATOS
    private fun buscarClientesEnDB(textoBusqueda: String) {
        // Aquí implementarás la lógica de búsqueda en la base de datos
        // Por ahora solo mostramos un mensaje
        Toast.makeText(this, "Buscando en DB: $textoBusqueda", Toast.LENGTH_SHORT).show()

        // TODO: Cuando tengas la base de datos, implementar la búsqueda real
        /*
        val repository = ClubDeportivoRepository(this)
        val resultados = repository.buscarClientes(textoBusqueda)

        if (resultados.isEmpty()) {
            Toast.makeText(this, "No se encontraron clientes", Toast.LENGTH_LONG).show()
        } else {
            // Configurar el RecyclerView con los resultados
            configurarRecyclerView(resultados)
        }
        */
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