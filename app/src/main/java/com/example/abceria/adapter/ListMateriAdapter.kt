package com.example.abceria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.model.materi.Materi
import com.example.abceria.model.user.User

class ListMateriAdapter(private val dataSet: java.util.ArrayList<Materi>):
    RecyclerView.Adapter<ListMateriAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(materi: Materi){
            val tvNamaMateri : TextView = itemView.findViewById(R.id.list_tv_materi)
            val tvDscMateri : TextView = itemView.findViewById(R.id.list_tv_dscmateri)


            tvNamaMateri.text = materi.title
            tvDscMateri.text = materi.description


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_rv_materi,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}