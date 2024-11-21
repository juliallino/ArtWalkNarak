package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MAQRCodePage : AppCompatActivity() {
    private lateinit var  botaoVoltarTela : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_qrcode_page)

        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
    }
    private fun VoltarTela() {
        val exposicaoId = intent.getStringExtra("idExposicao")
        Log.d("Debug", "ID de Exposição recebido: $exposicaoId")

        if (exposicaoId != null) {
            val intent = Intent(this, MAExposicaoUsuario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            startActivity(intent)
        } else {
            Log.d("Debug", "ID de Exposição não encontrado")
        }
    }
}