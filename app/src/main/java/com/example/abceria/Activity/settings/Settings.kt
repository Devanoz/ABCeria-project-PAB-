package com.example.abceria.Activity.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.Activity.auth.Login
import com.example.abceria.R

class Settings : AppCompatActivity() {

    private val auth = Auth.getAuthInstance()
    private lateinit var btnLogout: Button
    private val currentUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnLogout = findViewById(R.id.settings_btn_logout)

        btnLogout.setOnClickListener {
            auth.signOut()
        }

        auth.addAuthStateListener {
            if(currentUser == null){
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

        }
    }

}

