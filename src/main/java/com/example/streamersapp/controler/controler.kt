package com.example.streamersapp.controler

import android.content.Context
import android.widget.Toast
import com.example.streamersapp.MainActivity
import com.example.streamersapp.adapter.AdapterStreamer
import com.example.streamersapp.dao.DaoStreamers
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.objects_models.Repository.listStreamers

class Controller (val context: Context){
    lateinit var lista: MutableList<Streamer> // lista de objetos
    lateinit var adapter: AdapterStreamer

    private fun onViewProfile(position: Int) {
        val streamer = lista[position]
        Toast.makeText(context, "Veo el streamer ${streamer.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun onEdit(position: Int) {
        val streamer = lista[position]
        Toast.makeText(context, "Edito ${streamer.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun onDelete(position: Int) {
        val streamer = lista[position]
        Toast.makeText(context, "Elimino ${streamer.nombre}", Toast.LENGTH_SHORT).show()

    }

    init {
        println("Lanzamos el ejemplo")
        lista =  listStreamers.toMutableList()
        adapter = AdapterStreamer(lista,
            {
                    position-> println("Veo el streamer ${lista[position]}")
            },
            {
                    position-> println("Edito el streamer ${lista[position]}")
            },{
                    position-> println("Elimino el streamer ${lista[position]}")
            }

        )
    }

    // Configura el RecyclerView con el AdapterStreamer
    fun setAdapter() {
        // Creamos el adapter y lo guardamos en una propiedad
        val adapterStreamer = AdapterStreamer(
            lista,{
                    position -> onViewProfile(position)
            },{
                    position -> onEdit(position)
            },{
                    position -> onDelete(position)

            }

        )

        // Asignamos el adapter al RecyclerView de la activity
        val myActivity = context as MainActivity
        myActivity.binding.myRecyclerView.adapter = adapterStreamer
    }
}
