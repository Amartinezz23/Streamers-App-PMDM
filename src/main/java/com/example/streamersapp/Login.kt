package com.example.streamersapp



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.streamersapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    private val VALID_USERNAME = "admin"
    private val VALID_PASSWORD = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validateLogin(username, password)) {

                val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                prefs.edit().putString("username", username).apply()


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            Toast.makeText(this, "Función de registro - Próximamente", Toast.LENGTH_LONG).show()

        }

        binding.btnForgotPassword.setOnClickListener {
            Toast.makeText(this, "Recuperación de contraseña - Próximamente", Toast.LENGTH_LONG).show()

        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        return username == VALID_USERNAME && password == VALID_PASSWORD
    }
}