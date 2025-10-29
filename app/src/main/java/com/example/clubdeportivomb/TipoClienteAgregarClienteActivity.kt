package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class TipoClienteAgregarClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tipo_cliente_agregar_cliente)

        // Ajuste de bordes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            return@setOnApplyWindowInsetsListener insets
        }

        // Botones
        val btnSocio = findViewById<Button>(R.id.btnTipoClienteSocio)
        val btnNoSocio = findViewById<Button>(R.id.btnTipoClienteNoSocio)
        val iconBack = findViewById<ImageView>(R.id.iconBack)

        btnSocio.setOnClickListener {
            Toast.makeText(this, "Botón Socio presionado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistroClienteSocio::class.java)
            startActivity(intent)
        }

        btnNoSocio.setOnClickListener {
            Toast.makeText(this, "Botón No Socio presionado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistroClienteNoSocio::class.java)
            startActivity(intent)
        }

        iconBack.setOnClickListener {
            finish() // cierra esta Activity y vuelve a la anterior
        }
    }
}
