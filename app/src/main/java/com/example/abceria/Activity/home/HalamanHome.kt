package com.example.abceria.Activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.user.User
import com.example.abceria.state.UserState


class HalamanHome : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private val fireStore = DB.getFirestoreInstance()
    private val currentUser = Auth.getAuthInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_home)
        supportActionBar?.hide()

        tvUsername = findViewById(R.id.home_tv_username)
       setProfileUsername()
    }




    private fun setProfileUsername(){
        fireStore.collection("user").document(currentUser!!.uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            tvUsername.text = user?.username
        }
    }



}