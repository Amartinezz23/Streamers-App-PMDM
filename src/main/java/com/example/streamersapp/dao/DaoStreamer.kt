package com.example.streamersapp.dao

import com.example.streamersapp.interfaces.StreamerInterface
import com.example.streamersapp.models.Streamer
import com.example.streamersapp.objects_models.Repository

class DaoStreamers private constructor() : StreamerInterface {

    companion object {
        val myDao: DaoStreamers by lazy {
            DaoStreamers()
        }
    }


    override fun getAllStreamers(): List<Streamer> = Repository.listStreamers
}