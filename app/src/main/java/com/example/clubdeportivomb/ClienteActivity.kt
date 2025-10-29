package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityClienteBinding
import android.widget.Toast

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener para "Añadir Nuevo Cliente"
        binding.btnAgregarNuevoCliente.setOnClickListener {
            Toast.makeText(this, "Añadir Nuevo Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TipoClienteAgregarClienteActivity::class.java)
            startActivity(intent)
        }

        // Listener para "Vencimiento Cliente"
        binding.btnVencimientosCliente.setOnClickListener {
            Toast.makeText(this, "Vencimientos Clientes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, VencimientosActivity::class.java)
            startActivity(intent)
        }

        // Listener para "Buscar Cliente"
        binding.btnBuscarCliente.setOnClickListener {
            Toast.makeText(this, "Buscar Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ClienteBuscarCliente::class.java)
            startActivity(intent)
        }

        // Listener para "Control de Acceso"
        binding.btnControlAcceso.setOnClickListener {
            Toast.makeText(this, "Control de Acceso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlAcceso::class.java)
            startActivity(intent)
        }

        // Listener para "¿Necesitas ayuda?"
        binding.tvAyuda.setOnClickListener {
            Toast.makeText(this, "Necesitas ayuda?", Toast.LENGTH_SHORT).show()
            // Podés abrir un diálogo o Activity de ayuda
        }
    }
}
