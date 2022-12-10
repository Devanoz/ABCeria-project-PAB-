package com.example.abceria.Activity.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.Activity.auth.Login
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.user.User

class Settings : AppCompatActivity() {

    private val auth = Auth.getAuthInstance()
    private lateinit var btnLogout: Button
    private val currentUser = auth.currentUser
    private val firestore = DB.getFirestoreInstance()
    //components
    private lateinit var btnEditProfile: Button
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
        initComponents()
        setProfileDetailListener()

        btnLogout.setOnClickListener {
            auth.signOut()
        }
        btnEditProfile.setOnClickListener {
            val intent = Intent(this,editProfile::class.java)
            this.startActivity(intent)
        }

        auth.addAuthStateListener {
            if(currentUser == null){
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

        }
    }

    private fun initComponents(){
        btnLogout = findViewById(R.id.settings_btn_logout)
        btnEditProfile = findViewById(R.id.settings_btn_edit_profile)
        tvFullName = findViewById(R.id.settings_tv_fullName)
        tvEmail = findViewById(R.id.settings_tv_email)
    }

    private fun setProfileDetailListener(){
        val userUid = currentUser?.uid
        if (userUid != null) {
            firestore.collection("user").document(userUid).addSnapshotListener{ snapshot, e ->
                if (e != null) {
                    //
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java)
                    tvFullName.text = user?.fullName
                    tvEmail.text = currentUser?.email
                } else {
                    //current data null
                }
            }
        }
    }

}

