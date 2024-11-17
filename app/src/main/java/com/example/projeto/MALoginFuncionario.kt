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

class MALoginFuncionario : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var botaoEntrar: Button
    private lateinit var email: EditText
    private lateinit var senha: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_login)
        botaoEntrar = findViewById(R.id.botaoDeEntrarFuncionario)
        auth = FirebaseAuth.getInstance()
        email  = findViewById(R.id.emailEdit)
        senha  = findViewById(R.id.senhaEdit)

        val botaoLoginComoUsuario = findViewById<Button>(R.id.botaoDeLoginComoUsuario)
        botaoLoginComoUsuario.setOnClickListener {
            Toast.makeText(this, "Login como usuario", Toast.LENGTH_SHORT).show()
            VoltarLoginComoUsuario()
        }

    }

    override fun onStart() {
        super.onStart()
        botaoEntrar.setOnClickListener {
            if (email.text.toString().isNotEmpty() && senha.text.toString().isNotEmpty()) {
                auth.signInWithEmailAndPassword("centelhasemmovimento@gmail.com", "12345678")
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MAHomeFuncionario::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Revise os dados", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Existe algum campo em vazio", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun VoltarLoginComoUsuario() {
        Log.d("Login Usuario", "Indo para tela de login do usuario")
        val intent = Intent(this, MALoginUsuario::class.java)
        startActivity(intent)
    }
}
