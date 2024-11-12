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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterObraUsu
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class MAExposicaoUsuario : AppCompatActivity() {
    lateinit var botaoenviar:Button
    lateinit var chatIA:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_exposicao)
        botaoenviar = findViewById(R.id.enviar)
        chatIA = findViewById(R.id.chatIA)

        // RecyclerView para EXPOSIÇÕES
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
        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterObraUsu(this, obras)
        recyclerViewObras.adapter = adapterObra


        // Atualiza a lista para o adapter
        adapterObra.notifyDataSetChanged()


        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoScanObra =  findViewById<FloatingActionButton>(R.id.scanObra)
        botaoScanObra.setOnClickListener {
            ScanObra()
        }
        val botaoAcessibilidade = findViewById<ImageButton>(R.id.acessibilidadeExposicao)
        botaoAcessibilidade.setOnClickListener{
            AcessibilidadeSom()
        }

    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do usuario")
        startActivity(Intent(this, MAHomeUsuario::class.java))
    }

    private fun ScanObra() {
        Log.d("Scan obra", "Indo para tela de scan")
        startActivity(Intent(this, MAQRCodePage::class.java))
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

