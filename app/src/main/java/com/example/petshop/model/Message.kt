package com.example.petshop.model

data class Message(
    var userPhone: String = "",
    var message: String = "",
    var time: String = "",
    var isMe: Boolean = true
)