package com.example.abceria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.model.user.User

class LeaderboardAdapter(private val dataSet: java.util.ArrayList<User>):
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(user: User){
            val tvFullname : TextView = itemView.findViewById(R.id.leaderboard_tv_fullName)
            val tvUsername : TextView = itemView.findViewById(R.id.leaderboard_tv_userName)
            val tvPoints: TextView = itemView.findViewById(R.id.leaderboard_tv_points)

            tvFullname.text = user.username
            tvUsername.text = user.username
            tvPoints.text = "213 Pts"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_rv_user,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}