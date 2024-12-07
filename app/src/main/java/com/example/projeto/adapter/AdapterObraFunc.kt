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
import com.example.projeto.MAAddObra
import com.example.projeto.R
import com.example.projeto.model.Obra

class AdapterObraFunc(
    private val context: Context, private var obras: List<Obra>
) : RecyclerView.Adapter<AdapterObraFunc.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.funcionario_list_obras_view, parent, false)
        return ObrasViewHolder(view)
    }

    fun setFilteredList(obras: List<Obra>) {
        this.obras = obras
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = obras[position]
        holder.bind(obra)

        holder.imagemObra.setOnClickListener {
            val obraId = obra.idObra
            val exposicaoId = obra.idExposicao
            val intent = Intent(context, MAAddObra::class.java)
            intent.putExtra("idObra", obraId)
            intent.putExtra("idExposicao", exposicaoId)
            context.startActivity(intent)
        }

        holder.editObra.setOnClickListener {
            val obraId = obra.idObra
            val exposicaoId = obra.idExposicao
            val intent = Intent(context, MAAddObra::class.java)
            intent.putExtra("idObra", obraId)
            intent.putExtra("idExposicao", exposicaoId)
            context.startActivity(intent)
        }
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obras.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemObra: ImageView = itemView.findViewById(R.id.imagemObra)
        val editObra: ImageView = itemView.findViewById(R.id.editObra)

        // Método para associar os dados da obra ao item da view
        fun bind(obra: Obra) {
            // Converte a string Base64 para Bitmap e define na ImageView da obra
            val imagemBase64 = obra.imagemObra
            imagemBase64?.let {
                val bitmap = base64ToBitmap(it)
                imagemObra.setImageBitmap(bitmap)
            }
            // Define o ícone de edição
            editObra.setImageResource(R.drawable.baseline_edit_24)
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
