package com.example.abceria.Activity.settings

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.Activity.auth.Login
import com.example.abceria.Activity.home.HalamanHome
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.user.User
import com.example.abceria.state.StateFactory
import com.example.abceria.state.UserState

class Settings : AppCompatActivity() {

    private val auth = Auth.getAuthInstance()
    private lateinit var btnLogout: Button
    private val currentUser = auth.currentUser
    private val firestore = DB.getFirestoreInstance()
    private val firebaseStorage = DB.getStorageInstance()
    //components
    private lateinit var btnEditProfile: Button
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var imvProfile: ImageView

    //userState
    private val userState = StateFactory.getUserStateInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initComponents()
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        btnEditProfile.setOnClickListener {
            val intent = Intent(this,editProfile::class.java)
            this.startActivity(intent)
        }

//        auth.addAuthStateListener {
//            if(currentUser == null){
//                val intent = Intent(this, Login::class.java)
//                startActivity(intent)
//            }
//
//        }

    }

    private fun initComponents(){
        btnLogout = findViewById(R.id.settings_btn_logout)
        btnEditProfile = findViewById(R.id.settings_btn_edit_profile)
        tvFullName = findViewById(R.id.settings_tv_fullName)
        tvEmail = findViewById(R.id.settings_tv_email)
        imvProfile = findViewById(R.id.settings_imv_profileImage)
    }

    private fun setProfileImage(){
        imvProfile.setImageBitmap(userState.profilePicture)

    }

    private fun setProfileDetail(){
        tvFullName.text = userState.fullName
        tvEmail.text = userState.email
    }

    override fun onResume() {
        super.onResume()
        setProfileDetail()
        setProfileImage()
    }

}

