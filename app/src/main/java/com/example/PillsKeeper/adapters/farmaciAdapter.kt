package com.example.PillsKeeper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Farmaco
import com.example.PillsKeeper.model.FarmacoMinimal
import kotlinx.android.synthetic.main.farmaci_row.view.*


class farmaciAdapter (var context: Context, private val list: List<Farmaco>) :RecyclerView.Adapter<farmaciAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.farmaci_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        /*fun bind(farmaco: Farmaco){

            itemView.NomeFarmaco.text = farmaco.nomeFarmaco
            itemView.dose.text = farmaco.quantoNePrendi
            Glide.with(context).load(farmaco.imageURL).into(itemView.immagineFarmaco)

        }*/

        val immagine: ImageView = itemView.immagineFarmaco
        val nomeFarmaco: TextView = itemView.NomeFarmaco
        val dose: TextView = itemView.dose
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]

        holder.immagine.setImageResource(currentItem.imageURL.toInt())
        holder.nomeFarmaco.text = currentItem.nomeFarmaco
        holder.dose.text = currentItem.dosaggio.toString()
    }

}