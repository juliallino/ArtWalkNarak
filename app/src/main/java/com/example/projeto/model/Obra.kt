package com.example.projeto.model

data class Obra(
    val idObra: Int,
    val nomeObra: String,
    //  val imagemObra: String por ser url
    val imagemObra: Int,
    val editObra: Int,
    val descricao: String
)