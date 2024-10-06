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

class AdapterObra(private val context: Context, private val obras: MutableList<Obra>): RecyclerView.Adapter<AdapterObra.ObraViewHolder>() {
    // Cria uma nova View para o item da lista (chamado quando necessário)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterObra.ObraViewHolder {
        val obras = LayoutInflater.from(context).inflate(R.layout.funcionario_list_obras_view, parent, false)
        val holder = ObraViewHolder(obras)
        return holder
    }

    // Liga os dados do item à ViewHolder (chamado repetidamente para cada item da lista)
    // quem exibe as views para o usuario
    override fun onBindViewHolder(holder: AdapterObra.ObraViewHolder, position: Int) {
        holder.nomeObra.text = (obras[position].nomeObra)
        holder.imagemObra.setImageResource(obras[position].imagemObra)
    }
    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obras.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ObraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeObra = itemView.findViewById<TextView>(R.id.nomeExposicao)
        val imagemObra = itemView.findViewById<ImageView>(R.id.imagemObra)
    }
}