package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityClienteBinding
import android.widget.Toast
import com.example.clubdeportivomb.utils.AppUtils

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del usuario
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Mostrar nombre y rol en el header USANDO BINDING
        binding.tvUsuario.text = "$nombreUsuario - $rolUsuario"

        // ANIMACIÓN DE LA PELOTA USANDO BINDING
        AppUtils.startBallAnimation(binding.imgPelota, this)

        // Listeners de botones
        binding.btnAgregarNuevoCliente.setOnClickListener {
            Toast.makeText(this, "Nuevo Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TipoClienteAgregarClienteActivity::class.java)
            // PASAR LOS DATOS DEL USUARIO
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.btnVencimientosCliente.setOnClickListener {
            Toast.makeText(this, "Vencimientos Clientes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, VencimientosActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.btnBuscarCliente.setOnClickListener {
            Toast.makeText(this, "Buscar Cliente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ClienteBuscarCliente::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.btnControlAcceso.setOnClickListener {
            Toast.makeText(this, "Control de Acceso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlAcceso::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.btnVolverMenu.setOnClickListener {
            finish()
        }

        binding.tvAyuda.setOnClickListener {
            Toast.makeText(this, "Necesitas ayuda?", Toast.LENGTH_SHORT).show()
        }

        binding.iconBack.setOnClickListener {
            finish()
        }
    }
}