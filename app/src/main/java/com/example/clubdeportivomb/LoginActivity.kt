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
import com.example.clubdeportivomb.repository.ClubDeportivoRepository
import com.example.clubdeportivomb.model.Usuario
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var repository: ClubDeportivoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        repository = ClubDeportivoRepository(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = (
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )

        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val etUsuario = findViewById<TextInputEditText>(R.id.title_inputUsuario)
        val etPassword = findViewById<TextInputEditText>(R.id.title_inputPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.title_btnIngresar)

        // Redirigir al registro
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroUsuarioActivity::class.java))
        }

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLogin.setOnClickListener {
            val username = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Complete usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //val passwordHash = sha256(password)
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
    }

    // Función para abrir HomeActivity pasando nombre y rol
    private fun openHome(nombreUsuario: String, rolUsuario: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
        intent.putExtra("ROL_USUARIO", rolUsuario)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Salir")
            setMessage("¿Deseas salir de la aplicación?")
            setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }
            setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    // Función para generar hash SHA-256 de la contraseña
    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
