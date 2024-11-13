package com.example.projeto.model

data class Obra(
    val idObra: String? = null,
    val nomeObra: String? = null,
    val imagemObra: String? = null,
    val descricaoObra: String? = null,
){
    constructor() : this(null, null, null, null)
}