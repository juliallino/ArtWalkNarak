package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MALoginUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val botaoLoginComoFuncionario = findViewById<Button>(R.id.botaoDeLoginComoFuncionario)
        botaoLoginComoFuncionario.setOnClickListener {
            Toast.makeText(this, "Login como funcionario", Toast.LENGTH_SHORT).show()
            irParaLoginComoFuncionario()
        }
    }
    private fun irParaLoginComoFuncionario() {
        Log.d("Login", "Indo para tela de login do funcionário")
        val intent = Intent(this, MALoginFuncionario::class.java)
        startActivity(intent)
    }
}