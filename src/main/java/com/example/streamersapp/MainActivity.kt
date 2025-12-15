package com.example.streamersapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.streamersapp.controler.Controller
import com.example.streamersapp.databinding.ActivityMainBinding
import com.example.streamersapp.databinding.DialogAddStreamerBinding
import com.example.streamersapp.models.Streamer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var controller: Controller


    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Obtener el NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //
        // streamersFragment
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.streamersFragment)
        )

        // Vinculacion Toolbar con el NavController y AppBarConfiguration
        setupActionBarWithNavController(navController, appBarConfiguration)


        controller = Controller(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // Eventos del menú de opciones
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favoritos -> {
                navController.navigate(R.id.favoritosFragment)
                true
            }
            R.id.menu_ajustes -> {
                navController.navigate(R.id.ajustesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Permitir navegación hacia atrás con el botón de la Toolbar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    fun showAddStreamerDialog() {
        val dialogBinding = DialogAddStreamerBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        // Fondo transparente para que se vea el diseño personalizado
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Configurar botón Cancelar
        dialogBinding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        // Configurar botón Añadir
        dialogBinding.btnAnadir.setOnClickListener {
            val nombre = dialogBinding.etNombre.text.toString().trim()
            val categoria = dialogBinding.etCategoria.text.toString().trim()
            val plataformasText = dialogBinding.etPlataformas.text.toString().trim()
            val urlPerfil = dialogBinding.etUrlPerfil.text.toString().trim()
            val urlFoto = dialogBinding.etUrlFoto.text.toString().trim()

            // Validación básica
            if (nombre.isEmpty()) {
                Toast.makeText(this, " El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoria.isEmpty()) {
                Toast.makeText(this, "La categoría es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir plataformas separadas por coma en lista
            val plataformas = if (plataformasText.isNotEmpty()) {
                plataformasText.split(",").map { it.trim() }
            } else {
                listOf("Twitch") // Por defecto
            }

            // Crear nuevo streamer
            val nuevoId = (controller.lista.maxOfOrNull { it.id } ?: 0) + 1

            // Determinar qué foto usar
            val fotoFinal: Any = if (urlFoto.isNotEmpty()) {
                urlFoto  // URL de internet
            } else {
                R.drawable.ic_launcher_background  // Imagen local por defecto
            }

            val nuevoStreamer = Streamer(
                id = nuevoId,
                nombre = nombre,
                plataformas = plataformas,
                categoria = categoria,
                urlPerfil = urlPerfil.ifEmpty { "https://twitch.tv/$nombre" },
                foto = fotoFinal
            )

            // Añadir a la lista
            controller.addStreamer(nuevoStreamer)

            Toast.makeText(this, "nombre añadido correctamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }


    fun showEditStreamerDialog(position: Int) {
        val streamer = controller.lista[position]
        val dialogBinding = com.example.streamersapp.databinding.DialogEditStreamerBinding.inflate(LayoutInflater.from(this))

        // Pre-rellenar los campos con los datos actuales
        dialogBinding.etNombre.setText(streamer.nombre)
        dialogBinding.etCategoria.setText(streamer.categoria)
        dialogBinding.etPlataformas.setText(streamer.plataformas.joinToString(", "))
        dialogBinding.etUrlPerfil.setText(streamer.urlPerfil)

        // Si la foto es String (URL), mostrarla
        if (streamer.foto is String) {
            dialogBinding.etUrlFoto.setText(streamer.foto as String)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Configurar botón Cancelar
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
                Toast.makeText(this, " La categoría es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir plataformas separadas por coma en lista
            val plataformas = if (plataformasText.isNotEmpty()) {
                plataformasText.split(",").map { it.trim() }
            } else {
                listOf("Twitch")
            }

            // Determinar qué foto usar
            val fotoFinal: Any = when {
                urlFoto.isNotEmpty() -> urlFoto  // Nueva URL
                streamer.foto is String -> streamer.foto  // Mantener URL anterior
                else -> streamer.foto  // Mantener drawable anterior
            }

            // Crear streamer actualizado (mantener el mismo ID)
            val streamerActualizado = Streamer(
                id = streamer.id,
                nombre = nombre,
                plataformas = plataformas,
                categoria = categoria,
                urlPerfil = urlPerfil,
                foto = fotoFinal
            )

            // Actualizar en la lista
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