package com.example.abceria.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.Activity.settings.Settings
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.user.User
import com.example.abceria.state.StateFactory

class Home : Fragment() {

    private lateinit var tvUsername: TextView
    private val fireStore = DB.getFirestoreInstance()
    private val currentUser = Auth.getAuthInstance().currentUser
    private val firebaseStorage = DB.getStorageInstance()
    //components
    private lateinit var imvProfile: ImageView

    //userState
    private val userState = StateFactory.getUserStateInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfileImage()

        tvUsername = view.findViewById(R.id.home_tv_username)
        imvProfile = view.findViewById(R.id.home_imv_profile)
        setProfileUsername()

        imvProfile.setOnClickListener{
            val intent = Intent(this.context, Settings::class.java)
            this.startActivity(intent)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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
                userState.username = user?.username
                userState.fullName = user?.fullName
                userState.email = currentUser.email
//                tvUsername.text = userState.username
                tvUsername.text = user?.username
            } else {
                //current data null
            }
        }

    }

    private fun setProfileImage(){
        val storageRef = firebaseStorage.reference
        storageRef.child("/profile-image/013bb0dd-4979-4b56-84a9-18f10d4c9061").getBytes(Long.MAX_VALUE).addOnSuccessListener {
            val bitmapProfile = BitmapFactory.decodeByteArray(it,0,it.size)
            userState.profilePicture = bitmapProfile
            imvProfile.setImageBitmap(userState.profilePicture)
        }

    }


}