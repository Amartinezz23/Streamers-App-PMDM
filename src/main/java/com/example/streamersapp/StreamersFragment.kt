package com.example.streamersapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streamersapp.databinding.FragmentStreamersBinding

class StreamersFragment : Fragment(R.layout.fragment_streamers) {

    private lateinit var binding: FragmentStreamersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStreamersBinding.bind(view)

        // Referencia a la Activity
        val activity = requireActivity() as MainActivity

        // RecyclerView
        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = activity.controller.adapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }

        // Botón flotante para añadir streamer
        binding.btnAdd.setOnClickListener {
            activity.showAddStreamerDialog()
        }
    }
}
