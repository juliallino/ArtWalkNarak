package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeUsu
import com.example.projeto.model.Exposicao
import com.google.firebase.auth.FirebaseAuth

class MAHomeUsuario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnSair : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_home)
        auth = FirebaseAuth.getInstance()

        btnSair = findViewById(R.id.botaoSair)

        val recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesUsuarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicaoHomeUsu(this, listaExposicoes)
        recyclerViewExposicoes.adapter = adapterExposicao


    }

    override fun onStart() {
        super.onStart()
        btnSair.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MALoginUsuario::class.java))
        }
    }

}
