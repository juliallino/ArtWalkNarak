package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MALoginFuncionario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    lateinit var botaoEntrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_login)
        botaoEntrar = findViewById(R.id.botaoDeEntrarFuncionario)
        auth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        botaoEntrar.setOnClickListener {
            auth.signInWithEmailAndPassword("centelhasemmovimento@gmail.com", "12345678")
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MAHomeFuncionario::class.java)
                        startActivity(intent)
                    } else {

                    }
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
