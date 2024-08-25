package com.example.petshop.model

import androidx.annotation.DrawableRes
import com.example.petshop.R

data class User(
    @DrawableRes var avatar: Int = R.drawable.avatar_not_login,
    var name: String = "Nguyễn Văn A",
    var user_id: String = "0",
    var role: String = "Thành viên",
    var sex: String = "Nam",
    var birthday: String = "01/01/2000",
    var address: String = "đâu đó",

    var phone: String = "0123456789",
    var email: String = "noemail@email.com",
    var password: String? = null, // Chưa đăng nhập thì password = null

    var favoriteProducts: List<String> = listOf(),
)