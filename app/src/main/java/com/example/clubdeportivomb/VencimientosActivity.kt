package com.example.clubdeportivomb

import android.os.Build
import android.os.Bundle
import androidx.core.text.color

import android.view.Gravity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class VencimientosActivity : AppCompatActivity() {

    // Define una estructura de datos simple para los clientes
    data class Cliente(val nombre: String, val esSocio: Boolean, val fechaVencimiento: String)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vencimientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iconBack = findViewById<ImageView>(R.id.iconBack)

        iconBack.setOnClickListener {
            finish() // cierra esta Activity y vuelve a la anterior
        }

        // Obtener la referencia a la tabla
        val tablaVencimientos = findViewById<TableLayout>(R.id.tablaVencimientos)

        // Datos de ejemplo que están hardcodeados (solo necesitamos nombre y fecha de vencimiento, y si es socio o no)
        val listaClientes = listOf(
            Cliente("Juan Pérez", true, "30/12/2025"),
            Cliente("Ana García", false, "15/11/2025"),
            Cliente("Carlos Sánchez", true, "25/11/2025"),
            Cliente("Sofía Rodríguez", false, "N/A"), // Cliente con fecha no válida
            Cliente("Luis Martínez", true, "01/12/2025")
        )

        // Logica para ordenar por fecha más próxima

        // Definir el formato de fecha que usamos en nuestro texto
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Ordenando la lista de clientes
        val listaOrdenada = listaClientes.sortedWith(compareBy { cliente ->
            try {
                // Intenta convertir el texto de la fecha a un objeto LocalDate
                LocalDate.parse(cliente.fechaVencimiento, formatoFecha)
            } catch (e: DateTimeParseException) {
                // Si la fecha no es válida (ej: "N/A"), la mandamos al final de la lista.
                // LocalDate.MAX es una fecha muy lejana en el futuro.
                LocalDate.MAX
            }
        })


        // Llenando la tabla con la LISTA ORDENADA
        for (cliente in listaOrdenada) {
            val fila = TableRow(this)

            val tvNombre = crearCelda(cliente.nombre)
            val tvTipo = crearCelda(if (cliente.esSocio) "Socio" else "No Socio")
            val tvFecha = crearCelda(cliente.fechaVencimiento, alinearDerecha = true)

            fila.addView(tvNombre)
            fila.addView(tvTipo)
            fila.addView(tvFecha)

            tablaVencimientos.addView(fila)
        }
    }

    /**
     * Función de ayuda para crear y estilizar cada celda (TextView) de la tabla.
     */
    private fun crearCelda(texto: String, alinearDerecha: Boolean = false): TextView {
        val textView = TextView(this)
        textView.text = texto
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.setPadding(8, 8, 8, 8)
        textView.textSize = 14f
        if (alinearDerecha) {
            textView.gravity = Gravity.END
        }
        return textView
    }
}

