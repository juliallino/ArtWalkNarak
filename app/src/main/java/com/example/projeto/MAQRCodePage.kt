package com.example.projeto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class MAQRCodePage : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var botaoVoltarTela: ImageButton
    private lateinit var scannerView: ZXingScannerView
    private var obraId: String? = null
    private var exposicaoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_qrcode_page)
        val toolbar: Toolbar = findViewById(R.id.QRcodeTool)
        setSupportActionBar(toolbar)

        botaoVoltarTela = findViewById(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }

        // Verifica permissões de câmera
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }


        val idExposicao = intent.getStringExtra("idExposicao")
        val idObra = intent.getStringExtra("idObra")
        Log.d("MAObraUsuario", "idExposicao: $idExposicao, idObra: $idObra")
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        // Recebe o ID da obra passada pela intent
        obraId = intent.getStringExtra("idObra")
        exposicaoId = intent.getStringExtra("idExposicao")
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

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this) // Define o handler para capturar o resultado
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera() // Para a câmera ao sair da tela
    }

    override fun handleResult(rawResult: Result?) {
        val scannedData = rawResult?.text

        if (scannedData != null && scannedData == obraId) {
            // QR Code válido, salvar como visualizado
            onQRCodeScanned(obraId!!)
        } else {
            Toast.makeText(this, "QR Code inválido!", Toast.LENGTH_SHORT).show()
            scannerView.resumeCameraPreview(this) // Continua a leitura
        }
    }

    private fun onQRCodeScanned(obraId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val userRef = FirebaseFirestore.getInstance().collection("usuarios").document(userId)
            val viewedArtRef = userRef.collection("Obra").document(obraId)

            viewedArtRef.set(mapOf("viewed" to true)).addOnSuccessListener {
                    Toast.makeText(this, "QR Code validado!", Toast.LENGTH_SHORT).show()

                    // Redireciona para a tela da obra
                    val intent = Intent(this, MAObraUsuario::class.java)
                    Log.d("Debug", "ID de Exposição recebido na MAQRCodePage: $exposicaoId")
                    intent.putExtra("idObra", obraId)
                    intent.putExtra("idExposicao", exposicaoId) // Passa o ID da exposição
                    startActivity(intent)
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Erro ao validar QR Code: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}