package com.example.projeto.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.MAObraUsuario
import com.example.projeto.MAQRCodePage
import com.example.projeto.R
import com.example.projeto.model.Obra
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdapterObraUsu(
    private val context: Context, private var obras: List<Obra>
) : RecyclerView.Adapter<AdapterObraUsu.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.usuario_list_obras_view, parent, false)
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

            Log.d("Debug", "Clique na obra: ObraId = $obraId, ExposicaoId = $exposicaoId")

            // Certifica-se de que os IDs não são nulos
            if (obraId != null && exposicaoId != null) {
                // Verifica se a obra foi visualizada
                isArtViewed(context, obraId) { isViewed ->
                    if (isViewed) {
                        Log.d(
                            "ClickDebug",
                            "Obra visualizada. Navegando para a página de informações."
                        )
                        navigateToInfoPage(exposicaoId, obraId)
                    } else {
                        Log.d(
                            "ClickDebug",
                            "Obra não visualizada. Navegando para a página de QR Code."
                        )
                        navigateToQRCodePage(exposicaoId, obraId)
                    }
                }
            } else {
                Log.e("ClickError", "IDs da obra ou da exposição estão nulos. Verifique os dados!")
            }
        }
    }


    private fun navigateToInfoPage(exposicaoId: String?, obraId: String?) {
        if (exposicaoId != null && obraId != null) {
            val intent = Intent(context, MAObraUsuario::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            intent.putExtra("idObra", obraId)
            Log.d("Intent", "Intent criada com ExposicaoId: $exposicaoId e ObraId: $obraId")
            context.startActivity(intent)
        } else {
            Log.d("IntentError", "ExposicaoId ou ObraId está null")
        }
    }


    private fun navigateToQRCodePage(exposicaoId: String?, obraId: String?) {
        if (exposicaoId != null && obraId != null) {
            val intent = Intent(context, MAQRCodePage::class.java)
            intent.putExtra("idExposicao", exposicaoId)
            intent.putExtra("idObra", obraId)
            context.startActivity(intent)
        }
    }


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
            obra.idObra?.let { obraId ->
                isArtViewed(context, obraId) { isViewed ->
                    // Verifica se a obra foi visualizada (Booleano)
                    updateImageColor(obra, isViewed)
                }
            }
        }

        // Função para atualizar a saturação da imagem com base no estado de visualização
        fun updateImageColor(obra: Obra, isViewed: Boolean) {
            val bitmap = base64ToBitmap(obra.imagemObra ?: "")
            if (bitmap != null) {
                if (isViewed) {
                    imagemObra.setColorFilter(null) // Remove o filtro, deixando com cores
                } else {
                    val colorMatrix = ColorMatrix()
                    colorMatrix.setSaturation(0f) // Preto e branco
                    val filter = ColorMatrixColorFilter(colorMatrix)
                    imagemObra.setColorFilter(filter) // Aplica filtro de preto e branco
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


    private fun isArtViewed(context: Context, obraId: String, callback: (Boolean) -> Unit) {
        val userId =
            FirebaseAuth.getInstance().currentUser?.uid // autenticação de usuário
        if (userId != null) {
            val userRef = FirebaseFirestore.getInstance().collection("usuarios").document(userId)
            val viewedArtRef = userRef.collection("Obra").document(obraId)

            viewedArtRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val isViewed = document.getBoolean("viewed") == true
                        callback(isViewed) // Retorna o resultado para o callback
                    } else {
                        callback(false) // Se o documento não existe, assume que não foi visualizada
                    }
                }.addOnFailureListener { e ->
                    Log.w("Firestore", "Erro ao verificar visualização da obra", e)
                    callback(false) // Em caso de falha, assume que não foi visualizada
                }
        } else {
            callback(false) // Se não houver usuário logado, assume que não foi visualizada
        }
    }
}