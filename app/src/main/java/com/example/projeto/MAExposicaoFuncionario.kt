package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterObraFunc
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MAExposicaoFuncionario : AppCompatActivity() {
    private lateinit var recyclerViewSobreExposicoes: RecyclerView
    private lateinit var recyclerViewObras: RecyclerView

    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_exposicao)

        recyclerViewSobreExposicoes = findViewById<RecyclerView>(R.id.sobreExposicaoRecyclerView)
        recyclerViewSobreExposicoes.layoutManager = LinearLayoutManager(this)
        recyclerViewSobreExposicoes.setHasFixedSize(true)

        recyclerViewObras = findViewById<RecyclerView>(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 5)
        recyclerViewObras.setHasFixedSize(true)

        val sobreExposicaoLista:MutableList<Exposicao> = mutableListOf()
        val obrasList: MutableList<Obra> = mutableListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("Exposicao")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val exposicao: Exposicao? = data.toObject(Exposicao::class.java)
                        if(exposicao != null){
                        sobreExposicaoLista.add(exposicao)
                        }
                    }
                    val adapterExposicao = AdapterExposicao(this, sobreExposicaoLista)
                    recyclerViewSobreExposicoes.adapter = adapterExposicao
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }

        db.collection("Obra")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val obra: Obra? = data.toObject(Obra::class.java)
                        if(obra != null){
                        obrasList.add(obra)
                        }
                    }
                    val adapterObrasFunc = AdapterObraFunc(this, obrasList)
                    recyclerViewObras.adapter = adapterObrasFunc
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoAddObra = findViewById<ImageButton>(R.id.addObra)
        botaoAddObra.setOnClickListener{
            AddObra()
        }

    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        startActivity(Intent(this, MAHomeFuncionario::class.java))
    }

    private fun AddObra() {
        Log.d("ADD", "tela add obra")
        startActivity(Intent(this, MAAddObra::class.java))
    }

}
