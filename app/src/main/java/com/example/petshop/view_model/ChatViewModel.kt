package com.example.petshop.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.petshop.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val database = FirebaseDatabase.getInstance().reference.child("ChatMessages")

    var user_id: String? = null

    fun up_set_UserId(id: String) {
        this.user_id = id
    }

    init {
        // Lắng nghe sự thay đổi trên Firebase khi ứng dụng khởi động
        fetchMessagesFromFirebase()
    }

    fun addMessage(message: Message) {
        // Xác định user ID của người gửi tin nhắn
        val userId = user_id ?: "unknown"

        // Tạo một key mới cho tin nhắn trên Firebase
        val messageKey = database.child(userId).push().key ?: return
        // Đặt giá trị cho key với tin nhắn mới trên Firebase
        database.child(userId).child(messageKey).setValue(message)
            .addOnSuccessListener {
                // Xử lý thành công nếu cần
                // Sau khi tin nhắn được ghi vào Firebase thành công, cập nhật _messages
                val updatedMessages = _messages.value.toMutableList()
                updatedMessages.add(message)
                _messages.value = updatedMessages
            }
            .addOnFailureListener {
                // Xử lý lỗi nếu cần
            }
    }


    fun fetchMessagesFromFirebase() {
        // Lấy user ID của người dùng hiện tại
        val userId = user_id ?: "unknown"

        // Sử dụng addValueEventListener để lắng nghe sự thay đổi trên Firebase cho user ID hiện tại
        database.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messageList = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let {
                        messageList.add(it)
                    }
                }
                // Cập nhật _messages với dữ liệu từ Firebase
                _messages.update { messageList }
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi nếu cần
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(content: String) {
        if (content.isEmpty()) return
        val newMessage = Message(
            message = content,
            time = getCurrentTimeFormatted(),
            isMe = true,
            userPhone = UserViewModel().currentUser.value.phone
        )
        addMessage(newMessage)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeFormatted(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        return current.format(formatter)
    }

    fun clearMessages() {
        _messages.update { emptyList() }
    }
}

