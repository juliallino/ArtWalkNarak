package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterSobreObra
import com.example.projeto.model.Obra

class MAObraUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_obra)


        val recyclerViewObras = findViewById<RecyclerView>(R.id.sobreObrasRecyclerView)
        recyclerViewObras.layoutManager = LinearLayoutManager(this)
        recyclerViewObras.setHasFixedSize(true)
        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterSobreObra(this, obras)
        recyclerViewObras.adapter = adapterObra



        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoEnviar = findViewById<Button>(R.id.enivar)
        botaoEnviar.setOnClickListener{
            EnviarPergunta()
        }
        val botaoAcessibilidade = findViewById<ImageButton>(R.id.acessibilidadeObra)
        botaoAcessibilidade.setOnClickListener{
            AcessibilidadeSom()
        }

    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial da exposição do usuario")
        startActivity(Intent(this, MAExposicaoUsuario::class.java))
    }
    private fun EnviarPergunta(){
        Log.d("botão enviar", "para enviar texto ao gemini")
        Toast.makeText(this, "Pergunta enviada", Toast.LENGTH_SHORT).show()
    }
    private fun AcessibilidadeSom(){
        Log.d("botão acessibilidade", "para ativar a leitura de textp")
        Toast.makeText(this, "Acessibilidade ativada", Toast.LENGTH_SHORT).show()
    }
}