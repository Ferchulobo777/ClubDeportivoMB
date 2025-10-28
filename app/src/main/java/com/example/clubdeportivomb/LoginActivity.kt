package com.example.clubdeportivomb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import com.example.clubdeportivomb.model.Usuario
import java.security.MessageDigest
import androidx.activity.addCallback


class LoginActivity : AppCompatActivity() {

    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        repository = ClubDeportivoRepository(this)

        // Configurar ventana completa
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE



        // Referencias
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val etUsuario = findViewById<TextInputEditText>(R.id.title_inputUsuario)
        val etPassword = findViewById<TextInputEditText>(R.id.title_inputPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.title_btnIngresar)

        // Redirigir al registro
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroUsuarioActivity::class.java))
        }

        // Ajustar padding por barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
            val usuario: Usuario? = repository.obtenerUsuario(username, passwordHash)

            if (usuario != null) {
                when (usuario.rol) {
                    "Administración", "Profesores", "Nutricionistas" -> {
                        openHome(usuario.username, usuario.rol)
                    }
                    else -> Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        // Confirmación al presionar atrás
        onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(this@LoginActivity).apply {
                setTitle("Salir")
                setMessage("¿Deseas salir de la aplicación?")
                setPositiveButton("Sí") { dialog, _ ->
                    dialog.dismiss()
                    finish() // Cierra la actividad
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }

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

    // Función para generar hash SHA-256 (por si querés usarlo)
    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
