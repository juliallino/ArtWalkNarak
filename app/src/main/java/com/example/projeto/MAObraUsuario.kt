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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerViewObras = findViewById<RecyclerView>(R.id.sobreObrasRecyclerView)
        recyclerViewObras.layoutManager = LinearLayoutManager(this)
        recyclerViewObras.setHasFixedSize(true)
        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterSobreObra(this, obras)
        recyclerViewObras.adapter = adapterObra

        val batman = Obra(
            1,
            "Batman",
            R.drawable.batman,
            0,
            "Super-Herói de Gotham, seu nome é Bruce Wayne, traumatizado pela morte dos seus pais resolveu sair pelas ruas de Gotham atrás de vingança. Posteriormente reolveu salvar a cidade que seu pai tanto amava. Antes da perda de seus pais, Bruce Wayne era uma criança alegre e feliz, guardando boas memórias do tempo em que viveu na Mansão Wayne. Tendo se tornado introvertido e antissocial na adolescência, Bruce preferia a companhia de livros a pessoas. Ele tinha interesse em garotas, mas não se preocupava em gastar tempo com elas. Apesar das Indústrias Wayne serem capazes de lhe proporcionar grandes aposentos e o melhor conforto possível, \\n\\nBruce mantinha seu quarto na Torre Wayne o mais simples possível, acreditando que a praticidade de achar coisas mais rápido compensava o conforto exagerado que poderia ter com seu dinheiro. Bruce também sempre foi dedicado em sua vida estudantil, tirando as melhores notas possíveis, mas não se preocupando muito com tal resultado―ele de fato estudava por gosto, não pelo resultado"
        )

        obras.add(batman)
        adapterObra.notifyDataSetChanged()

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