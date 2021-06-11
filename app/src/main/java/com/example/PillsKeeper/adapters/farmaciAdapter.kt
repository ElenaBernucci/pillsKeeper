package com.example.PillsKeeper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Farmaco
import kotlinx.android.synthetic.main.farmaci_row.view.*

class farmaciAdapter (var context: Context, list: List<Farmaco>) :RecyclerView.Adapter<ViewHolder>() {
    private var list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.farmaci_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(farmaco: Farmaco){

            itemView.NomeFarmaco.text = farmaco.nomeFarmaco
            itemView.dose.text = farmaco.quantoNePrendi
            Glide.with(context).load(farmaco.imageURL).into(itemView.immagineFarmaco)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}