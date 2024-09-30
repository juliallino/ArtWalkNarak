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

    //cria o item da lista
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterObra.ObraViewHolder {
        val obras = LayoutInflater.from(context).inflate(R.layout.obras_view, parent, false)
        val holder = ObraViewHolder(obras)
        return holder
    }

    // exibe as views para o usuario
    override fun onBindViewHolder(holder: AdapterObra.ObraViewHolder, position: Int) {
        holder.nomeObra.text = (obras[position].nomeObra)
        holder.imagemObra.setImageResource(obras[position].imagemObra)
    }

    override fun getItemCount(): Int = obras.size

    inner class ObraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeObra = itemView.findViewById<TextView>(R.id.nomeObra)
        val imagemObra = itemView.findViewById<ImageView>(R.id.imagemObra)
    }
}