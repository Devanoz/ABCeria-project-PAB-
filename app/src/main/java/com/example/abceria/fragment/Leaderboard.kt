package com.example.abceria.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.adapter.LeaderboardAdapter
import com.example.abceria.db.DB
import com.example.abceria.model.user.User


class Leaderboard : Fragment() {

    private val firestore = DB.getFirestoreInstance()
    private lateinit var rvLeaderboard: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val userList:ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun getUserList() {
        firestore.collection("user").get().addOnSuccessListener {
            it.forEach{ document ->
                val user = User()
                user.username = document.get("username").toString()
                user.fullName = document.get("fullName").toString()
                user.score = Integer.parseInt(document.get("score").toString())
                userList.add(user)
            }
            leaderboardAdapter = LeaderboardAdapter(userList)
            rvLeaderboard.adapter = leaderboardAdapter
        }


    }

    private fun initRecyclerView(view: View){
        rvLeaderboard = view.findViewById(R.id.leaderboard_rv_leaderboard)
        rvLeaderboard.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        getUserList()

    }


}