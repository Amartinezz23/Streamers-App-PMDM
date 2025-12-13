package com.example.streamersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.R

// Adapter para RecyclerView de Streamers
class AdapterStreamer(var listStreamers: MutableList<Streamer>,
                      val onViewProfile: (position: Int) -> Unit,
                      val onEdit: (position: Int) -> Unit,
                      val onDelete: (position: Int) -> Unit) :
    RecyclerView.Adapter<ViewHStreamer>() {

    /*
    Método que crea la view del ViewHolderStreamer
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHStreamer {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemStreamer = R.layout.item_streamer
        return ViewHStreamer(
            layoutInflater.inflate(layoutItemStreamer, parent, false),
            onViewProfile,
            onEdit,
            onDelete,
        )
    }

    /*
    Método que renderiza los datos de cada streamer en la view.
    */
    override fun onBindViewHolder(holder: ViewHStreamer, position: Int) {
        val streamer = listStreamers[position]




        holder.renderize(streamer)
    }

    /*
    Método que devuelve el número de objetos a representar en el RecyclerView.
    */
    override fun getItemCount(): Int = listStreamers.size
}