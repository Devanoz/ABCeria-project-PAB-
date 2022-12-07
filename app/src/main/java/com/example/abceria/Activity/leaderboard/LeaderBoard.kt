package com.example.abceria.Activity.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.adapter.LeaderboardAdapter
import com.example.abceria.db.DB
import com.example.abceria.model.leaderboard.Leaderboard
import com.example.abceria.model.user.User
import com.example.abceria.model.user.UserDetail
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentReference
import java.util.UUID

class LeaderBoard : AppCompatActivity() {
    lateinit var buttonMingguIni: Button
    lateinit var buttonBulanIni: Button
    lateinit var buttonSemuaWaktu: Button
    lateinit var rvLeaderboard : RecyclerView

    val database = DB.getRealtimeDatabaseInstance()
    val fsDatabase = DB.getFirestoreInstance()

    lateinit var  leaderboardAdapter : LeaderboardAdapter

    val userList = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        supportActionBar?.hide()

        initButtonComponents()

//        getValueFromDatabase()
//        addValueToDatabase()

//        addUserToDatabase()
        getUserFromDatabase()
    }

    private fun addUserToDatabase(){
        val userRef = fsDatabase.collection("user")
        userRef.add(
            User("1","devano","asdf")
        ).addOnSuccessListener {
            Toast.makeText(this,"sukses menambahkan data",Toast.LENGTH_SHORT)
        }.addOnFailureListener {
            Toast.makeText(this,"gagal menambahkan data",Toast.LENGTH_SHORT)
        }
    }
    private fun getUserFromDatabase(){
        fsDatabase.collection("user").get().addOnSuccessListener{
            val listDocument = it.documents
            listDocument.forEach { document ->
                val userMap = document.data
                var userDetail = userMap?.get("userDetail") as DocumentReference
                val user = User(UUID.randomUUID().toString(), userMap?.get("username").toString(),
                    userMap?.get("password").toString(),null
                )
               userDetail.get().addOnSuccessListener { it ->
                   val data = it.data as UserDetail
                   println(data.fullName)
               }.addOnFailureListener {
                   println("asuu")
               }
                userList.add(user)
            }

            initRecyclerView(userList)

        }.addOnFailureListener {
            Toast.makeText(this,"gagal mendapatkan data",Toast.LENGTH_SHORT)
        }
    }

//    private fun getValueFromDatabase(){
//        database.getReference("leaderboard").get().addOnSuccessListener {
//            val data = it.getValue<ArrayList<Leaderboard>>()
//            //remove data pertama karena data pertama selalu null, jadi kita hilangkan supaya tidak error
//            data?.removeAt(0)
//            //sort descending berdasarkan nilai score
//            data?.sortByDescending { it ->
//                it.score
//            }
////            val user = data?.get(1) as Leaderboard
////            println(user.user.username)
////            println(user.score)
//            if (data != null) {
//                initRecyclerView(data)
//            }
//        }
//    }

    private fun addValueToDatabase(){
        database.getReference("leaderboard").child("4").setValue(
            Leaderboard(
                User("4","Mayla Ayyuni Sonya","asdf",null),
                500
            )

        ).addOnSuccessListener {
            Toast.makeText(this,"berhasil menambahkan data",Toast.LENGTH_LONG)
        }.addOnFailureListener {
            Toast.makeText(this,"gagal menambahkan data",Toast.LENGTH_LONG)
        }
    }

    fun initRecyclerView(listOfLeaderboard: ArrayList<User>){
        rvLeaderboard = findViewById(R.id.leaderboard_rv_leaderboard)

        leaderboardAdapter = LeaderboardAdapter(listOfLeaderboard)

        rvLeaderboard.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvLeaderboard.adapter = leaderboardAdapter
    }

    private fun initButtonComponents(){
        buttonMingguIni = findViewById(R.id.leaderboard_btn_mingguIni)
        buttonBulanIni = findViewById(R.id.leaderboard_btn_bulanIni)
        buttonSemuaWaktu = findViewById(R.id.leaderboard_btn_semuaWaktu)

        var isButtonMingguIniClicked = false
        var isButtonBulanIniClicked = false
        var isButtonSemuaWaktuClicked = false

        buttonMingguIni.setOnClickListener{
            it.setBackgroundResource(R.drawable.leaderboard_button_wrapper_clicked)
            buttonBulanIni.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
            buttonSemuaWaktu.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
        }

        buttonBulanIni.setOnClickListener{
            it.setBackgroundResource(R.drawable.leaderboard_button_wrapper_clicked)
            buttonMingguIni.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
            buttonSemuaWaktu.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
        }

        buttonSemuaWaktu.setOnClickListener{
            it.setBackgroundResource(R.drawable.leaderboard_button_wrapper_clicked)
            buttonMingguIni.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
            buttonBulanIni.setBackgroundResource(R.drawable.leaderboard_button_wrapper)
        }
    }
}