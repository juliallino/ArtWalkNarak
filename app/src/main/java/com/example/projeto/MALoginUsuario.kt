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
    lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var botaoEntrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_login)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.emailEdit)
        senha = findViewById(R.id.senhaEdit)
        botaoEntrar = findViewById(R.id.botaoDeEntrarUsuario)

        val botaoLoginComoFuncionario = findViewById<Button>(R.id.botaoDeLoginComoFuncionario)
        botaoLoginComoFuncionario.setOnClickListener {
            Toast.makeText(this, "Login como funcionario", Toast.LENGTH_SHORT).show()
            LoginComoFuncionario()
        }

        val botaoCadastro = findViewById<Button>(R.id.botaoDeCadastro)
        botaoCadastro.setOnClickListener {
            CadastroPagina()
        }

    }

    override fun onStart() {
        super.onStart()
        botaoEntrar.setOnClickListener {
            if (email.text.toString().isNotEmpty() && senha.text.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString() , senha.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, MAHomeUsuario::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Revise os dados ou crie uma conta", Toast.LENGTH_SHORT).show()
                    } }
            }else{
                Toast.makeText(this, "Existe algum campo em vazio", Toast.LENGTH_SHORT).show()
            }
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