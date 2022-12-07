package com.example.abceria.model.user

import android.net.Uri

data class User(
    val id: String,
    var fullName: String,
    var username: String,
    var profilePicture: Uri = Uri.EMPTY,
    var score: Int = 0,
)
