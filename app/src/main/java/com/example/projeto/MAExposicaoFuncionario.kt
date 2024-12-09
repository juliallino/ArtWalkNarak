package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterObraFunc
import com.example.projeto.model.Obra
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MAExposicaoFuncionario : AppCompatActivity() {
    private lateinit var recyclerViewObras: RecyclerView
    private lateinit var nomeExposicao: TextView
    private lateinit var descricaoExposicao: TextView
    private val obrasList: MutableList<Obra> = mutableListOf()
    private var db = Firebase.firestore
    private lateinit var botaoVoltarTela: ImageButton
    private lateinit var botaoAddObra: ImageButton
    private lateinit var busca: SearchView
    private var adapterObrasFunc = AdapterObraFunc(this, obrasList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_exposicao)

        nomeExposicao = findViewById(R.id.nomeExposicao)
        descricaoExposicao = findViewById(R.id.descricaoExposicao)
        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)
        botaoAddObra = findViewById(R.id.addObra)
        busca = findViewById(R.id.busca)

        recyclerViewObras = findViewById(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 3)
        recyclerViewObras.setHasFixedSize(true)


        db = FirebaseFirestore.getInstance()
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("Debug", "ID recebido: $exposicaoId")

        if (exposicaoId != null) {
            db.collection("Exposicao").document(exposicaoId).get()
                .addOnSuccessListener { documentReference ->
                    if (documentReference != null && documentReference.exists()) {
                        nomeExposicao.text = documentReference.getString("nomeExposicao")
                        descricaoExposicao.text = documentReference.getString("descricaoExposicao")
                    } else {
                        Log.d("Debug", "Documento não encontrado")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("Debug", "Erro ao buscar o documento: ${exception.message}")
                }
        } else {
            Log.d("Debug", "ID não encontrado")
        }

        db.collection("Obra").whereEqualTo("idExposicao", exposicaoId).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (data in documents) {
                        val obra: Obra? = data.toObject(Obra::class.java)
                        obra?.idObra = data.id
                        if (obra != null) {
                            obrasList.add(obra)
                        }
                    }
                    recyclerViewObras.adapter = adapterObrasFunc
                } else {
                    Log.d("Debug", "Nenhuma obra encontrada para esta Exposição.")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
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

        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }
        botaoAddObra.setOnClickListener {
            AddObra()
        }

    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        startActivity(Intent(this, MAHomeFuncionario::class.java))
    }

    private fun AddObra() {
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("ADD", "tela add obra")
        val intent = Intent(this, MAAddObra::class.java)
        intent.putExtra("idExposicao", exposicaoId)
        Log.d("Debug", "ID enviado: $exposicaoId")
        startActivity(intent)
    }

    private fun fileList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Obra>()
            for (i in obrasList) {
                if (i.nomeObra?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
            } else {
                adapterObrasFunc.setFilteredList(filteredList)
            }
        }
    }

}
