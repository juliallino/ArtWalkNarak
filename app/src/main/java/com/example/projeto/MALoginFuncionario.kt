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

class MALoginFuncionario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_funcionario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoLoginComoUsuario = findViewById<Button>(R.id.botaoDeLoginComoUsuario)
        botaoLoginComoUsuario.setOnClickListener {
            Toast.makeText(this, "Login como usuario", Toast.LENGTH_SHORT).show()
            VoltarLoginComoUsuario()
        }
        val botaoDeEntrarFuncionario = findViewById<Button>(R.id.botaoDeEntrarFuncionario)
        botaoDeEntrarFuncionario.setOnClickListener {
            Toast.makeText(this, "Acesso permitido", Toast.LENGTH_SHORT).show()
            AprovarLoginFuncionario()
        }
    }
    private fun AprovarLoginFuncionario() {
        Log.d("Home Funcionario", "Indo para tela inicial do funcionário")
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }

    private fun VoltarLoginComoUsuario() {
        Log.d("Login Usuario", "Indo para tela de login do usuario")
        val intent = Intent(this, MALoginUsuario::class.java)
        startActivity(intent)
    }
}
