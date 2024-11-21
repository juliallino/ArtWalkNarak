package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicaoHomeFunc
import com.example.projeto.model.Exposicao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MAHomeFuncionario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnSair : Button
    private lateinit var busca: SearchView
    private val listaExposicoes:  MutableList<Exposicao> = mutableListOf()
    private val adapterExposicaoHomeFunc = AdapterExposicaoHomeFunc(this,listaExposicoes)

    private lateinit var recyclerViewExposicoes: RecyclerView
    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_home)

        btnSair = findViewById(R.id.botaoSair)
        auth = FirebaseAuth.getInstance()
        busca = findViewById(R.id.busca)
        recyclerViewExposicoes = findViewById(R.id.recyclerviewExposicoesFuncionarios)
        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewExposicoes.setHasFixedSize(true)

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
                    recyclerViewExposicoes.adapter = adapterExposicaoHomeFunc
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

        busca.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                fileList(newText)
                return true
            }

        })
    }

    override fun onStart() {
        super.onStart()
        btnSair.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MALoginUsuario::class.java))
        }
    }

    private fun fileList(query:String?) {
        if(query != null){
            val filteredList = ArrayList<Exposicao>()
            for (i in listaExposicoes){
                if (i.nomeExposicao?.lowercase(Locale.ROOT)?.contains(query) == true){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
//                Toast.makeText(this,"Nenhuma exposição encontrada", Toast.LENGTH_SHORT).show()
            }else{
                adapterExposicaoHomeFunc.setFilteredList(filteredList)
            }
        }
    }
    private fun AddExposicao() {
        Log.d("ADD", "Tela add Exposição")
        val intent = Intent(this, MAAddExposicao::class.java)
        startActivity(intent)
    }
}
