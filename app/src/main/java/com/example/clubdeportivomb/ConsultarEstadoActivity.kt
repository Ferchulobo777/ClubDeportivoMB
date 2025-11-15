package com.example.clubdeportivomb

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConsultarEstadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_estado_cuota)

        // mostrar datos del usuario que vienen de PagosActivity
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        val tvUsuario = findViewById<TextView>(R.id.tvUsuario)
        tvUsuario.text = "$nombreUsuario - $rolUsuario"


        val iconBack = findViewById<ImageView>(R.id.iconBack)
        iconBack.setOnClickListener {
            finish() // cierra la activity y vuelve a la anterior
        }

    }


}
