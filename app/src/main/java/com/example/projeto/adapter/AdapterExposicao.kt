package com.example.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.model.Exposicao

class AdapterExposicao(
    private val context: Context,
    private val exposicoes: MutableList<Exposicao>
) : RecyclerView.Adapter<AdapterExposicao.ExposicaoViewHolder>() {

    // Cria uma nova View para o item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposicaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_sobre_exposicao_view, parent, false)
        return ExposicaoViewHolder(view)
    }

    // Liga os dados do item à ViewHolder
    override fun onBindViewHolder(holder: ExposicaoViewHolder, position: Int) {
        val exposicao = exposicoes[position]
        holder.bind(exposicao)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = exposicoes.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeExposicao: TextView = itemView.findViewById(R.id.nomeExposicao)
        private val descricaoExposicao: TextView = itemView.findViewById(R.id.descricaoExposicao)

        // Método para associar os dados da exposição ao item da view
        fun bind(exposicao: Exposicao) {
            nomeExposicao.text = exposicao.nomeExposicao
            descricaoExposicao.text = exposicao.descricaoExposicao
        }
    }
}
