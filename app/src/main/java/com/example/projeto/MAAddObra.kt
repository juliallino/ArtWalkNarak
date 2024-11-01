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

class MAAddObra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_obras)

        //botões
        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaExposicoes)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
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
        Log.d("Voltar", "Voltando para tela exposicções do funcionário")
        startActivity(Intent(this, MAExposicaoFuncionario::class.java))
    }
    private fun Salvar() {
        Log.d("Salvar", "Salvar nova obra")
        Toast.makeText(this, "Obra savla", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MAExposicaoFuncionario::class.java)
        startActivity(intent)
    }

    private fun Excluir() {
        Log.d("Excluir", "Excluir obra")
        Toast.makeText(this, "Obra excluida", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MAExposicaoFuncionario::class.java)
        startActivity(intent)
    }
}