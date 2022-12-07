package com.example.abceria.Activity.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.abceria.Activity.leaderboard.LeaderBoard
import com.example.abceria.MainActivity
import com.example.abceria.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.GoogleAuthProvider


class Login : AppCompatActivity() {
    private val auth = Auth.getAuthInstance()

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2
    private var showOneTapUi = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        // Initialize Firebase Auth
        etEmail = findViewById(R.id.login_et_email)
        etPassword = findViewById(R.id.login_et_password)
        btnLogin = findViewById(R.id.login_btn_login)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(getString(R.string.CLIENT_ID))
                .setFilterByAuthorizedAccounts(false)

                .build()
        ).build()

        btnLogin.setOnClickListener{

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when{
                        idToken != null -> {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken,null)
                            auth.signInWithCredential(firebaseCredential).addOnCompleteListener{
                                if(it.isSuccessful){
                                    val intent = Intent(this,LeaderBoard::class.java)
                                    startActivity(intent)
                                }else {
                                    Toast.makeText(this,"Gagal autentikasi dengan google",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        else -> {
                            Toast.makeText(this,"NO TOKEN ID",Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e : ApiException){
                    when(e.statusCode){
                        CommonStatusCodes.CANCELED -> {
                            showOneTapUi = false
                        }
                    }
                }
            }
        }
    }

    private fun loginWithEmailPassword(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                val user = auth.currentUser
                //move to homepage
            }else {
                Toast.makeText(this,"sign in failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUserWithEmailPassword(email: String,password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                val user = auth.currentUser
                //update UI
            }else {
                Toast.makeText(this,"Auth failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginWithGoogle(){
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener {
            try {
                println("open onetap UI")
                startIntentSenderForResult(it.pendingIntent.intentSender,REQ_ONE_TAP,null,0,0,0,null)

            }catch (e: IntentSender.SendIntentException){
                Log.d("EROR AUTH","Couldnt start onetap UI ${e.localizedMessage}")
            }
        }.addOnFailureListener {
            Log.d("EROR AUTH FAILURE","Couldnt start onetap UI ${it.localizedMessage}")
        }
    }



    override fun onStart() {
        super.onStart()
        var currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}