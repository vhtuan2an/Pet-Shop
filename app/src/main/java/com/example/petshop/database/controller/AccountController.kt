package com.example.petshop.database.controller

import com.example.petshop.database.model.Account
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccountController {
    companion object {

        private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        fun isPhoneNumberExists(numberphone: String, callback: (Boolean) -> Unit) {
            val query = database.child("Accounts").orderByChild("numberphone").equalTo(numberphone)
            query.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        callback(dataSnapshot.exists())
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Xử lý lỗi nếu cần
                    }
                }
            )
        }

        fun registerAccount(numberphone: String, password: String, callback: (Boolean) -> Unit) {
            isPhoneNumberExists(numberphone) { isExists ->
                if (isExists) {
                    callback(false)
                } else {
                    // Tiếp tục đăng ký nếu số điện thoại chưa tồn tại
                    val userId = database.push().key ?: return@isPhoneNumberExists
                    val account =
                        Account(
                            user_id = userId,
                            numberphone = numberphone,
                            password = password,
                            name = "Chưa đặt",
                            email = "Thêm email",
                            sex = "Chỉnh sửa",
                            address = "Thêm địa chỉ",
                            birthDay = "Chưa đặt"
                        )
                    database.child("Accounts").child(userId).setValue(account)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                callback(true)
                            } else {
                                callback(false)
                            }
                        }
                }
            }
        }

        fun Login(numberphone: String, password: String, callback: (Boolean) -> Unit) {
            val query = database.child("Accounts").orderByChild("numberphone").equalTo(numberphone)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var accountExists = false
                    for (accountSnapshot in dataSnapshot.children) {
                        val account = accountSnapshot.getValue(Account::class.java)
                        if (account != null && account.password == password) {
                            accountExists = true
                            break
                        }
                    }
                    callback(accountExists)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(false)
                }
            })
        }


        // Hàm cập nhật thông tin tài khoản
        fun editProfile(account: Account, callback: (Boolean) -> Unit) {
            val userId = account.user_id
            if (userId != null) {
                database.child("Accounts").child(userId).setValue(account)
                    .addOnCompleteListener { task ->
                        callback(task.isSuccessful)
                    }
                    .addOnFailureListener {
                        callback(false)
                    }
            } else {
                callback(false)
            }
        }

        // lấy thông tin người dùng bằng sđt
        fun getAccountByNumberphone(numberphone: String, callback: (Account?) -> Unit) {
            val query = database.child("Accounts").orderByChild("numberphone").equalTo(numberphone)
                .limitToFirst(1)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var account: Account? = null
                    for (accountSnapshot in dataSnapshot.children) {
                        account = accountSnapshot.getValue(Account::class.java)
                        break
                    }
                    callback(account)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Xử lý lỗi nếu cần
                    callback(null)
                }
            })
        }

    }
}
