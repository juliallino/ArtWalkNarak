package com.example.projeto.model

data class ExposicaoHome(
    val nomeExposicao: String,
    val imagemExposicao: Int,
//  val imagemExposicao: String por ser url
    val editExposicao: Int,
    val descricao: String
)