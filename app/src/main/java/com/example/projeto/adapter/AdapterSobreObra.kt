package com.example.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.model.Obra

class AdapterSobreObra (
    private val context: Context,
    private val obras: MutableList<Obra>)
    : RecyclerView.Adapter<AdapterSobreObra.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.usuario_list_sobre_obra_view, parent, false)
        return ObrasViewHolder(view)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obras.size

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = obras[position]
        holder.bind(obra)
    }

    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeObra: TextView = itemView.findViewById(R.id.nomeObra)
        val imagemObra: TextView = itemView.findViewById(R.id.imagemObra)
        val descricaoObra: TextView = itemView.findViewById(R.id.descricaoObra)
        fun bind(obra: Obra) {
            nomeObra.text = obra.nomeObra
            imagemObra.text = obra.imagemObra
            descricaoObra.text = obra.descricaoObra
        }
    }

}