package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import com.example.clubdeportivomb.db.ClubDeportivoDBHelper
import com.example.clubdeportivomb.model.Usuario
import java.security.MessageDigest
import androidx.activity.addCallback
import android.view.LayoutInflater

class LoginActivity : AppCompatActivity() {

    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ✅ CORREGIDO: Crear instancia del DBHelper primero
        val dbHelper = ClubDeportivoDBHelper(this)
        repository = ClubDeportivoRepository(dbHelper)

        // Configuración normal de ventana - CONTROLES VISIBLES
        // Se eliminó el modo inmersivo que ocultaba los controles

        // Referencias
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val etUsuario = findViewById<TextInputEditText>(R.id.title_inputUsuario)
        val etPassword = findViewById<TextInputEditText>(R.id.title_inputPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.title_btnIngresar)

        // Redirigir al registro
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroUsuarioActivity::class.java))
        }

        // Acción de login
        btnLogin.setOnClickListener {
            val username = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Complete usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordHash = password.hashCode().toString()
            // ✅ CORREGIDO: Usar el método correcto del repository
            val usuario: Usuario? = repository.obtenerUsuarioPorUsername(username)

            if (usuario != null) {
                // Verificar contraseña (comparar el hash)
                if (passwordHash == usuario.passwordHash) {
                    when (usuario.rol) {
                        "Administración", "Profesores", "Nutricionistas" -> {
                            openHome(usuario.username, usuario.rol)
                        }
                        else -> Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        // Confirmación al presionar atrás - USA TU MODAL PERSONALIZADO
        onBackPressedDispatcher.addCallback(this) {
            showExitDialog()  // ← Cambiado para usar tu modal personalizado
        }
    }

    // Función para abrir HomeActivity
    private fun openHome(nombreUsuario: String, rolUsuario: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
        intent.putExtra("ROL_USUARIO", rolUsuario)
        startActivity(intent)
        finish()
    }

    // FUNCIÓN PARA MOSTRAR TU MODAL PERSONALIZADO DE SALIR
    private fun showExitDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_modal_salir, null)

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

        // Botón SÍ - cierra la aplicación
        val btnSi = dialogView.findViewById<MaterialButton>(R.id.btnSi)
        btnSi.setOnClickListener {
            dialog.dismiss()
            finishAffinity() // Cierra todas las actividades y sale de la app
        }

        dialog.show()
    }

    // Función para generar hash SHA-256 (por si querés usarlo)
    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}