package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAAddObra
import com.example.projeto.R
import com.example.projeto.model.Obra

class AdapterObraFunc(
    private val context: Context,
    private val obras: MutableList<Obra>)
    : RecyclerView.Adapter<AdapterObraFunc.ObrasViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.funcionario_list_obras_view, parent, false)
        return ObrasViewHolder(view)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obras.size

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = obras[position]
        holder.bind(obra)
        holder.imagemObra.setOnClickListener { context.startActivity(Intent(context, MAAddObra::class.java)) }
        holder.editObra.setOnClickListener { context.startActivity(Intent(context, MAAddObra::class.java)) }

    }


    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemObra: ImageView = itemView.findViewById(R.id.imagemObra)
        val editObra: ImageView = itemView.findViewById(R.id.editObra)

        // Método para associar os dados da exposição ao item da view
        fun bind(obra: Obra) {
            imagemObra.setImageResource(obra.imagemObra)
            editObra.setImageResource(obra.editObra)
        }
    }
}