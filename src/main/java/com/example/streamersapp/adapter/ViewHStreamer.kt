package com.example.streamersapp.adapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.databinding.ItemStreamerBinding
import com.bumptech.glide.Glide
import com.example.streamersapp.R



class ViewHStreamer(view: View,  val onViewProfile: (Int) -> Unit,
                    val onEdit: (Int) -> Unit,
                    val onDelete: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    val binding: ItemStreamerBinding = ItemStreamerBinding.bind(view)

    fun renderize(streamer: Streamer) {
        // Nombre y categoría
        binding.txtNombre.text = streamer.nombre
        binding.txtCategoria.text = streamer.categoria

        // Imagen del streamer (puede ser drawable o URL)
        Glide.with(itemView.context)
            .load(streamer.foto) // URL o drawable local
            .circleCrop()              // <-- hace la imagen redonda
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgStreamer)


        binding.iconTwitch.visibility =
            if ("Twitch" in streamer.plataformas){
                View.VISIBLE
            } else
                View.GONE
        binding.iconYoutube.visibility =
            if ("YouTube" in streamer.plataformas){
                View.VISIBLE
            } else
                View.GONE
        binding.iconKick.visibility =
            if ("Kick" in streamer.plataformas){
                View.VISIBLE
            } else
                View.GONE

        setOnClickListener(adapterPosition)
    }

    private fun setOnClickListener(position: Int) {
        binding.btnPerfil.setOnClickListener {
            onViewProfile(position)
        }
        binding.btnEditar.setOnClickListener {
            onEdit(position)
        }
        binding.btnBorrar.setOnClickListener {
            onDelete(position)
        }
    }
}