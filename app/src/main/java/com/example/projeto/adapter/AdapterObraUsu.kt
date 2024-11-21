package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
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

    fun setFilteredList(obras: List<Obra>) {
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

            // Salvar que a obra foi visualizada
            if (obraId != null) {
                saveViewedArt(context, obraId)
            }

            // Atualizar o estado da imagem (saturação)
            holder.updateImageColor(obra)

            // Intent para navegar à tela da obra
            val intent = Intent(context, MAObraUsuario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            intent.putExtra("idObra", obraId)
            context.startActivity(intent)
        }
    }

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ObrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemObra: ImageView = itemView.findViewById(R.id.imagemObra)

        fun bind(obra: Obra) {
            obra.imagemObra?.let {
                val bitmap = base64ToBitmap(it)
                if (bitmap != null) {
                    imagemObra.setImageBitmap(bitmap)
                }
            }

            // Verifica se a obra foi visualizada e aplica a saturação
            val isViewed = obra.idObra?.let { isArtViewed(context, it) }
            if (isViewed != null) {
                updateImageColor(obra, isViewed)
            }
        }
        // Função para atualizar a saturação da imagem com base no estado de visualização
        fun updateImageColor(obra: Obra, isViewed: Boolean = false) {
            val bitmap = base64ToBitmap(obra.imagemObra ?: "")
            if (bitmap != null) {
                if (isViewed) {
                    imagemObra.setColorFilter(null) // Remove o filtro, deixando com cores
                } else {
                    val colorMatrix = ColorMatrix()
                    colorMatrix.setSaturation(0f) // Preto e branco
                    val filter = ColorMatrixColorFilter(colorMatrix)
                    imagemObra.setColorFilter(filter)
                }
                imagemObra.setImageBitmap(bitmap)
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
    // Função para salvar a obra como visualizada
    private fun saveViewedArt(context: Context, artId: String) {
        val sharedPreferences = context.getSharedPreferences("ArtGallery", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(artId, true)
        editor.apply()
    }
    // Função para verificar se a obra foi visualizada
    private fun isArtViewed(context: Context, artId: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("ArtGallery", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(artId, false)
    }
}
