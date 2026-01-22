package com.example.streamersapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.streamersapp.controler.Controller
import com.example.streamersapp.databinding.ActivityMainBinding
import com.example.streamersapp.databinding.DialogAddStreamerBinding
import com.example.streamersapp.models.Streamer
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var controller: Controller

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)


        drawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.streamersFragment,
                R.id.favoritosFragment,
                R.id.ajustesFragment,
                R.id.ejemploFragment1,
                R.id.ejemploFragment2
            ),
            drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)


        navigationView.setupWithNavController(navController)


        binding.bottomNav?.setupWithNavController(navController)


        controller = Controller(this)


        setupDrawerHeader(navigationView)


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    performLogout()
                    true
                }
                else -> {
                    menuItem.isChecked = true
                    drawerLayout.closeDrawers()
                    navController.navigate(menuItem.itemId)
                    true
                }
            }
        }
    }

    private fun setupDrawerHeader(navigationView: NavigationView) {
        val headerView = navigationView.getHeaderView(0)
        val tvUsername = headerView.findViewById<TextView>(R.id.tvHeaderUsername)


        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", "Usuario")

        tvUsername.text = username
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(this, "Buscador - Próximamente", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_filter -> {
                Toast.makeText(this, "Filtros - Próximamente", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_logout -> {
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performLogout() {
        // Limpiar SharedPreferences
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        prefs.edit().clear().apply()

        // Volver a LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun showAddStreamerDialog() {
        val dialogBinding = DialogAddStreamerBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnAnadir.setOnClickListener {
            val nombre = dialogBinding.etNombre.text.toString().trim()
            val categoria = dialogBinding.etCategoria.text.toString().trim()
            val plataformasText = dialogBinding.etPlataformas.text.toString().trim()
            val urlPerfil = dialogBinding.etUrlPerfil.text.toString().trim()
            val urlFoto = dialogBinding.etUrlFoto.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoria.isEmpty()) {
                Toast.makeText(this, "La categoría es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val plataformas = if (plataformasText.isNotEmpty()) {
                plataformasText.split(",").map { it.trim() }
            } else {
                listOf("Twitch")
            }

            val nuevoId = (controller.lista.maxOfOrNull { it.id } ?: 0) + 1

            val fotoFinal: Any = if (urlFoto.isNotEmpty()) {
                urlFoto
            } else {
                R.drawable.ic_launcher_background
            }

            val nuevoStreamer = Streamer(
                id = nuevoId,
                nombre = nombre,
                plataformas = plataformas,
                categoria = categoria,
                urlPerfil = urlPerfil.ifEmpty { "https://twitch.tv/$nombre" },
                foto = fotoFinal
            )

            controller.addStreamer(nuevoStreamer)
            Toast.makeText(this, "$nombre añadido correctamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showEditStreamerDialog(position: Int) {
        val streamer = controller.lista[position]
        val dialogBinding = com.example.streamersapp.databinding.DialogEditStreamerBinding.inflate(LayoutInflater.from(this))

        dialogBinding.etNombre.setText(streamer.nombre)
        dialogBinding.etCategoria.setText(streamer.categoria)
        dialogBinding.etPlataformas.setText(streamer.plataformas.joinToString(", "))
        dialogBinding.etUrlPerfil.setText(streamer.urlPerfil)

        if (streamer.foto is String) {
            dialogBinding.etUrlFoto.setText(streamer.foto as String)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnGuardar.setOnClickListener {
            val nombre = dialogBinding.etNombre.text.toString().trim()
            val categoria = dialogBinding.etCategoria.text.toString().trim()
            val plataformasText = dialogBinding.etPlataformas.text.toString().trim()
            val urlPerfil = dialogBinding.etUrlPerfil.text.toString().trim()
            val urlFoto = dialogBinding.etUrlFoto.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoria.isEmpty()) {
                Toast.makeText(this, "La categoría es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val plataformas = if (plataformasText.isNotEmpty()) {
                plataformasText.split(",").map { it.trim() }
            } else {
                listOf("Twitch")
            }

            val fotoFinal: Any = when {
                urlFoto.isNotEmpty() -> urlFoto
                streamer.foto is String -> streamer.foto
                else -> streamer.foto
            }

            val streamerActualizado = Streamer(
                id = streamer.id,
                nombre = nombre,
                plataformas = plataformas,
                categoria = categoria,
                urlPerfil = urlPerfil,
                foto = fotoFinal
            )

            controller.editStreamer(position, streamerActualizado)
            Toast.makeText(this, "$nombre actualizado correctamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showDeleteConfirmationDialog(position: Int) {
        val streamer = controller.lista[position]
        val dialogBinding = com.example.streamersapp.databinding.DialogDeleteConfirmationBinding.inflate(LayoutInflater.from(this))

        dialogBinding.tvMessage.text = "¿Deseas borrar a ${streamer.nombre}?"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSi.setOnClickListener {
            controller.deleteStreamer(position)
            Toast.makeText(this, "${streamer.nombre} eliminado", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}