package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MALoginFuncionario : AppCompatActivity() {
    val numeroIdentificacao = "12345678"
    val senha = "12345678"
    lateinit var editNumero:EditText
    lateinit var editSenha:EditText
    lateinit var botaoEntrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_login)
        botaoEntrar = findViewById(R.id.botaoDeEntrarFuncionario)
        editNumero = findViewById(R.id.numeroEdit)
        editSenha = findViewById(R.id.senhaEdit)

    }

    override fun onStart() {
        super.onStart()
        botaoEntrar.setOnClickListener {
            val numeroInput = editNumero.text.toString()
            val senhaInput = editSenha.text.toString()

            if (numeroInput == numeroIdentificacao && senhaInput == senha) {
                val intent = Intent(this, MAHomeFuncionario::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Número/Pass incorretos", Toast.LENGTH_SHORT).show()
            }
        }


        val botaoLoginComoUsuario = findViewById<Button>(R.id.botaoDeLoginComoUsuario)
        botaoLoginComoUsuario.setOnClickListener {
            Toast.makeText(this, "Login como usuario", Toast.LENGTH_SHORT).show()
            VoltarLoginComoUsuario()
        }
    }
    private fun VoltarLoginComoUsuario() {
        Log.d("Login Usuario", "Indo para tela de login do usuario")
        val intent = Intent(this, MALoginUsuario::class.java)
        startActivity(intent)
    }
}
