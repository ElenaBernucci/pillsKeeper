package com.example.PillsKeeper.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Farmaco
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.recycler_row.view.*


class farmaciAdapter (var context: Context,
                      private val list: List<Farmaco>,
                      private val listener: OnItemClickListener
                      ) :RecyclerView.Adapter<farmaciAdapter.ViewHolder>() {

    val storage = Firebase.storage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        Log.d("AccessoAdapter", "Inizio a creare il tutto")
        var view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        val httpsReference = storage.getReferenceFromUrl(currentItem.imageURL)
        val image: Long = 512 * 512
        httpsReference.getBytes(image).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)

            holder.immagine.setImageBitmap(bitmap)
            holder.nomeFarmaco.text = currentItem.nomeFarmaco
            holder.dose.text = currentItem.dosaggio.toString()
        }.addOnFailureListener{

        }
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), OnClickListener{

        /*fun bind(farmaco: Farmaco){

            itemView.NomeFarmaco.text = farmaco.nomeFarmaco
            itemView.dose.text = farmaco.quantoNePrendi
            Glide.with(context).load(farmaco.imageURL).into(itemView.immagineFarmaco)

        }*/

        val immagine: ImageView = itemView.immagineFarmaco
        val nomeFarmaco: TextView = itemView.NomeFarmaco
        val dose: TextView = itemView.dose

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}