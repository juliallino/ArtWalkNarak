package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MALoginUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_login)

        val botaoLoginComoFuncionario = findViewById<Button>(R.id.botaoDeLoginComoFuncionario)
        botaoLoginComoFuncionario.setOnClickListener {
            Toast.makeText(this, "Login como funcionario", Toast.LENGTH_SHORT).show()
            irParaLoginComoFuncionario()
        }
        val botaoLoginComGoogle = findViewById<ImageButton>(R.id.google)
        botaoLoginComGoogle.setOnClickListener {
            Toast.makeText(this, "Login com google aprovado", Toast.LENGTH_SHORT).show()
            EntrarComGoogle()
        }
        val botaoCadastroComGoogle = findViewById<ImageButton>(R.id.google2)
        botaoCadastroComGoogle.setOnClickListener {
            Toast.makeText(this, "Cadastro com google aprovado", Toast.LENGTH_SHORT).show()
            EntrarComGoogle()
        }
    }

    private fun irParaLoginComoFuncionario() {
        Log.d("Login", "Indo para tela de login do funcionário")
        val intent = Intent(this, MALoginFuncionario::class.java)
        startActivity(intent)
    }
    private fun EntrarComGoogle() {
        Log.d("Login", "Indo para tela de inical do usuario")
        val intent = Intent(this, MAHomeUsuario::class.java)
        startActivity(intent)
    }
}