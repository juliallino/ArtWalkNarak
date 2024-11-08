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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MAAddExposicao : AppCompatActivity() {

    lateinit var nomeExpo :EditText
    lateinit var descricaoExposicao: EditText
    lateinit var botaoEmAndamento: Button
    lateinit var botaoEncerrada: Button
    lateinit var botaoSalvar: Button
    lateinit var botaoExcluir: Button
    lateinit var fb: FirebaseFirestore
    var status: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_exposicao)

        nomeExpo = findViewById(R.id.nomeExposicaoEdit)
        descricaoExposicao = findViewById(R.id.descricaoExposicaoEdit)
        botaoEmAndamento = findViewById(R.id.emAndamentoBotao)
        botaoEncerrada = findViewById(R.id.encerradaBotao)
        botaoSalvar = findViewById(R.id.salvarBotao)
        botaoExcluir = findViewById(R.id.excluirBotao)
        fb = Firebase.firestore

    }

    override fun onStart() {
        super.onStart()

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }

        botaoEmAndamento.setOnClickListener {
            status = true
            Toast.makeText(this, "EM ANDAMENTO", Toast.LENGTH_SHORT).show()
        }
        botaoEncerrada.setOnClickListener {
            status = false
            Toast.makeText(this, "ENCERRADA", Toast.LENGTH_SHORT).show()
        }

       botaoSalvar.setOnClickListener {
            val nome = nomeExpo.text.toString()
            val descricao = descricaoExposicao.text.toString()

           fb.collection("Exposicao")
                .add(mapOf(
                    "nomeExposicao" to nome,
                    "descricaoExposicao" to descricao,
                    "status" to status
                ))
           VoltarTela()
        }

        botaoExcluir.setOnClickListener {
            val nome = nomeExpo.text.toString()
            fb.collection("Exposicao")
                .document(nome)
                .delete()
            VoltarTela()
        }
    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }
}