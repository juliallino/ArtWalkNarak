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
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapter.AdapterExposicao
import com.example.projeto.adapter.AdapterExposicaoHome
import com.example.projeto.adapter.AdapterObra
import com.example.projeto.model.Exposicao
import com.example.projeto.model.Obra
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MAExposicaoUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_exposicao)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerViewSobreExposicoes =
            findViewById<RecyclerView>(R.id.sobreExposicaoRecyclerView)
        recyclerViewSobreExposicoes.layoutManager = GridLayoutManager(this, 5)
        recyclerViewSobreExposicoes.setHasFixedSize(true)

        // Configurar adapter para exposições
        val sobreExposicaoLista: MutableList<Exposicao> = mutableListOf()
        val adapterExposicao = AdapterExposicao(this, sobreExposicaoLista)
        recyclerViewSobreExposicoes.adapter = adapterExposicao

        // Adicionando exposições manualmente
        val centelhas = Exposicao(
            1,
            "CENTELHAS EM MOVIMENTO",
            R.drawable.centelhas,
            0, // Sem botão de edição para o usuário
            "A exposição Centelhas em Movimento reúne cerca de 190 obras da coleção de Igor Queiroz Barroso, explorando a arte brasileira do século XX, com foco nas diferentes fases do modernismo."
        )
        sobreExposicaoLista.add(centelhas)

        // RecyclerView para obras
        val recyclerViewObras = findViewById<RecyclerView>(R.id.obrasRecyclerView)
        recyclerViewObras.layoutManager = GridLayoutManager(this, 5)
        recyclerViewObras.setHasFixedSize(true)

        val obras: MutableList<Obra> = mutableListOf()
        val adapterObra = AdapterObra(this, obras, false) // Não permite edição
        recyclerViewObras.adapter = adapterObra

        // Adicionando obras manualmente
        val batman = Obra(
            1,
            "Batman",
            R.drawable.batman,
            0, // Sem botão de edição para o usuário
            "Super-Herói de Gotham"
        )
        val arqueiro = Obra(
            2,
            "Aruqueiro Verde",
            R.drawable.arqueiro,
            0, // Sem botão de edição para o usuário
            "Super-Herói de StarCity"
        )
        obras.add(batman)
        obras.add(arqueiro)

        // Atualiza a lista para o adapter
        adapterObra.notifyDataSetChanged()

        //botões

        val botaoVoltarTela = findViewById<ImageButton>(R.id.voltarParaTelaHome)
        botaoVoltarTela.setOnClickListener{
            VoltarTela()
        }
        val botaoScanObra =  findViewById<FloatingActionButton>(R.id.scanObra)
        botaoScanObra.setOnClickListener {
            ScanObra()
        }

    }
    private fun VoltarTela() {
        Log.d("Voltar", "Voltando para tela inicial do usuario")
        startActivity(Intent(this, MAHomeFuncionario::class.java))
    }

    private fun ScanObra() {
        Log.d("Scan obra", "Indo para tela de scan")
        startActivity(Intent(this, MAQRCodePage::class.java))
    }

    //entrar na obra
}

