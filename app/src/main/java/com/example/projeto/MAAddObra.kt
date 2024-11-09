package com.example.projeto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream


class MAAddObra : AppCompatActivity() {
    lateinit var nomeObra : EditText
    lateinit var descricaoObra: EditText
    lateinit var botaoUploadImagem: Button
    lateinit var botaoSalvar: Button
    lateinit var botaoExcluir: Button
    lateinit var fb: FirebaseFirestore
    var imagemBase64: String? = null

    companion object {
        const val REQUEST_CODE_IMAGE_PICK = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_obras)

        nomeObra = findViewById(R.id.nomeObraEdit)
        descricaoObra = findViewById(R.id.descricaoObraEdit)
        botaoSalvar = findViewById(R.id.salvarBotao)
        botaoExcluir = findViewById(R.id.excluirBotao)
        fb = Firebase.firestore

        botaoUploadImagem = findViewById(R.id.uploadBotao)
        botaoUploadImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MAAddExposicao.REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()
                imagemBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaExposicoes)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        botaoSalvar.setOnClickListener {
            val nome = nomeObra.text.toString()
            val descricao = descricaoObra.text.toString()

            val exposicaoData = hashMapOf(
                "nomeExposicao" to nome,
                "descricaoExposicao" to descricao,
                "imagemExposicao" to imagemBase64
            )

            fb.collection("Obra")
                .add(exposicaoData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Exposição salva com sucesso!", Toast.LENGTH_SHORT).show()
                    VoltarTela()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao salvar exposição.", Toast.LENGTH_SHORT).show()
                }
        }

        botaoExcluir.setOnClickListener {
            val nome = nomeObra.text.toString()
            fb.collection("Exposicao")
                .document(nome)
                .delete()
            VoltarTela()
        }

    }

    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela exposicções do funcionário")
        startActivity(Intent(this, MAExposicaoFuncionario::class.java))
    }
}

