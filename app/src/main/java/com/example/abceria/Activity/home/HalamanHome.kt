package com.example.abceria.Activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.Activity.settings.Settings
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.user.User
import com.example.abceria.state.UserState
import com.google.firebase.firestore.EventListener


class HalamanHome : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private val fireStore = DB.getFirestoreInstance()
    private val currentUser = Auth.getAuthInstance().currentUser

    //components
    private lateinit var imvProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_home)
        supportActionBar?.hide()

        tvUsername = findViewById(R.id.home_tv_username)
        imvProfile = findViewById(R.id.home_imv_profile)
       setProfileUsername()

        imvProfile.setOnClickListener{
            val intent = Intent(this,Settings::class.java)
            this.startActivity(intent)
        }
    }

    private fun setProfileUsername(){
        val userNameRef = fireStore.collection("user").document(currentUser!!.uid);
        userNameRef.addSnapshotListener{ snapshot, e ->
            if (e != null) {
               //
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val user = snapshot.toObject(User::class.java)
                tvUsername.text = user?.username
            } else {
                //current data null
            }
        }

    }



}