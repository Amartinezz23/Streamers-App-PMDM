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
        binding.txtNombre.text = streamer.nombre
        binding.txtCategoria.text = streamer.categoria

        Glide.with(itemView.context)
            .load(streamer.foto)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgStreamer)

        // ⚡ Usar siempre adapterPosition dentro del click
        binding.btnPerfil.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onViewProfile(adapterPosition)
        }

        binding.btnEditar.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onEdit(adapterPosition)
        }

        binding.btnBorrar.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                onDelete(adapterPosition)
        }
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