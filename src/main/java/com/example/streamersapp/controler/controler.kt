package com.example.streamersapp.controler

import android.content.Context
import android.widget.Toast
import com.example.streamersapp.MainActivity
import com.example.streamersapp.adapter.AdapterStreamer
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.objects_models.Repository.listStreamers

class Controller(val context: Context) {
    lateinit var lista: MutableList<Streamer>
    lateinit var adapter: AdapterStreamer

    private fun onViewProfile(position: Int) {
        val streamer = lista[position]
        Toast.makeText(context, "Ver perfil de ${streamer.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun onEdit(position: Int) {
        val myActivity = context as MainActivity
        myActivity.showEditStreamerDialog(position)
    }

    private fun onDelete(position: Int) {
        val myActivity = context as MainActivity
        myActivity.showDeleteConfirmationDialog(position)
    }

    init {
        lista = listStreamers.toMutableList()
        adapter = AdapterStreamer(
            lista,
            { position -> onViewProfile(position) },
            { position -> onEdit(position) },
            { position -> onDelete(position) }
        )
    }


    fun addStreamer(streamer: Streamer) {
        lista.add(streamer)
        adapter.notifyItemInserted(lista.size - 1)
    }


    fun editStreamer(position: Int, streamerActualizado: Streamer) {
        lista[position] = streamerActualizado
        adapter.notifyItemChanged(position)
    }


    fun deleteStreamer(position: Int) {
        lista.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, lista.size) // ⭐ ESTA LÍNEA SOLUCIONA EL PROBLEMA
    }
}