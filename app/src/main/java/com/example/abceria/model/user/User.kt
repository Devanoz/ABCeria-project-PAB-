package com.example.abceria.model.user

data class User(
    private val id: String,
    private val username: String,
    private val password: String,
    private val user_detail: UserDetail
)
