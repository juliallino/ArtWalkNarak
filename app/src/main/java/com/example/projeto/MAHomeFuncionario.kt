package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeFunc
import com.example.projeto.model.Exposicao

class MAHomeFuncionario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_home)


        val recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesFuncionarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicaoHomeFunc(this, listaExposicoes)
        recyclerViewExposicoes.adapter = adapterExposicao

        // Exemplo de criação de uma nova Exposição
        val centelhasEmMovimento = Exposicao(1, "CENTELHAS EM MOVIMENTO", R.drawable.centelhas, R.drawable.baseline_edit_24, "Descrição da exposição.")
        listaExposicoes.add(centelhasEmMovimento)

        // Botão para adicionar exposição
        val botaoAddExposicao = findViewById<ImageButton>(R.id.botaoaddexpo)
        botaoAddExposicao.setOnClickListener {
            AddExposicao()
        }

    }

    private fun AddExposicao() {
        Log.d("ADD", "Tela add Exposição")
        val intent = Intent(this, MAAddExposicao::class.java)
        startActivity(intent)
    }

}
