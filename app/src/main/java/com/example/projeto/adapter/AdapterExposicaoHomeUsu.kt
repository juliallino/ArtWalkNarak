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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAExposicaoUsuario
import com.example.projeto.R
import com.example.projeto.model.Exposicao

class AdapterExposicaoHomeUsu(
    private val context: Context,
    private var exposicoes: List<Exposicao>,
) : RecyclerView.Adapter<AdapterExposicaoHomeUsu.ExposicaoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposicaoViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.usuario_list_exposicoes_home_view, parent, false)
        return ExposicaoViewHolder(view)
    }

    fun setFilteredList(exposicoes: List<Exposicao>) {
        this.exposicoes = exposicoes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = exposicoes.size

    override fun onBindViewHolder(holder: ExposicaoViewHolder, position: Int) {
        val exposicao = exposicoes[position]
        // Chama o método bind para associar os dados ao ViewHolder
        holder.bind(exposicao)

        // Configura o clique na imagem para abrir a tela MAExposicaoUsuario
        holder.imagemExposicao.setOnClickListener {
            val exposicaoId = exposicao.idExposicao
            val intent = Intent(context, MAExposicaoUsuario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            context.startActivity(intent)
        }
    }

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeExposicao: TextView = itemView.findViewById(R.id.nomeExposicao)
        val imagemExposicao: ImageView = itemView.findViewById(R.id.imagemExposicao)

        // Método para associar os dados da exposição ao item da view
        fun bind(exposicao: Exposicao) {
            nomeExposicao.text = exposicao.nomeExposicao
            // Converte a imagem Base64 para Bitmap e define na ImageView
            val imagemBase64 = exposicao.imagemExposicao
            imagemBase64?.let {
                val bitmap = base64ToBitmap(it)
                imagemExposicao.setImageBitmap(bitmap)
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