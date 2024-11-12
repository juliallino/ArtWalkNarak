package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterObraFunc
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra

class MAExposicaoFuncionario : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_exposicao)

        // RecyclerView para exposições
        val recyclerViewSobreExposicoes = findViewById<RecyclerView>(R.id.sobreExposicaoRecyclerView)
        recyclerViewSobreExposicoes.layoutManager = LinearLayoutManager(this)
        recyclerViewSobreExposicoes.setHasFixedSize(true)
        // Configurar adapter para exposições
        val sobreExposicaoLista: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicao(this, sobreExposicaoLista)
        recyclerViewSobreExposicoes.adapter = adapterExposicao

        // RecyclerView para obras
        val recyclerViewObras = findViewById<RecyclerView>(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 5)
        recyclerViewObras.setHasFixedSize(true)
        // Configurar adapter para obras
        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterObraFunc(this, obras)
        recyclerViewObras.adapter = adapterObra



        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoAddObra = findViewById<ImageButton>(R.id.addObra)
        botaoAddObra.setOnClickListener{
            AddObra()
        }

    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        startActivity(Intent(this, MAHomeFuncionario::class.java))
    }

    private fun AddObra() {
        Log.d("ADD", "tela add obra")
        startActivity(Intent(this, MAAddObra::class.java))
    }

}
