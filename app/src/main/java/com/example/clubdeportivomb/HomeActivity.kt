package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        // Chevron - muestra modal de CERRAR SESIÓN
        iconBack.setOnClickListener {
            showLogoutDialog()
        }

        // Obtener datos del usuario
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Referencias a las vistas
        val tvSaludo = findViewById<TextView>(R.id.title_saludo_menu)
        val imgPelota = findViewById<ImageView>(R.id.imgPelota)
        val tvUser = findViewById<TextView>(R.id.tvUsuario)
        val tvAyuda = findViewById<TextView>(R.id.tvAyuda)

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
            showAboutDialog()
        }

        // Listener para "¿Necesitas ayuda?" - ABRE EL MODAL PERSONALIZADO
        tvAyuda.setOnClickListener {
            showHelpDialog()
        }
    }

    // Botón físico BACK - también muestra el modal de CERRAR SESIÓN
    override fun onBackPressed() {
        showLogoutDialog()
    }

    // FUNCIÓN PARA MOSTRAR EL MODAL DE CERRAR SESIÓN (vuelve al Login)
    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_cerrar_sesion, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.6f)

        val btnNo = dialogView.findViewById<MaterialButton>(R.id.btnNo)
        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        val btnSi = dialogView.findViewById<MaterialButton>(R.id.btnSi)
        btnSi.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        dialog.show()
    }

    // FUNCIÓN PARA MOSTRAR EL MODAL PERSONALIZADO DE ACERCA DE
    private fun showAboutDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_acerca_de, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.6f)

        val btnVolver = dialogView.findViewById<MaterialButton>(R.id.button)
        btnVolver.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // FUNCIÓN PARA MOSTRAR EL MODAL PERSONALIZADO DE AYUDA
    private fun showHelpDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_ayuda, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.6f)

        val btnVolver = dialogView.findViewById<MaterialButton>(R.id.button)
        btnVolver.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}