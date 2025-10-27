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

        // Obtener datos del usuario pasados desde LoginActivity
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val rolUsuario = intent.getStringExtra("ROL_USUARIO") ?: "Invitado"

        // Mostrar nombre en el saludo
        val tvSaludo = findViewById<TextView>(R.id.title_saludo_menu)
        tvSaludo.text = "Bienvenido/a $nombreUsuario!!"

        // Mostrar nombre en el header
        val tvUser = findViewById<TextView>(R.id.tvUsuario)
        tvUser.text = nombreUsuario

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

        // Listeners de botones (puedes cambiar para abrir tus pantallas)
        btnClientes.setOnClickListener { /* Abrir pantalla clientes */
            val intent = Intent(this, ClienteActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnPersonal.setOnClickListener { /* Abrir pantalla personal */
            val intent = Intent(this, PersonalActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnActividades.setOnClickListener { /* Abrir pantalla actividades */
            val intent = Intent(this, ActividadesActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnTurnos.setOnClickListener { /* Abrir pantalla turnos */
            val intent = Intent(this, TurnosActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ROL_USUARIO", rolUsuario)
            startActivity(intent)
        }
        btnAcercaDe.setOnClickListener { /* Abrir pantalla acerca de */
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
                setPositiveButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                create()
                show()
            }
        }
    }
}
