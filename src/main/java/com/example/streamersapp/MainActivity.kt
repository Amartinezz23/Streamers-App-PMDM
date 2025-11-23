package com.example.streamersapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streamersapp.controler.Controller
import com.example.streamersapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var controller: Controller
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos todo
        init()
    }

    private fun init() {
        // Inicializamos RecyclerView
        initRecyclerView()

        // Creamos el Controller
        controller = Controller(this)

        // Configuramos el Adapter
        controller.setAdapter()

        // Si quieres, mostrar datos en log
        // controller.loggOut()
    }

    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        // Puedes agregar animaciones o decoradores si quieres
    }
}