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


        val activity = requireActivity() as MainActivity


        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = activity.controller.adapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }


        binding.btnAdd.setOnClickListener {
            activity.showAddStreamerDialog()
        }
    }
}
