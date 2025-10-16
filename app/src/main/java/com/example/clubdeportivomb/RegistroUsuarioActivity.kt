package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class RegistroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)

        // Ajuste de márgenes para status/navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_registro_usuario)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Edge-to-edge
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )

        // ===== Botón volver =====
        val iconBack = findViewById<ImageView>(R.id.iconBack)
        iconBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ===== Footer Inicia sesión =====
        val tvIniciaSesion = findViewById<TextView>(R.id.tvIniciaSesion)
        tvIniciaSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ===== Área (MaterialAutoCompleteTextView) =====
        val autoCompleteArea = findViewById<MaterialAutoCompleteTextView>(R.id.autoCompleteArea)
        val areas = resources.getStringArray(R.array.areas_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areas)
        autoCompleteArea.setAdapter(adapter)

        autoCompleteArea.setOnItemClickListener { parent, _, position, _ ->
            val selectedArea = parent.getItemAtPosition(position).toString()
            // Guardar o usar la selección
        }
    }
}
