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
import android.widget.ImageView
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
    lateinit var imagemObra : ImageView

    var imagemBase64: String? = null
    lateinit var db: FirebaseFirestore

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
        imagemObra = findViewById(R.id.imagemObra)
        db = Firebase.firestore
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
            val exposicaoId = intent.getStringExtra("idExposicao")

            // Exibir a imagem na ImageView antes de salvar
            imagemBase64?.let { base64String ->
                val bitmap = base64ToBitmap(base64String)
                imagemObra.setImageBitmap(bitmap)
            }

            if (exposicaoId != null) {
                // Criando um mapa com os dados da obra
                val obraData = hashMapOf(
                    "nomeObra" to nome,
                    "descricaoObra" to descricao,
                    "imagemObra" to imagemBase64,
                    "idExposicao" to exposicaoId
                )

                db.collection("Obra")
                    .add(obraData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Obra salva com sucesso!", Toast.LENGTH_SHORT).show()
                        VoltarTela()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Erro ao salvar obra: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Erro: Exposição não encontrada.", Toast.LENGTH_SHORT).show()
            }
        VoltarTela()
        }

        botaoExcluir.setOnClickListener {
            val exposicaoId = intent.getStringExtra("idExposicao")

            db.collection("Exposicao")
                .document(exposicaoId!!)
                .collection("Obra")
                .document()
                .delete()
            VoltarTela()
        }

    }

    private fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedString: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela exposições do funcionário")
        val exposicaoId = intent.getStringExtra("idExposicao")
        exposicaoId?.let {
            val intent = Intent(this, MAExposicaoFuncionario::class.java)
            intent.putExtra("idExposicao", it) // Passando o idExposicao como extra
            startActivity(intent)
        } ?: run {
            Log.d("Voltar", "Erro: Exposição não encontrada")
        }
    }
}

