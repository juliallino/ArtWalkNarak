package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAExposicaoFuncionario
import com.example.projeto.MAExposicaoUsuario
import com.example.projeto.R
import com.example.projeto.model.Exposicao

class AdapterExposicaoHomeUsu(
    private val context: Context,
    private val exposicoes: MutableList<Exposicao>)
    : RecyclerView.Adapter<AdapterExposicaoHomeUsu.ExposicaoViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposicaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.usuario_list_exposicoes_home_view, parent, false)
        return ExposicaoViewHolder(view)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = exposicoes.size

    override fun onBindViewHolder(holder: ExposicaoViewHolder, position: Int) {
        val exposicao = exposicoes[position]
        // Chama o método bind para associar os dados ao ViewHolder
        holder.bind(exposicao)

        // Configura o clique na imagem para abrir a tela MAExposicaoFuncionario
        holder.imagemExposicao.setOnClickListener {
            context.startActivity(Intent(context, MAExposicaoUsuario::class.java))
        }
    }

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeExposicao: TextView = itemView.findViewById(R.id.nomeExposicao)
        val imagemExposicao: TextView = itemView.findViewById(R.id.imagemExposicao)


        // Método para associar os dados da exposição ao item da view
        fun bind(exposicao: Exposicao) {
            nomeExposicao.text = exposicao.nomeExposicao
            imagemExposicao.text = exposicao.imagemExposicao
        }
    }
}