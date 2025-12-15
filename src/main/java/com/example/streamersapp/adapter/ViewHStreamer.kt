package com.example.streamersapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.databinding.ItemStreamerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.streamersapp.R

class ViewHStreamer(
    view: View,
    val onViewProfile: (Int) -> Unit,
    val onEdit: (Int) -> Unit,
    val onDelete: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    val binding: ItemStreamerBinding = ItemStreamerBinding.bind(view)

    fun renderize(streamer: Streamer) {
        binding.txtNombre.text = streamer.nombre
        binding.txtCategoria.text = streamer.categoria


        when (streamer.foto) {
            is Int -> {

                Glide.with(itemView.context)
                    .load(streamer.foto as Int)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // No cachear en disco
                    .skipMemoryCache(true) // No cachear en memoria
                    .signature(com.bumptech.glide.signature.ObjectKey(streamer.id.toString())) // Signature única
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgStreamer)
            }
            is String -> {

                Glide.with(itemView.context)
                    .load(streamer.foto as String)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .signature(com.bumptech.glide.signature.ObjectKey(streamer.id.toString()))
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgStreamer)
            }
            else -> {

                binding.imgStreamer.setImageResource(R.drawable.ic_launcher_background)
            }
        }


        mostrarPlataformas(streamer.plataformas)


        binding.btnPerfil.setOnClickListener(null)
        binding.btnEditar.setOnClickListener(null)
        binding.btnBorrar.setOnClickListener(null)


        binding.btnPerfil.setOnClickListener {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onViewProfile(pos)
            }
        }

        binding.btnEditar.setOnClickListener {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onEdit(pos)
            }
        }

        binding.btnBorrar.setOnClickListener {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                it.isEnabled = false
                onDelete(pos)
                it.postDelayed({ it.isEnabled = true }, 500)
            }
        }
    }


    private fun mostrarPlataformas(plataformas: List<String>) {
        // Ocultar todos por defecto
        binding.iconTwitch.visibility = View.GONE
        binding.iconYoutube.visibility = View.GONE
        binding.iconKick.visibility = View.GONE

        // Mostrar según las plataformas
        plataformas.forEach { plataforma ->
            when (plataforma.lowercase()) {
                "twitch" -> {
                    binding.iconTwitch.visibility = View.VISIBLE
                    binding.iconTwitch.setImageResource(R.drawable.twitch)
                }
                "youtube" -> {
                    binding.iconYoutube.visibility = View.VISIBLE
                    binding.iconYoutube.setImageResource(R.drawable.youtube)
                }
                "kick" -> {
                    binding.iconKick.visibility = View.VISIBLE
                    binding.iconKick.setImageResource(R.drawable.kick)
                }
            }
        }
    }
}