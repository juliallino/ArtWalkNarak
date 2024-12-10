package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterObraUsu
import com.example.projeto.model.Obra
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale



class MAExposicaoUsuario : AppCompatActivity() {
    private lateinit var recyclerViewObras: RecyclerView
    private lateinit var nomeExposicao: TextView
    private lateinit var descricaoExposicao: TextView
    private var db = Firebase.firestore
    private lateinit var busca: SearchView
    private val obrasList: MutableList<Obra> = mutableListOf()
    private val adapterObras = AdapterObraUsu(this, obrasList)
    private var textToSpeech: TextToSpeech? = null
    lateinit var botaoAcessibilidade: ImageButton
    lateinit var botaoDesatiavrAcessibildade: ImageButton
    private lateinit var botaoVoltarTela: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_exposicao)

        nomeExposicao = findViewById(R.id.nomeExposicao)
        descricaoExposicao = findViewById(R.id.descricaoExposicao)
        busca = findViewById(R.id.busca)

        botaoAcessibilidade = findViewById(R.id.acessibilidadeExposicao)
        botaoDesatiavrAcessibildade = findViewById(R.id.desativaracessibilidade)
        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)

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

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langResult =
                    textToSpeech?.setLanguage(Locale("pt", "BR")) ?: TextToSpeech.LANG_NOT_SUPPORTED

                when (langResult) {
                    TextToSpeech.LANG_MISSING_DATA -> {
                        showErrorMessage("Dados de idioma ausentes. Por favor, instale os dados do idioma.")
                    }

                    TextToSpeech.LANG_NOT_SUPPORTED -> {
                        showErrorMessage("Idioma não suportado.")
                    }

                    else -> {
                        // A linguagem foi definida com sucesso
                    }
                }
            } else {
                showErrorMessage("Erro ao inicializar o TextToSpeech.")
            }
        }

        botaoAcessibilidade.setOnClickListener { v ->
            if (textToSpeech != null) {
                val textToRead = "${nomeExposicao.text} ${descricaoExposicao.text}"
                textToSpeech?.stop()
                textToSpeech?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
                AcessibilidadeSom()
            } else {
                showErrorMessage("TextToSpeech não está disponível.")
            }
        }
        botaoDesatiavrAcessibildade.setOnClickListener {
            textToSpeech?.stop()
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
                    recyclerViewObras.adapter = adapterObras
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
//                Toast.makeText(this,"Nenhuma obra encontrada", Toast.LENGTH_SHORT).show()
            } else {
                adapterObras.setFilteredList(filteredList)
            }
        }
    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do usuario")
        startActivity(Intent(this, MAHomeUsuario::class.java))
    }

    private fun AcessibilidadeSom() {
        Log.d("botão acessibilidade", "para ativar a leitura de textp")
        Toast.makeText(this, "Acessibilidade ativada", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        super.onDestroy()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

