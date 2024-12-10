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

class MALoginUsuario : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var botaoEntrar: Button
    private lateinit var botaoCadastro: Button
    private lateinit var botaoLoginComoFuncionario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_login)

        // Inicializa os componentes
        initViews()
        setupListeners()
    }

    private fun initViews() {
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.emailEdit)
        senha = findViewById(R.id.senhaEdit)
        botaoEntrar = findViewById(R.id.botaoDeEntrarUsuario)
        botaoCadastro = findViewById(R.id.botaoDeCadastro)
        botaoLoginComoFuncionario = findViewById(R.id.botaoDeLoginComoFuncionario)
    }

    private fun setupListeners() {
        botaoLoginComoFuncionario.setOnClickListener {
            Toast.makeText(this, "Login como funcionário", Toast.LENGTH_SHORT).show()
            LoginComoFuncionario()
        }

        botaoCadastro.setOnClickListener {
            CadastroPagina()
        }

        botaoEntrar.setOnClickListener {
            realizarLogin()
        }
    }

    private fun realizarLogin() {
        if (email.text.isNotEmpty() && senha.text.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MAHomeUsuario::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Revise os dados ou crie uma conta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Existem campos vazios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun CadastroPagina() {
        Log.d("Cadastro", "Indo para tela de Cadastro")
        val intent = Intent(this, MACadastroUsuario::class.java)
        startActivity(intent)
    }

    private fun LoginComoFuncionario() {
        Log.d("Login", "Indo para tela de login do funcionário")
        val intent = Intent(this, MALoginFuncionario::class.java)
        startActivity(intent)
    }
}
