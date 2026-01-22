package com.example.streamersapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.streamersapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding


    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)


        val streamerId = args.streamerId


        val activity = requireActivity() as MainActivity
        val streamer = activity.controller.lista.find { it.id == streamerId }

        streamer?.let {

            binding.tvDetailNombre.text = it.nombre
            binding.tvDetailCategoria.text = "Categoría: ${it.categoria}"
            binding.tvDetailPlataformas.text = "Plataformas: ${it.plataformas.joinToString(", ")}"
            binding.tvDetailUrl.text = it.urlPerfil


            when (it.foto) {
                is Int -> {
                    Glide.with(requireContext())
                        .load(it.foto as Int)
                        .circleCrop()
                        .into(binding.imgDetailFoto)
                }
                is String -> {
                    Glide.with(requireContext())
                        .load(it.foto as String)
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.imgDetailFoto)
                }
            }


            mostrarPlataformas(it.plataformas)
        }
    }

    private fun mostrarPlataformas(plataformas: List<String>) {
        binding.iconDetailTwitch.visibility = View.GONE
        binding.iconDetailYoutube.visibility = View.GONE
        binding.iconDetailKick.visibility = View.GONE

        plataformas.forEach { plataforma ->
            when (plataforma.lowercase()) {
                "twitch" -> {
                    binding.iconDetailTwitch.visibility = View.VISIBLE
                    binding.iconDetailTwitch.setImageResource(R.drawable.twitch)
                }
                "youtube" -> {
                    binding.iconDetailYoutube.visibility = View.VISIBLE
                    binding.iconDetailYoutube.setImageResource(R.drawable.youtube)
                }
                "kick" -> {
                    binding.iconDetailKick.visibility = View.VISIBLE
                    binding.iconDetailKick.setImageResource(R.drawable.kick)
                }
            }
        }
    }
}