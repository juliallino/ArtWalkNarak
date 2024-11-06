package com.example.projeto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeUsu
import com.example.projeto.model.Exposicao

class MAHomeUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_home)


        val recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesUsuarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicaoHomeUsu(this, listaExposicoes)
        recyclerViewExposicoes.adapter = adapterExposicao

        // Exemplo de criação de uma nova Exposição
        val centelhasEmMovimento = Exposicao(1, "CENTELHAS EM MOVIMENTO", R.drawable.centelhas, R.drawable.edit, "Descrição da exposição.", true)
        listaExposicoes.add(centelhasEmMovimento)

    }

}
