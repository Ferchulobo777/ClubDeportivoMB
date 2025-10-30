package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivomb.databinding.ActivityTipoClienteAgregarClienteBinding
import android.widget.Toast
import com.example.clubdeportivomb.utils.AppUtils

class TipoClienteAgregarClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTipoClienteAgregarClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipoClienteAgregarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del usuario
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Mostrar nombre en el header USANDO BINDING
        binding.tvUsuario.text = "$nombreUsuario - $rolUsuario"

        // ANIMACIÓN DE LA PELOTA USANDO BINDING
        AppUtils.startBallAnimation(binding.imgPelota, this)

        // Listeners de botones USANDO BINDING
        binding.btnTipoClienteSocio.setOnClickListener {
            Toast.makeText(this, "Botón Socio presionado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistroClienteSocio::class.java)
            // PASAR LOS DATOS DEL USUARIO
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.btnTipoClienteNoSocio.setOnClickListener {
            Toast.makeText(this, "Botón No Socio presionado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistroClienteNoSocio::class.java)
            // PASAR LOS DATOS DEL USUARIO
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }

        binding.iconBack.setOnClickListener {
            finish()
        }
    }
}