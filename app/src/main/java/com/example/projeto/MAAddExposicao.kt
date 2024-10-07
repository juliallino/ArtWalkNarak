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

class MAAddExposicao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_exposicao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }
        val emAndamentoBotao = findViewById<Button>(R.id.EmAndamentoBotao)
        emAndamentoBotao.setOnClickListener {
            EmAndamento()
        }
        val encerradaBotao = findViewById<Button>(R.id.EncerradaBotao)
        encerradaBotao.setOnClickListener {
            Encerrada()
        }

        val salvarBotao = findViewById<Button>(R.id.salvarBotao)
        salvarBotao.setOnClickListener {
            Salvar()
        }
        val excluirBotao = findViewById<Button>(R.id.excluirBotao)
        excluirBotao.setOnClickListener {
            Excluir()
        }


    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }

    private fun Salvar() {
        Log.d("Salvar", "Voltando para tela inicial do funcionário")
        Toast.makeText(this, "Exposição savla", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }

    private fun Excluir() {
        Log.d("Excluir", "Voltando para tela inicial do funcionário")
        Toast.makeText(this, "Exposição excluida", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }

    private fun EmAndamento() {
        Log.d("Em andamento botão", "botao de status da exposição")
        Toast.makeText(this, "Em andamento", Toast.LENGTH_SHORT).show()

    }

    private fun Encerrada() {
        Log.d("Encerrada botão", "botao de status da exposição")
        Toast.makeText(this, "Encerrada", Toast.LENGTH_SHORT).show()

    }

}