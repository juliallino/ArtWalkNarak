package com.example.projeto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Locale

class MAObraUsuario : AppCompatActivity() {
    lateinit var botaoenviar: Button
    lateinit var chatIA: EditText
    private lateinit var nomeObra: TextView
    private lateinit var descricaoObra: TextView
    private lateinit var imagemObra: ImageView
    lateinit var botaoAcessibilidade: ImageButton
    lateinit var botaoDesatiavrAcessibildade: ImageButton
    private var textToSpeech: TextToSpeech? = null
    private var db = Firebase.firestore
    private lateinit var botaoVoltarTela: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_obra)

        nomeObra = findViewById(R.id.nomeObra)
        descricaoObra = findViewById(R.id.descricaoObra)
        imagemObra = findViewById(R.id.imagemObra)
        botaoAcessibilidade = findViewById(R.id.acessibilidadeObra)
        botaoDesatiavrAcessibildade = findViewById(R.id.desativaracessibilidade)
        chatIA = findViewById(R.id.chatIA)
        botaoenviar = findViewById(R.id.enivar)
        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)

        db = FirebaseFirestore.getInstance()


        val obraId = intent.getStringExtra("idObra")
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("Debug", "ID recebido: $obraId")
        Log.d("Debug", "ID de Exposição recebido: $exposicaoId")

        if (obraId != null) {
            db.collection("Obra").document(obraId).get().addOnSuccessListener { documentReference ->
                    if (documentReference != null && documentReference.exists()) {
                        nomeObra.text = documentReference.getString("nomeObra")
                        descricaoObra.text = documentReference.getString("descricaoObra")
                        val base64Image = documentReference.getString("imagemObra")
                        if (base64Image != null) {
                            val bitmap = decodeBase64ToBitmap(base64Image)
                            if (bitmap != null) {
                                imagemObra.setImageBitmap(bitmap)
                            } else {
                                Log.d("Debug", "Erro ao decodificar a imagem")
                            }

                        } else {
                            Log.d("Debug", "Campo de imagem não encontrado")
                        }
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
                val textToRead = "${nomeObra.text} ${descricaoObra.text}"
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

        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }

    }


    private fun VoltarTela() {
        val exposicaoId = intent.getStringExtra("idExposicao")
        if (exposicaoId != null) {
            val intent = Intent(this, MAExposicaoUsuario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            startActivity(intent)
        } else {
            Log.d("Debug", "ID de Exposição não encontrado")
        }
    }


    fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    private fun AcessibilidadeSom() {
        Log.d("botão acessibilidade", "para ativar a leitura de textp")
        Toast.makeText(this, "Acessibilidade ativada", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        chatIA.setOnClickListener {
            chatIA.setText("")
        }
        botaoenviar.setOnClickListener {
            lifecycleScope.launch {
                generateIA()
            }
        }
    }

    suspend fun generateIA() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash", apiKey = ""
        )

        val prompt = chatIA.text.toString()
        val response = generativeModel.generateContent(prompt + "responta essa pergunta")
        chatIA.setText(response.text)
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