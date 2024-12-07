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

class MALoginFuncionario : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var botaoEntrar: Button
    private lateinit var botaoLoginComoUsuario: Button
    private lateinit var email: EditText
    private lateinit var senha: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_login)
        botaoEntrar = findViewById(R.id.botaoDeEntrarFuncionario)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        email = findViewById(R.id.emailEdit)
        senha = findViewById(R.id.senhaEdit)
        botaoLoginComoUsuario = findViewById(R.id.botaoDeLoginComoUsuario)

        botaoLoginComoUsuario.setOnClickListener {
            Toast.makeText(this, "Login como usuario", Toast.LENGTH_SHORT).show()
            VoltarLoginComoUsuario()
        }

        botaoEntrar.setOnClickListener {
            if (email.text.toString().isNotEmpty() && senha.text.toString().isNotEmpty()) {
                auth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                // Verificar se o usuário é funcionário no Firestore
                                db.collection("usuarios").document(userId).get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            val isFuncionario =
                                                document.getBoolean("funcionario") ?: false
                                            if (isFuncionario) {
                                                // Usuário é funcionário, redirecionar para a tela de funcionário
                                                Log.d("Login", "Funcionário logado com sucesso!")
                                                val intent =
                                                    Intent(this, MAHomeFuncionario::class.java)
                                                startActivity(intent)
                                            } else {
                                                // Usuário não é funcionário, exibir mensagem de erro
                                                Toast.makeText(
                                                    this,
                                                    "Você não tem permissão para acessar esta área.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            // Documento não encontrado
                                            Toast.makeText(
                                                this,
                                                "Erro ao verificar usuário. Tente novamente.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }.addOnFailureListener { e ->
                                        Log.e(
                                            "Login",
                                            "Erro ao buscar dados no Firestore: ${e.message}"
                                        )
                                        Toast.makeText(
                                            this,
                                            "Erro ao verificar permissão. Tente novamente.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            } else {
                                Toast.makeText(
                                    this, "Erro ao obter dados do usuário.", Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this, "Revise os dados ou crie uma conta", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Existe algum campo vazio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun VoltarLoginComoUsuario() {
        Log.d("Login Usuario", "Indo para tela de login do usuario")
        val intent = Intent(this, MALoginUsuario::class.java)
        startActivity(intent)
    }
}
