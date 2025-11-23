package com.example.streamersapp.objects_models

import com.example.streamersapp.R
import com.example.streamersapp.models.Streamer



object Repository {
    val listStreamers: List<Streamer> = listOf(
        Streamer(1, "Ibai", listOf("Twitch", "YouTube"), "Entretenimiento", "https://www.twitch.tv/ibai", 1),
        Streamer(2, "AuronPlay", listOf("Twitch", "YouTube"), "Variedades", "https://www.twitch.tv/auronplay", 2),
        Streamer(3, "ElXokas", listOf("Twitch"), "Gaming", "https://www.twitch.tv/elxokas", R.drawable.elxokas),
        Streamer(4, "TheGrefg", listOf("Twitch", "YouTube"), "Gaming", "https://www.twitch.tv/thegrefg", 4),
        Streamer(5, "Rubius", listOf("YouTube"), "Gaming", "https://www.youtube.com/rubius", 5),
        Streamer(6, "Willyrex", listOf("YouTube"), "Gaming", "https://www.youtube.com/willyrex", 6),
        Streamer(7, "Vegetta777", listOf("YouTube"), "Gaming", "https://www.youtube.com/vegetta777", 7),
        Streamer(8, "Jordi Wild", listOf("Youtube"), "Variedades", "https://www.youtube.com/@ElRinconDeGiorgio", 8),
        Streamer(9, "Antonio Gutierrez", listOf("Twitch", "Youtube"), "Entretenimiento", "https://www.youtube.com/@antoniogutierrezz", 9),
        Streamer(10, "Peereira7", listOf("Twitch", "YouTube"), "FPS", "https://www.youtube.com/@Peereira7", 9),
        Streamer(11, "DjMaRiiO", listOf("Twitch", "Youtube"), "Esports", "https://www.youtube.com/@DjMaRiiO", 10),
        Streamer(12, "IlloJuan", listOf("YouTube", "Twitch"), "Variedades", "https://www.youtube.com/@IlloJuan_", 11))

}