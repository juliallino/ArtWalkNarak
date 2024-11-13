package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterExposicaoHomeFunc
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MAHomeFuncionario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnSair : Button
    private lateinit var recyclerViewExposicoes: RecyclerView
    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_home)

        btnSair = findViewById(R.id.botaoSair)
        auth = FirebaseAuth.getInstance()

        recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoesFuncionarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

        val listaExposicoes:MutableList<Exposicao> = mutableListOf()

        db = FirebaseFirestore.getInstance()
        db.collection("Exposicao")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val exposicao: Exposicao? = data.toObject(Exposicao::class.java)
                        if(exposicao != null){
                            listaExposicoes.add(exposicao)
                        }
                    }
                    val adapterExposicao = AdapterExposicaoHomeFunc(this, listaExposicoes)
                    recyclerViewExposicoes.adapter = adapterExposicao
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }



        // Botão para adicionar exposição
        val botaoAddExposicao = findViewById<ImageButton>(R.id.botaoaddexpo)
        botaoAddExposicao.setOnClickListener {
            AddExposicao()
        }
    }

    override fun onStart() {
        super.onStart()
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
