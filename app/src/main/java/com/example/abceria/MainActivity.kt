package com.example.abceria

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toFile
import com.example.abceria.Activity.auth.Auth
import com.example.abceria.db.DB
import com.example.abceria.model.user.UserDetail
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    val fireStore = DB.getFirestoreInstance()

    val auth = Auth.getAuthInstance()

    val storage = DB.getStorageInstance()

    lateinit var btnUpload : Button
    lateinit var btnChooseFile : Button
    lateinit var tvUpload : TextView
    lateinit var imvPreview : ImageView

    var imageURI: Uri? = null

    companion object {
        const val PICK_FILE_REQUEST = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUpload = findViewById(R.id.main_btn_upload)
        btnChooseFile = findViewById(R.id.main_btn_choose_file)
        tvUpload = findViewById(R.id.main_tv_upload)
        imvPreview = findViewById(R.id.main_imv_show_preview)


        btnChooseFile.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_FILE_REQUEST)
        }

        btnUpload.setOnClickListener{
            if(imageURI!=null){
                addImage(imageURI!!)
            }else {
                auth.signOut()
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_FILE_REQUEST && resultCode==Activity.RESULT_OK){
            if (data != null) {
                if(data.data!=null){
                    val imagePickedUri = data.data
                    imvPreview.setImageURI(data.data)
                    if (imagePickedUri != null) {
                        imageURI = imagePickedUri
                    }
                }
            }
        }

    }



    fun addUserDetail(userDetail: UserDetail){
        fireStore.collection("user-detail").add(userDetail).addOnSuccessListener {
            Toast.makeText(this,"berhasil menambahkan data",Toast.LENGTH_LONG)
        }.addOnFailureListener {
            Toast.makeText(this,"gagal menambahkan data",Toast.LENGTH_LONG)
        }
    }

    fun addImage(uri: Uri){

        val storageRef = storage.reference
//        val profileRef = storageRef.child("profile.jpg")
//        val profileStorageRef = storageRef.child("/images/profile.jpg")

        var file:Uri = uri

        val profileRef = storageRef.child("/candi/${file.lastPathSegment}")

        profileRef.putFile(file)

    }



}