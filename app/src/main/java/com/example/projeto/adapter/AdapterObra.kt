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
import com.example.projeto.MAExposicaoFuncionario
import com.example.projeto.MAObraUsuario
import com.example.projeto.R
import com.example.projeto.model.Obra

class AdapterObra(
    private val context: Context,
    private val obras: MutableList<Obra>,
    private val isFuncionarioView: Boolean // Usado para diferenciar o layout
) : RecyclerView.Adapter<AdapterObra.ObraViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObraViewHolder {
        val layoutResId = if (viewType == VIEW_TYPE_FUNCIONARIO) {
            R.layout.funcionario_list_obras_view // Layout para funcionário
        } else {
            R.layout.usuario_list_obras_view // Layout para usuário
        }

        val view = LayoutInflater.from(context).inflate(layoutResId, parent, false)
        return ObraViewHolder(view)
    }

    // Liga os dados do item à ViewHolder (chamado repetidamente para cada item da lista)
    override fun onBindViewHolder(holder: ObraViewHolder, position: Int) {
        val obra = obras[position]
        holder.bind(obra)
        holder.itemView.findViewById<ImageView>(R.id.imagemObra).setOnClickListener {
            holder.itemView.context.startActivity(Intent( holder.itemView.context, MAAddObra::class.java)
            )
        }
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int = obras.size

    // Classe interna ViewHolder, responsável por armazenar as Views de cada item
    inner class ObraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeObra = itemView.findViewById<TextView>(R.id.nomeExposicao)
        private val imagemObra = itemView.findViewById<ImageView>(R.id.imagemObra)
        private val editObra = itemView.findViewById<ImageView>(R.id.editObra)

        // Método para associar os dados da obra ao item da view
        fun bind(obra: Obra) {
            nomeObra?.text = obra.nomeObra
            imagemObra.setImageResource(obra.imagemObra)

            // Se for a view do funcionário, mostra o botão de editar
            if (isFuncionarioView) {
                editObra.setImageResource(obra.editObra)
                        editObra.visibility = View.VISIBLE
            } else {
                editObra.visibility = View.GONE // Oculta se não for funcionário
            }
        }

    }
}
