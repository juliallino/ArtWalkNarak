package com.example.projeto.model

data class Exposicao(
    val idExposicao: Int,
    val nomeExposicao: String,
    //  val imagemExposicao: String por ser url
    val imagemExposicao: Int,
    val editExposicao: Int,
    val descricaoExposicao: String
)
