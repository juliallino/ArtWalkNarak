package com.example.projeto.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
    private val obrasList: MutableList<Obra>)
    : RecyclerView.Adapter<AdapterSobreObra.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.usuario_list_sobre_obra_view, parent, false)
        return ObrasViewHolder(view)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obrasList.size

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = obrasList[position]
        holder.bind(obra)
        holder.nomeObra.text = obra.nomeObra
        holder.descricaoObra.text = obra.descricaoObra
    }

    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeObra: TextView = itemView.findViewById(R.id.nomeObra)
        val imagemObra: ImageView = itemView.findViewById(R.id.imagemObra)
        val descricaoObra: TextView = itemView.findViewById(R.id.descricaoObra)

        fun bind(obra: Obra) {
            nomeObra.text = obra.nomeObra
            descricaoObra.text = obra.descricaoObra
            obra.imagemObra?.let {
                    val bitmap = base64ToBitmap(it)
                    if (bitmap != null) {
                        imagemObra.setImageBitmap(bitmap)
                    }
                }
            }
        }

        private fun base64ToBitmap(base64String: String): Bitmap? {
            return try {
                val decodedString: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }