package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MACadastroUsuario : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var confirmarSenha: EditText
    private lateinit var botaoCadastrar: Button
    private lateinit var botaoVoltarLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_cadastro)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        email = findViewById(R.id.emailEdit)
        senha = findViewById(R.id.senhaEdit)
        confirmarSenha = findViewById(R.id.confirmeSenhaEdit)
        botaoCadastrar = findViewById(R.id.botaoDeCadastro)
        botaoVoltarLogin = findViewById(R.id.botaoDeLoginComoUsuario)

        botaoVoltarLogin.setOnClickListener {
            VoltarParaLogin()
        }
    }

    override fun onStart() {
        super.onStart()
        botaoCadastrar.setOnClickListener {
            if (email.text.toString().isNotEmpty() && senha.text.toString()
                    .isNotEmpty() && confirmarSenha.text.toString().isNotEmpty()
            ) {
                if (senha.text.toString() == confirmarSenha.text.toString()) {
                    auth.createUserWithEmailAndPassword(
                        email.text.toString(),
                        senha.text.toString()
                    ).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = auth.currentUser?.uid
                                val userMap = hashMapOf(
                                    "email" to email.text.toString(), "funcionario" to false
                                )

                                if (userId != null) {
                                    db.collection("usuarios").document(userId).set(userMap)
                                        .addOnSuccessListener {
                                            Log.d(
                                                "Cadastro",
                                                "Usuário comum cadastrado com sucesso!"
                                            )
                                            Toast.makeText(
                                                this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val intent = Intent(this, MALoginUsuario::class.java)
                                            startActivity(intent)
                                        }.addOnFailureListener { e ->
                                            Log.e(
                                                "Cadastro",
                                                "Erro ao salvar no Firestore: ${e.message}"
                                            )
                                            Toast.makeText(
                                                this,
                                                "Erro ao salvar no banco de dados.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Erro ao Cadastrar: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "As senhas não estão correspondentes", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Existe algum campo vazio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun VoltarParaLogin() {
        Log.d("Login", "Indo para tela de login do usuario")
        val intent = Intent(this, MALoginUsuario::class.java)
        startActivity(intent)
    }
}