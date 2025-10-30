package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity  // ← ESTE IMPORT FALTABA
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.ImageView
import android.view.LayoutInflater
import com.example.clubdeportivomb.utils.AppUtils

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iconBack = findViewById<ImageView>(R.id.iconBack)

        // Chevron - muestra modal de CERRAR SESIÓN (no salir de la app)
        iconBack.setOnClickListener {
            showLogoutDialog()  // ← Cambiado a logout
        }

        // Obtener datos del usuario
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Referencias a las vistas
        val tvSaludo = findViewById<TextView>(R.id.title_saludo_menu)
        val imgPelota = findViewById<ImageView>(R.id.imgPelota)
        val tvUser = findViewById<TextView>(R.id.tvUsuario)

        // Usar las funciones utilitarias
        AppUtils.setStyledWelcomeMessage(tvSaludo, nombreUsuario, this)
        AppUtils.startBallAnimation(imgPelota, this)

        // Mostrar nombre en el header
        tvUser.text = "$nombreUsuario - $rolUsuario"

        // Botones del menú
        val btnClientes = findViewById<MaterialButton>(R.id.btnClientes)
        val btnPersonal = findViewById<MaterialButton>(R.id.btnPersonal)
        val btnActividades = findViewById<MaterialButton>(R.id.btnActividades)
        val btnTurnos = findViewById<MaterialButton>(R.id.btnTurnos)
        val btnAcercaDe = findViewById<MaterialButton>(R.id.btnAcercaDe)

        // Ejemplo: ocultar botón Turnos si el usuario no es Administración
        if (rolUsuario != "Administración") {
            btnTurnos.visibility = View.GONE
        }

        // Listeners de botones
        btnClientes.setOnClickListener {
            val intent = Intent(this, ClienteActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnPersonal.setOnClickListener {
            val intent = Intent(this, PersonalActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnActividades.setOnClickListener {
            val intent = Intent(this, ActividadesActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnTurnos.setOnClickListener {
            val intent = Intent(this, TurnosActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnAcercaDe.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Acerca De")
                setMessage("Desarrollo de Aplicaciones para Dispositivos Moviles\n" +
                        "Proyecto creado por:\n" +
                        "\n" +
                        "Fernando Rodriguez\n" +
                        "Vanesa Aracena\n" +
                        "Emilia Sosa\n" +
                        "Franco Guarachi\n" +
                        "Tomás Maldocena")
                setNegativeButton("Cerrar", null)
                create()
                show()
            }
        }
    }

    // Botón físico BACK - también muestra el modal de CERRAR SESIÓN
    override fun onBackPressed() {
        showLogoutDialog()  // ← Cambiado a logout
    }

    // FUNCIÓN PARA MOSTRAR EL MODAL DE CERRAR SESIÓN (vuelve al Login)
    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_cerrar_sesion, null)  // ← XML de cerrar sesión

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Configurar el fondo transparente del dialog
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.6f) // Fondo oscuro semitransparente

        // Botón NO - simplemente cierra el dialog
        val btnNo = dialogView.findViewById<MaterialButton>(R.id.btnNo)
        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        // Botón SÍ - CIERRA SESIÓN Y VUELVE AL LOGIN
        val btnSi = dialogView.findViewById<MaterialButton>(R.id.btnSi)
        btnSi.setOnClickListener {
            dialog.dismiss()
            // Cierra todas las actividades y vuelve al Login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        dialog.show()
    }
}