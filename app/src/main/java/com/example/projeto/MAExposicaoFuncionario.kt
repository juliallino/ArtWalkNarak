package com.example.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterObra
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra

class MAExposicaoFuncionario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.funcionario_exposicao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerViewSobreExposicoes =
            findViewById<RecyclerView>(R.id.sobreExposicaoRecyclerView)

        recyclerViewSobreExposicoes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //otimiza a lista
        recyclerViewSobreExposicoes.setHasFixedSize(true)
        //configurar adapter
        val sobreExposicaoLista: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicao(this, sobreExposicaoLista)
        recyclerViewSobreExposicoes.adapter = adapterExposicao
        //adicionando manualmente
        val centelhas = Exposicao(
            1,
            "CENTELHAS EM MOVIMENTO",
            R.drawable.centelhas,
            R.drawable.edit,
            "A exposição Centelhas em Movimento reúne cerca de 190 obras da coleção de Igor Queiroz Barroso, explorando a arte brasileira do século XX, com foco nas diferentes fases do modernismo.\\n\\n A curadoria, assinada por Paulo Miyada e Tiago Gualberto, busca criar diálogos visuais entre as obras, sem seguir uma ordem cronológica, estimulando reflexões sobre o movimento modernista e suas transformações."
        )
        sobreExposicaoLista.add(centelhas)


        val recyclerViewObras = findViewById<RecyclerView>(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 5)
        recyclerViewObras.setHasFixedSize(true)

        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterObra(this, obras)
        recyclerViewObras.adapter = adapterObra
        //adicionando manualmente
        val batman = Obra(
            1,
            "Batman",
            R.drawable.batman,
            R.drawable.edit,
            "Super-Herói de Gotham"
        )
        val arqueiro = Obra(
            2,
            "Aruqueiro Verde",
            R.drawable.arqueiro,
            R.drawable.edit,
            "Super-Herói de StarCity"
        )
        obras.add(batman)
        obras.add(arqueiro)
        // Atualizar a lista para o adapter
        adapterObra.notifyDataSetChanged()

        //botões
        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener {
            VoltarTela()
        }

//        val botaoEditObra = findViewById<ImageButton>(R.id.imagemObra)
//        botaoEditObra.setOnClickListener{
//           EditEObra()
//      }

        val botaoAddObra = findViewById<ImageButton>(R.id.addObra)
        botaoAddObra.setOnClickListener {
            AddObra()
        }
    }

    //func de botões
    private fun VoltarTela() {
        Log.d("Voltar home Funcionario", "Voltando para tela inicial do funcionário")
        val intent = Intent(this, MAHomeFuncionario::class.java)
        startActivity(intent)
    }

//    private fun EditEObra() {
//        Log.d("Editar", "Indo para tela de editar obra")
//        val intent = Intent(this, MAAddObra::class.java)
//        startActivity(intent)
//    }

    private fun AddObra() {
        Log.d("ADD", "tela add obra")
        val intent = Intent(this, MAAddObra::class.java)
        startActivity(intent)
    }

}