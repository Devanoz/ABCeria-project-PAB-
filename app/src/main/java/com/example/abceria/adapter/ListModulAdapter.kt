package com.example.abceria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.model.materi.Materi
import com.example.abceria.model.modul.Modul
import com.example.abceria.model.user.User

class ListModulAdapter(private val dataSet: java.util.ArrayList<Modul>):
    RecyclerView.Adapter<ListModulAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(modul: Modul){
            val tvNamaModul : TextView = itemView.findViewById(R.id.list_tv_modul)
            //bakal ditambahin desc modul kalau fragmen detail modul nya udah ada
            tvNamaModul.text = modul.title
            //bakal ditambahin desc modul kalau fragmen detail modul nya udah ada

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_rv_modul,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}