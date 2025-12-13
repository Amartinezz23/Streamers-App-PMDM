package com.example.streamersapp.models

class Streamer(
    val id: Int,
    var nombre: String,
    var plataformas: List<String>,   // Ej: listOf("Twitch", "YouTube")
    var categoria: String,
    var urlPerfil: String,
    var foto: Any
) {
    override fun toString(): String {
        return "Streamer: $nombre\n" +
                "Plataformas: ${plataformas.joinToString(", ")}\n" +
                "Categoría: $categoria\n" +
                "Perfil: $urlPerfil\n" +
                "Foto: $foto"
    }
}