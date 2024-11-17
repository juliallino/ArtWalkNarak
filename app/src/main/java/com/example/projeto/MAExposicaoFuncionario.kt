package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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

class MAExposicaoFuncionario : AppCompatActivity() {
//    private lateinit var recyclerViewSobreExposicoes: RecyclerView
    private lateinit var recyclerViewObras: RecyclerView
    private lateinit var nomeExposicao: TextView
    private lateinit var descricaoExposicao: TextView
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_exposicao)

        nomeExposicao = findViewById(R.id.nomeExposicao)
        descricaoExposicao = findViewById(R.id.descricaoExposicao)

        recyclerViewObras = findViewById<RecyclerView>(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 5)
        recyclerViewObras.setHasFixedSize(true)


        db = FirebaseFirestore.getInstance()
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("Debug", "ID recebido: $exposicaoId")

        if (exposicaoId != null) {
            db.collection("Exposicao")
                .document(exposicaoId)  // Buscando o documento específico pelo ID
                .get()
                .addOnSuccessListener { documentReference ->
                    if (documentReference != null && documentReference.exists()) {
                        // Preenchendo as informações da exposição
                        nomeExposicao.text = documentReference.getString("nomeExposicao")
                        descricaoExposicao.text = documentReference.getString("descricaoExposicao")
                    } else {
                        Log.d("Debug", "Documento não encontrado")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Debug", "Erro ao buscar o documento: ${exception.message}")
                }
        } else {
            Log.d("Debug", "ID não encontrado")
        }

        val obrasList: MutableList<Obra> = mutableListOf()

        db.collection("Obra")
            .whereEqualTo("idExposicao", exposicaoId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (data in documents) {
                        val obra: Obra? = data.toObject(Obra::class.java)
                        if (obra != null) {
                            obrasList.add(obra)
                        }
                    }
                    val adapterObrasFunc = AdapterObraFunc(this, obrasList)
                    recyclerViewObras.adapter = adapterObrasFunc
                } else {
                    Log.d("Debug", "Nenhuma obra encontrada para esta Exposição.")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
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
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("ADD", "tela add obra")
        val intent = Intent(this, MAAddObra::class.java)
        intent.putExtra("idExposicao", exposicaoId)
        Log.d("Debug", "ID enviado: $exposicaoId")
        startActivity(intent)
    }

}
