package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MAAddObra : AppCompatActivity() {
    lateinit var nomeObra : EditText
    lateinit var descricaoObra: EditText

    lateinit var botaoSalvar: Button
    lateinit var botaoExcluir: Button
    lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_obras)

        nomeObra = findViewById(R.id.nomeObraEdit)
        descricaoObra = findViewById(R.id.descricaoObraEdit)
        botaoSalvar = findViewById(R.id.salvarBotao)
        botaoExcluir = findViewById(R.id.excluirBotao)
        fb = Firebase.firestore

    }

    override fun onStart() {
        super.onStart()
        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaExposicoes)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }

        botaoSalvar.setOnClickListener {
            val nome = nomeObra.text.toString()
            val descricao = descricaoObra.text.toString()

            fb.collection("Obra")
                .document(nome)
                .set(mapOf(
                    "nomeObra" to nome,
                    "descricaoObra" to descricao,
                ))
            VoltarTela()
        }

        botaoExcluir.setOnClickListener {
            val nome = nomeObra.text.toString()
            fb.collection("Obra")
                .document(nome)
                .delete()
            VoltarTela()
        }
    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela exposicções do funcionário")
        startActivity(Intent(this, MAExposicaoFuncionario::class.java))
    }
}

