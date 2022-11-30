package com.example.abceria.Activity.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abceria.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
    }
}