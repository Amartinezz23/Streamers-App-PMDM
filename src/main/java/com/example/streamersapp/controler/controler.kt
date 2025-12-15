package com.example.streamersapp.controler

import android.content.Context
import android.widget.Toast
import com.example.streamersapp.MainActivity
import com.example.streamersapp.adapter.AdapterStreamer
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.objects_models.Repository.listStreamers

class Controller(private val context: Context) {

    val lista: MutableList<Streamer> = listStreamers.toMutableList()

    val adapter: AdapterStreamer = AdapterStreamer(
        lista,
        { position -> onViewProfile(position) },
        { position -> onEdit(position) },
        { position -> onDelete(position) }
    )

    private fun onViewProfile(position: Int) {
        Toast.makeText(
            context,
            "Ver perfil de ${lista[position].nombre}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onEdit(position: Int) {
        (context as MainActivity).showEditStreamerDialog(position)
    }

    private fun onDelete(position: Int) {
        (context as MainActivity).showDeleteConfirmationDialog(position)
    }

    fun addStreamer(streamer: Streamer) {
        val position = lista.size
        lista.add(streamer)
        adapter.notifyItemInserted(position)
    }

    fun editStreamer(position: Int, streamerActualizado: Streamer) {
        lista[position] = streamerActualizado
        adapter.notifyItemChanged(position)
    }

    fun deleteStreamer(position: Int) {
        lista.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}
