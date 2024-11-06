package com.example.projeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MALoginUsuario : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSingInCliente : GoogleSignInClient
    private lateinit var googleBnt1 : ImageButton
    private lateinit var googleBnt2 : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_login)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.gcm_defaultSenderId))
            .requestEmail()
            .build()
        googleSingInCliente = GoogleSignIn.getClient(this,gso)

        googleBnt1 = findViewById<ImageButton>(R.id.google)
        googleBnt2 = findViewById<ImageButton>(R.id.google2)

        val botaoLoginComoFuncionario = findViewById<Button>(R.id.botaoDeLoginComoFuncionario)
        botaoLoginComoFuncionario.setOnClickListener {
            Toast.makeText(this, "Login como funcionario", Toast.LENGTH_SHORT).show()
            irParaLoginComoFuncionario()
        }

    }

    override fun onStart() {
        super.onStart()
        googleBnt1.setOnClickListener{
            signInGoogle()
        }
    }

    private fun irParaLoginComoFuncionario() {
        Log.d("Login", "Indo para tela de login do funcionário")
        val intent = Intent(this, MALoginFuncionario::class.java)
        startActivity(intent)
    }
    private fun signInGoogle(){
        val signInIntente = googleSingInCliente.signInIntent
        launcher.launch(signInIntente)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account :GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent: Intent = Intent(this, MAHomeUsuario::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

}