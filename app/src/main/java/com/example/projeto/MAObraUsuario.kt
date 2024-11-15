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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterObraUsu
import com.example.projeto.model.Obra
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MAObraUsuario : AppCompatActivity() {
    lateinit var botaoenviar:Button
    lateinit var chatIA: EditText
//    private lateinit var recyclerViewObras: RecyclerView
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_obra)

        chatIA = findViewById(R.id.chatIA)
        botaoenviar = findViewById(R.id.enivar)

//        recyclerViewObras = findViewById<RecyclerView>(R.id.sobreObrasRecyclerView)
//        recyclerViewObras.layoutManager = LinearLayoutManager(this)
//        recyclerViewObras.setHasFixedSize(true)
//
//        val obrasList: MutableList<Obra> = mutableListOf()
//
//        db.collection("Obra")
//            .get()
//            .addOnSuccessListener {
//                if(!it.isEmpty){
//                    for(data in it.documents){
//                        val obra: Obra? = data.toObject(Obra::class.java)
//                        if(obra != null){
//                            obrasList.add(obra)
//                            Log.d("Firestore", "Obra: ${obra.nomeObra}, Descrição: ${obra.descricaoObra}")
//                        }
//                    }
//                    val adapterObra = AdapterSobreObra(this, obrasList)
//                    recyclerViewObras.adapter = adapterObra
//                    adapterObra.notifyDataSetChanged()
//
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
//            }

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoAcessibilidade = findViewById<ImageButton>(R.id.acessibilidadeObra)
        botaoAcessibilidade.setOnClickListener{
            AcessibilidadeSom()
        }

    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial da exposição do usuario")
        startActivity(Intent(this, MAExposicaoUsuario::class.java))
    }

    private fun AcessibilidadeSom(){
        Log.d("botão acessibilidade", "para ativar a leitura de textp")
        Toast.makeText(this, "Acessibilidade ativada", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        chatIA.setOnClickListener{
            chatIA.setText("")
        }
        botaoenviar.setOnClickListener{
            lifecycleScope.launch{
                generateIA()
            }
        }
    }

    suspend fun generateIA(){
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBcZX1D7erqrZK8VYgwwJN_Y_PMoxJMopc")

        val prompt = chatIA.text.toString()
        val response = generativeModel.generateContent(prompt + "responta essa pergunta em até 250 caracteres")
        chatIA.setText(response.text)
    }

}