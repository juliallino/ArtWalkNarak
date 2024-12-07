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


class MAAddExposicao : AppCompatActivity() {

    lateinit var nomeExpo: EditText
    lateinit var descricaoExposicao: EditText
    lateinit var botaoEmAndamento: Button
    lateinit var botaoEncerrada: Button
    lateinit var botaoUploadImagem: Button
    lateinit var botaoSalvar: Button
    lateinit var botaoExcluir: Button
    lateinit var imagemExposicao: ImageView

    lateinit var db: FirebaseFirestore
    var status: Boolean = true
    var imagemBase64: String? = null


    companion object {
        const val REQUEST_CODE_IMAGE_PICK = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_add_exposicao)

        nomeExpo = findViewById(R.id.nomeExposicaoEdit)
        descricaoExposicao = findViewById(R.id.descricaoExposicaoEdit)
        botaoEmAndamento = findViewById(R.id.emAndamentoBotao)
        botaoEncerrada = findViewById(R.id.encerradaBotao)
        botaoSalvar = findViewById(R.id.salvarBotao)
        botaoExcluir = findViewById(R.id.excluirBotao)
        imagemExposicao = findViewById(R.id.imagemExposicao)
        db = Firebase.firestore
        botaoUploadImagem = findViewById(R.id.uploadBotao)


        botaoUploadImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
        }

        val exposicaoId = intent.getStringExtra("idExposicao")
        if (exposicaoId != null) {
            carregarDadosExposicao(exposicaoId)
        }

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imagemExposicao.setImageBitmap(bitmap)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()
                imagemBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val exposicaoId = intent.getStringExtra("idExposicao")
        botaoEmAndamento.setOnClickListener {
            status = true
            Toast.makeText(this, "EM ANDAMENTO", Toast.LENGTH_SHORT).show()
        }

        botaoEncerrada.setOnClickListener {
            status = false
            Toast.makeText(this, "ENCERRADA", Toast.LENGTH_SHORT).show()
        }
        botaoSalvar.setOnClickListener {
            if (exposicaoId != null) {
                atualizarExposicao(exposicaoId)
            } else {
                adicionarExposicao()
            }
        }
        botaoExcluir.setOnClickListener {
            val exposicaoId = intent.getStringExtra("idExposicao")
            if (exposicaoId != null) {
                excluirExposicao(exposicaoId)
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

    private fun carregarDadosExposicao(id: String) {
        db.collection("Exposicao").document(id).get().addOnSuccessListener { document ->
                if (document != null) {
                    nomeExpo.setText(document.getString("nomeExposicao"))
                    descricaoExposicao.setText(document.getString("descricaoExposicao"))
                    status = document.getBoolean("status") ?: true

                    val imagemBase64 = document.getString("imagemExposicao")
                    if (imagemBase64 != null) {
                        val bitmap = base64ToBitmap(imagemBase64)
                        imagemExposicao.setImageBitmap(bitmap)
                        this.imagemExposicao = imagemExposicao
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar dados da exposição.", Toast.LENGTH_SHORT)
                    .show()
            }
    }


    private fun atualizarExposicao(id: String) {
        val nome = nomeExpo.text.toString()
        val descricao = descricaoExposicao.text.toString()

        db.collection("Exposicao").document(id).get().addOnSuccessListener { document ->
                if (document != null) {
                    val imagemAtual = document.getString("imagemExposicao")
                    val imagemFinal = imagemBase64 ?: imagemAtual

                    val exposicaoData = mapOf(
                        "nomeExposicao" to nome,
                        "descricaoExposicao" to descricao,
                        "status" to status,
                        "imagemExposicao" to imagemFinal
                    )

                    db.collection("Exposicao").document(id).set(exposicaoData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Exposição atualizada com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()
                            VoltarTela()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Erro ao atualizar a exposição.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao buscar dados da exposição.", Toast.LENGTH_SHORT)
                    .show()
            }
    }


    private fun adicionarExposicao() {
        val nome = nomeExpo.text.toString()
        val descricao = descricaoExposicao.text.toString()

        val exposicaoData = mapOf(
            "nomeExposicao" to nome,
            "descricaoExposicao" to descricao,
            "status" to status,
            "imagemExposicao" to imagemBase64
        )

        db.collection("Exposicao").add(exposicaoData).addOnSuccessListener {
                Toast.makeText(this, "Exposição salva com sucesso!", Toast.LENGTH_SHORT).show()
                VoltarTela()
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar exposição.", Toast.LENGTH_SHORT).show()
            }
    }


    private fun excluirExposicao(id: String) {
        db.collection("Exposicao").document(id).delete().addOnSuccessListener {
                Toast.makeText(this, "Exposição excluída com sucesso!", Toast.LENGTH_SHORT).show()
                VoltarTela()
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao excluir a exposição.", Toast.LENGTH_SHORT).show()
            }
    }


    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do funcionário")
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }
}