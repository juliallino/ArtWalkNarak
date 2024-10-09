package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAAddExposicao
import com.example.projeto.MAExposicaoFuncionario
import com.example.projeto.MAExposicaoUsuario
import com.example.projeto.R
import com.example.projeto.model.Exposicao

class AdapterExposicaoHome(
    private val context: Context,
    private val exposicoes: MutableList<Exposicao>,
    private val isFuncionarioView: Boolean // Usado para diferenciar o layout
) : RecyclerView.Adapter<AdapterExposicaoHome.ExposicaoViewHolder>() {

    // Define dois tipos de View, um para o funcionário e outro para o usuário
    companion object {
        const val VIEW_TYPE_FUNCIONARIO = 1
        const val VIEW_TYPE_USUARIO = 2
    }

    override fun getItemViewType(position: Int): Int {
        // Retorna o tipo da View com base na variável isFuncionarioView
        return if (isFuncionarioView) VIEW_TYPE_FUNCIONARIO else VIEW_TYPE_USUARIO
    }

    // Cria uma nova View para o item da lista (chamado quando necessário)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposicaoViewHolder {
        val layoutResId = if (viewType == VIEW_TYPE_FUNCIONARIO) {
            R.layout.funcionario_list_exposicoes_home_view // Layout para funcionário
        } else {
            R.layout.usuario_list_exposicoes_home_view // Layout para usuário
        }

        val view = LayoutInflater.from(context).inflate(layoutResId, parent, false)
        return ExposicaoViewHolder(view)
    }

    // Liga os dados do item à ViewHolder (chamado repetidamente para cada item da lista)
    override fun onBindViewHolder(holder: ExposicaoViewHolder, position: Int) {
        val exposicao = exposicoes[position]
        // Chama o método bind para associar os dados ao ViewHolder
        holder.bind(exposicao)

        holder.itemView.findViewById<ImageView>(R.id.imagemExposicao).setOnClickListener {
            holder.itemView.context.startActivity(Intent( holder.itemView.context,MAExposicaoFuncionario::class.java))
        }

        holder.itemView.findViewById<ImageView>(R.id.editExposicao).setOnClickListener {
            holder.itemView.context.startActivity(Intent( holder.itemView.context,MAAddExposicao::class.java))
        }

    }


    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = exposicoes.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ExposicaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeExposicao = itemView.findViewById<TextView>(R.id.nomeExposicao)
        private val imagemExposicao = itemView.findViewById<ImageView>(R.id.imagemExposicao)
        private val editExposicao = itemView.findViewById<ImageView>(R.id.editExposicao) // Pode ser oculto dependendo do tipo de view

        // Método para associar os dados da exposição ao item da view
        fun bind(exposicao: Exposicao) {
            nomeExposicao.text = exposicao.nomeExposicao
            imagemExposicao.setImageResource(exposicao.imagemExposicao)
            // Se for a view do funcionário, mostra o botão de editar
            if (isFuncionarioView) {
                editExposicao.setImageResource(exposicao.editExposicao)
                editExposicao.visibility = View.VISIBLE

            } else {
                editExposicao.visibility = View.GONE // Oculta se não for funcionário
            }
        }
    }
}
