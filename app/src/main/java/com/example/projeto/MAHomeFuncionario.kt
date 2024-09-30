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
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.model.ExposicaoHome

class MAHomeFuncionario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_funcionario)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerViewExposicoes = findViewById<RecyclerView>(R.id.recyclerviewExposicoes)

        recyclerViewExposicoes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //otimiza a lista
        recyclerViewExposicoes.setHasFixedSize(true)
        //configurar adapter
        val listaExposcoes: MutableList<ExposicaoHome> = mutableListOf()
        val adapterExposicao = AdapterExposicao(this, listaExposcoes)
        recyclerViewExposicoes.adapter = adapterExposicao

        val centelhasEmMovimento = ExposicaoHome("CENTELHAS EM MOVIMENTO", R.drawable.centelhas, R.drawable.edit, "Exposição centelhas em movimento")
        listaExposcoes.add(centelhasEmMovimento)

        val botaoEdit = findViewById<ImageButton>(R.id.botaoaddexpo)
        botaoEdit.setOnClickListener{
            EditarExposicao()
        }
    }
    private fun EditarExposicao() {
        Log.d("Editar", "Indo para tela de editar exposição")
        val intent = Intent(this, MAEdicaoExposicao::class.java)
        startActivity(intent)
    }
}