package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAObraUsuario
import com.example.projeto.R
import com.example.projeto.model.Obra

class AdapterObraUsu(
    private val context: Context,
    private var obras: List<Obra>
) : RecyclerView.Adapter<AdapterObraUsu.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.usuario_list_obras_view, parent, false)
        return ObrasViewHolder(view)
    }
    fun setFilteredList(obras: List<Obra>){
        this.obras = obras
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = obras.size

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = obras[position]
        holder.bind(obra)

        holder.imagemObra.setOnClickListener {
            val obraId = obra.idObra
            val exposicaoId = obra.idExposicao
            val intent = Intent(context, MAObraUsuario::class.java)
            intent.getStringExtra("idExposicao")
            intent.putExtra("idExposicao", exposicaoId)
            intent.putExtra("idObra", obraId)
            context.startActivity(intent)
        }

    }

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemObra: ImageView = itemView.findViewById(R.id.imagemObra)

        // Método para associar os dados da obra ao item da view
        fun bind(obra: Obra) {
            // Se a imagem for Base64
            obra.imagemObra?.let {
                val bitmap = base64ToBitmap(it)
                if (bitmap != null) {
                    imagemObra.setImageBitmap(bitmap)
                }
            }
        }

        // Função para converter a string Base64 para Bitmap
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
}
