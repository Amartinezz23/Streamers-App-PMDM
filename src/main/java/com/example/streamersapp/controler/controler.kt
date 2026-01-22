package com.example.streamersapp.controler

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.streamersapp.MainActivity
import com.example.streamersapp.R
import com.example.streamersapp.adapter.AdapterStreamer
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.objects_models.Repository.listStreamers
import com.example.streamersapp.StreamersFragmentDirections


class Controller(private val context: Context) {

    val lista: MutableList<Streamer> = listStreamers.toMutableList()

    val adapter: AdapterStreamer = AdapterStreamer(
        lista,
        { position -> onViewProfile(position) },
        { position -> onEdit(position) },
        { position -> onDelete(position) }
    )


    private fun onViewProfile(position: Int) {
        val streamer = lista[position]
        val activity = context as MainActivity

        val action = StreamersFragmentDirections.actionStreamersFragmentToDetailFragment(streamer.id)
        activity.findNavController(R.id.nav_host_fragment).navigate(action)
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