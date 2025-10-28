package com.example.clubdeportivomb

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.SearchView
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
import java.util.Locale

class VencimientosActivity : AppCompatActivity() {

    data class Cliente(val nombre: String, val esSocio: Boolean, val fechaVencimiento: String)

    private lateinit var tablaVencimientos: TableLayout
    private val listaOriginalClientes = listOf(
        Cliente("Juan Pérez", true, "30/12/2025"),
        Cliente("Laura Gómez", true, "15/09/2025"),      // <-- VENCIDO
        Cliente("Marcos Díaz", false, "01/10/2025"),     // <-- VENCIDO
        Cliente("Ana García", false, "15/11/2025"),
        Cliente("Carlos Sánchez", true, "25/11/2025"),
        Cliente("Sofía Rodríguez", false, "N/A"),
        Cliente("Luis Martínez", true, "01/12/2025")
    )

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
        tablaVencimientos = findViewById(R.id.tablaVencimientos)
        val searchView = findViewById<SearchView>(R.id.searchView)

        iconBack.setOnClickListener { finish() }

        val listaOrdenadaInicial = ordenarLista(listaOriginalClientes)
        actualizarTabla(listaOrdenadaInicial)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val textoDeBusqueda = newText?.lowercase(Locale.getDefault()) ?: ""
                val listaFiltrada = if (textoDeBusqueda.isEmpty()) {
                    listaOriginalClientes
                } else {
                    listaOriginalClientes.filter {
                        it.nombre.lowercase(Locale.getDefault()).contains(textoDeBusqueda)
                    }
                }
                val listaFiltradaYOrdenada = ordenarLista(listaFiltrada)
                actualizarTabla(listaFiltradaYOrdenada)
                return true
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ordenarLista(lista: List<Cliente>): List<Cliente> {
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return lista.sortedWith(compareBy { cliente ->
            try {
                LocalDate.parse(cliente.fechaVencimiento, formatoFecha)
            } catch (e: DateTimeParseException) {
                LocalDate.MAX
            }
        })
    }

    /**
     * Limpia la tabla y la vuelve a llenar con una nueva lista de clientes.
     * Ahora también comprueba si la fecha de vencimiento ha pasado y colorea la fila.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun actualizarTabla(listaClientes: List<Cliente>) {
        val childCount = tablaVencimientos.childCount
        if (childCount > 1) {
            tablaVencimientos.removeViews(1, childCount - 1)
        }

        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val hoy = LocalDate.now()

        for (cliente in listaClientes) {
            val fila = TableRow(this)
            val tvNombre = crearCelda(cliente.nombre)
            val tvTipo = crearCelda(if (cliente.esSocio) "Socio" else "No Socio")
            val tvFecha = crearCelda(cliente.fechaVencimiento, alinearDerecha = true)

            // --- INICIO DE LA LÓGICA DE COLOREADO ---
            try {
                val fechaVencimiento = LocalDate.parse(cliente.fechaVencimiento, formatoFecha)
                // Comprueba si la fecha de vencimiento es ANTERIOR a la fecha de hoy
                if (fechaVencimiento.isBefore(hoy)) {
                    // Pinta el fondo de la fila de color rojo
                    fila.setBackgroundColor(ContextCompat.getColor(this, R.color.fila_vencida))
                }
            } catch (e: DateTimeParseException) {
                // Si la fecha es "N/A" u otra cosa, no hagas nada y deja el fondo transparente.
            }
            // --- FIN DE LA LÓGICA DE COLOREADO ---

            fila.addView(tvNombre)
            fila.addView(tvTipo)
            fila.addView(tvFecha)

            tablaVencimientos.addView(fila)
        }
    }

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
