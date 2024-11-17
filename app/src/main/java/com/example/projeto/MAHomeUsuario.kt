package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeUsu
import com.example.projeto.model.Exposicao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MAHomeUsuario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnSair : Button
    private lateinit var recyclerViewExposicoes: RecyclerView
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_home)
        auth = FirebaseAuth.getInstance()
        btnSair = findViewById(R.id.botaoSair)

        recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesUsuarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes: MutableList<Exposicao> = mutableListOf()


        db = FirebaseFirestore.getInstance()
        db.collection("Exposicao")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val exposicao: Exposicao? = data.toObject(Exposicao::class.java)
                        exposicao?.idExposicao = data.id
                        if(exposicao != null){
                            listaExposicoes.add(exposicao)
                        }
                    }
                    val adapterExposicao = AdapterExposicaoHomeUsu(this, listaExposicoes)
                    recyclerViewExposicoes.adapter = adapterExposicao
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }

    }
    override fun onStart() {
        super.onStart()
        btnSair.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MALoginUsuario::class.java))
        }
    }

}
