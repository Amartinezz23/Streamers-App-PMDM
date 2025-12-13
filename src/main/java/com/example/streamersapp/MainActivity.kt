package com.example.streamersapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.streamersapp.controler.Controller
import com.example.streamersapp.databinding.ActivityMainBinding
import com.example.streamersapp.databinding.DialogAddStreamerBinding
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.databinding.DialogDeleteConfirmationBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos el controlador
        controller = Controller(this)

        // ✅ Configurar el RecyclerView
        binding.myRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.myRecyclerView.adapter = controller.adapter

        // Configurar botón flotante para añadir
        binding.btnAdd.setOnClickListener {
            showAddStreamerDialog()
        }
    }

    /**
     * Muestra un diálogo para añadir un nuevo streamer
     */
    private fun showAddStreamerDialog() {
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
                Toast.makeText(this, "⚠️ El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoria.isEmpty()) {
                Toast.makeText(this, "⚠️ La categoría es obligatoria", Toast.LENGTH_SHORT).show()
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

            Toast.makeText(this, "✅ $nombre añadido correctamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Muestra un diálogo para editar un streamer existente
     */
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

        // Configurar botón Guardar
        dialogBinding.btnGuardar.setOnClickListener {
            val nombre = dialogBinding.etNombre.text.toString().trim()
            val categoria = dialogBinding.etCategoria.text.toString().trim()
            val plataformasText = dialogBinding.etPlataformas.text.toString().trim()
            val urlPerfil = dialogBinding.etUrlPerfil.text.toString().trim()
            val urlFoto = dialogBinding.etUrlFoto.text.toString().trim()

            // Validación básica
            if (nombre.isEmpty()) {
                Toast.makeText(this, "⚠️ El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoria.isEmpty()) {
                Toast.makeText(this, "⚠️ La categoría es obligatoria", Toast.LENGTH_SHORT).show()
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

            Toast.makeText(this, "✅ $nombre actualizado correctamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Muestra un diálogo de confirmación para eliminar un streamer
     */
    fun showDeleteConfirmationDialog(position: Int) {
        val streamer = controller.lista[position]
        val dialogBinding = com.example.streamersapp.databinding.DialogDeleteConfirmationBinding.inflate(LayoutInflater.from(this))

        // Personalizar el mensaje
        dialogBinding.tvMessage.text = "¿Deseas borrar a ${streamer.nombre}?"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Botón NO
        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        // Botón SÍ
        dialogBinding.btnSi.setOnClickListener {
            controller.deleteStreamer(position)
            Toast.makeText(this, "🗑️ ${streamer.nombre} eliminado", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}