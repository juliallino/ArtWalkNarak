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
import com.example.projeto.MAAddExposicao
import com.example.projeto.MAExposicaoFuncionario
import com.example.projeto.R
import com.example.projeto.model.Exposicao

class AdapterExposicaoHomeFunc(
    private val context: Context,
    private var exposicoes: List<Exposicao>,
) : RecyclerView.Adapter<AdapterExposicaoHomeFunc.ExposicaoViewHolder>() {

    // Cria uma nova View para o item da lista (chamado quando necessário)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposicaoViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.funcionario_list_exposicoes_home_view, parent, false)
        return ExposicaoViewHolder(view)
    }
    fun setFilteredList(exposicoes: List<Exposicao>){
        this.exposicoes =exposicoes
        notifyDataSetChanged()
    }

    // Liga os dados do item à ViewHolder (chamado repetidamente para cada item da lista)
    override fun onBindViewHolder(holder: ExposicaoViewHolder, position: Int) {
        val exposicao = exposicoes[position]
        // Chama o método bind para associar os dados ao ViewHolder
        holder.bind(exposicao)

        holder.imagemExposicao.setOnClickListener {
            val exposicaoId = exposicao.idExposicao
            val intent = Intent(context, MAExposicaoFuncionario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            context.startActivity(intent)
        }

        holder.editExposicao.setOnClickListener {
            val exposicaoId = exposicao.idExposicao
            val intent = Intent(context, MAAddExposicao::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            context.startActivity(intent)
        }


    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = exposicoes.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeExposicao: TextView = itemView.findViewById(R.id.nomeExposicao)
        val imagemExposicao: ImageView = itemView.findViewById(R.id.imagemExposicao)
        val editExposicao: ImageView = itemView.findViewById(R.id.editExposicao)

        // Método para associar os dados da exposição ao item da view
        fun bind(exposicao: Exposicao) {
            nomeExposicao.text = exposicao.nomeExposicao
            // Converte a string Base64 para Bitmap e define na ImageView
            val imagemBase64 = exposicao.imagemExposicao
            imagemBase64?.let {
                val bitmap = base64ToBitmap(it)
                imagemExposicao.setImageBitmap(bitmap)
            }
            // Define o ícone de edição
            editExposicao.setImageResource(R.drawable.baseline_edit_24)
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
