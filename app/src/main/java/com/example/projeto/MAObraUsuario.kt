package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterSobreObra
import com.example.projeto.model.Obra
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MAObraUsuario : AppCompatActivity() {
    lateinit var botaoenviar:Button
    lateinit var chatIA: EditText
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
        val botaoAcessibilidade = findViewById<ImageButton>(R.id.acessibilidadeObra)
        botaoAcessibilidade.setOnClickListener{
            AcessibilidadeSom()
        }

    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial da exposição do usuario")
        startActivity(Intent(this, MAExposicaoUsuario::class.java))
    }

    private fun AcessibilidadeSom(){
        Log.d("botão acessibilidade", "para ativar a leitura de textp")
        Toast.makeText(this, "Acessibilidade ativada", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        chatIA.setOnClickListener{
            chatIA.setText("")
        }
        botaoenviar.setOnClickListener{
            lifecycleScope.launch{
                generateIA()
            }
        }
    }

    suspend fun generateIA(){
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBcZX1D7erqrZK8VYgwwJN_Y_PMoxJMopc")

        val prompt = chatIA.text.toString()
        val response = generativeModel.generateContent(prompt + "responta essa pergunta em até 250 caracteres")
        chatIA.setText(response.text)
    }

}