package com.example.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.model.ExposicaoHome

// classe que gerencia todos os recursos dinâmicos, ou seja, todas as coleções de inserção, exclusão ou edição de dados
// ele que comunica o ViewHolder(quem gerencia cada celula da lista) e é responsavel pelo produto final
class AdapterExposicao(private val context: Context, private val exposicoes: MutableList<ExposicaoHome>): RecyclerView.Adapter<AdapterExposicao.ExposicaoViewHolder>() {
    
    //cria o item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterExposicao.ExposicaoViewHolder {
        val exposicao_view = LayoutInflater.from(context).inflate(R.layout.exposicoes_home_view, parent, false)
        val holder = ExposicaoViewHolder(exposicao_view)
        return holder
    }

    // exibe as views para o usuario
    override fun onBindViewHolder(holder: AdapterExposicao.ExposicaoViewHolder, position: Int) {
        holder.nomeExposicao.text = (exposicoes[position].nomeExposicao)
        holder.imagemExposicao.setImageResource(exposicoes[position].imagemExposicao)
        holder.editExposicao.setImageResource(exposicoes[position].editExposicao)
    }

    override fun getItemCount(): Int = exposicoes.size

    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeExposicao = itemView.findViewById<TextView>(R.id.nomeObra)
        val imagemExposicao = itemView.findViewById<ImageView>(R.id.imagemObra)
        val editExposicao = itemView.findViewById<ImageView>(R.id.editExposicao)
    }
}