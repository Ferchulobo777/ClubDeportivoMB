package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityClienteBinding
import android.widget.Toast
import androidx.activity.addCallback
import android.widget.ImageView

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iconBack = findViewById<ImageView>(R.id.iconBack)
        iconBack.setOnClickListener {
            finish() // cierra esta Activity y vuelve a la anterior
        }

        // Listener para "Añadir Nuevo Cliente"
        binding.btnAgregarNuevoCliente.setOnClickListener {
            Toast.makeText(this, "Añadir Nuevo Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TipoClienteAgregarClienteActivity::class.java)
            startActivity(intent)
        }

        // Listener para "Modificar Cliente"
        binding.btnModificarCliente.setOnClickListener {
            Toast.makeText(this, "Modificar Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TipoClienteModificarCliente::class.java)
            startActivity(intent)
        }

        // Listener para "Buscar Cliente"
        binding.btnBuscarCliente.setOnClickListener {
            Toast.makeText(this, "Buscar Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,  ClienteBuscarCliente::class.java)
            startActivity(intent)
        }

        // Listener para "Control de Acceso"
        binding.btnControlAcceso.setOnClickListener {
            Toast.makeText(this, "Control de Acceso", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, ControlAccesoActivity::class.java))
        }

        // Listener para "¿Necesitas ayuda?"
        binding.tvAyuda.setOnClickListener {
            Toast.makeText(this, "Necesitas ayuda?", Toast.LENGTH_SHORT).show()
            // Podés abrir un diálogo o Activity de ayuda
        }

        onBackPressedDispatcher.addCallback(this) {
            // Esta acción se ejecutará cuando se presione el botón de atrás.
            // finish() destruye la actividad actual y vuelve a la anterior en la pila.
            finish()
        }
    }
}
