package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeFunc
import com.example.projeto.model.Exposicao
import com.google.firebase.auth.FirebaseAuth

class MAHomeFuncionario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnSair : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_home)

        btnSair = findViewById(R.id.botaoSair)
        auth = FirebaseAuth.getInstance()

        val recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesFuncionarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicaoHomeFunc(this, listaExposicoes)
        recyclerViewExposicoes.adapter = adapterExposicao


        // Botão para adicionar exposição
        val botaoAddExposicao = findViewById<ImageButton>(R.id.botaoaddexpo)
        botaoAddExposicao.setOnClickListener {
            AddExposicao()
        }
        btnSair.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MALoginUsuario::class.java))
        }
    }

    private fun AddExposicao() {
        Log.d("ADD", "Tela add Exposição")
        val intent = Intent(this, MAAddExposicao::class.java)
        startActivity(intent)
    }

}
