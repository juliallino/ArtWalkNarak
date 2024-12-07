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
    lateinit var botaoVoltarTela : ImageButton
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
        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)


        botaoUploadImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
        }

        val obraId = intent.getStringExtra("idObra")
        Log.d("Debug", "ID recebido: $obraId")

        if (obraId != null) {
            carregarDadosObra()
        }
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
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
                imagemObra.setImageBitmap(bitmap)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()
                imagemBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val obraId = intent.getStringExtra("idObra")

        botaoSalvar.setOnClickListener {
            if (obraId != null) {
                atualizarObra(obraId)
            } else {
                adicionarObra()
            }
        }
        botaoExcluir.setOnClickListener {
            if (obraId != null) {
                excluirObra(obraId)
            } else {
                Toast.makeText(this, "Essa exposição não existe.", Toast.LENGTH_SHORT).show()
            }
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
    private fun carregarDadosObra() {
        val idObra = intent.getStringExtra("idObra")

        if (idObra != null) {
            db.collection("Obra").document(idObra).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        nomeObra.setText(document.getString("nomeObra"))
                        descricaoObra.setText(document.getString("descricaoObra"))
                        // Carregar imagem se existir
                        val imagemBase64 = document.getString("imagemObra")
                        if (imagemBase64 != null) {
                            val bitmap = base64ToBitmap(imagemBase64)
                            imagemObra.setImageBitmap(bitmap)
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao carregar dados da exposição.", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun atualizarObra(id: String) {
        val nome = nomeObra.text.toString()
        val descricao = descricaoObra.text.toString()
        val exposicaoId = intent.getStringExtra("idExposicao")

        db.collection("Obra").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val imagemAtual = document.getString("imagemObra")
                    val imagemFinal = imagemBase64 ?: imagemAtual

                    val obraData = mapOf(
                        "nomeObra" to nome,
                        "descricaoObra" to descricao,
                        "imagemObra" to imagemFinal,
                        "idExposicao" to exposicaoId
                    )

                    db.collection("Obra").document(id)
                        .set(obraData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Obra atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                            VoltarTela()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Erro ao atualizar a obra.", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao buscar dados da obra.", Toast.LENGTH_SHORT).show()
            }
    }


    private fun adicionarObra() {
        val nome = nomeObra.text.toString()
        val descricao = descricaoObra.text.toString()
        val exposicaoId = intent.getStringExtra("idExposicao")

        val obraData = mapOf(
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
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar obra.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun excluirObra(id: String) {
            db.collection("Obra").document(id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Obra excluída com sucesso!", Toast.LENGTH_SHORT).show()
                    VoltarTela()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao excluir a obra.", Toast.LENGTH_SHORT).show()
                    VoltarTela()

                }
    }


    private fun VoltarTela() {
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("Debug", "ID de Exposição recebido: $exposicaoId")
        if (exposicaoId != null) {
            val intent = Intent(this, MAExposicaoFuncionario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            startActivity(intent)
        } else {
            Log.d("Debug", "ID de Exposição não encontrado")
        }
    }
}


