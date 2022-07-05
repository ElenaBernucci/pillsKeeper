package com.example.PillsKeeper.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Dottore
import com.example.PillsKeeper.model.DottoreItem
import com.example.PillsKeeper.model.Farmaco
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.recycler_row_dottore.view.*

class DottoriAdapter(var context: Context,
                     private val list: List<Dottore>,
                     private val listener: OnItemClickListener
): RecyclerView.Adapter<DottoriAdapter.ViewHolder>() {

    val storage = Firebase.storage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_dottore,
        parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        val httpsReference = storage.getReferenceFromUrl(currentItem.imageURL)
        val image: Long = 512 * 512

        httpsReference.getBytes(image).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            val nomeCognome = currentItem.nomeD + " " + currentItem.cognomeD

            holder.immagine.setImageBitmap(bitmap)
            holder.nome.text = nomeCognome
        }.addOnFailureListener{
            Log.d("Dottori Adapter", "Errore caricamento immagine")
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val immagine = itemView.immagineDottore
        val nome = itemView.NomeDottore

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onBackPressed(v: View?)
    }
}