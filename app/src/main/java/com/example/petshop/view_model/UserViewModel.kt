package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import com.example.petshop.R
import com.example.petshop.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User>(
        User(
            name = "Nguyễn Công Tú",
            role = "Khách hàng thân thiết",
            favoriteProducts = listOf(),
            sex = "Nam",
            email = "asdfsaf@asadfs.fff",
            phone = "0123456789",
            avatar = R.drawable.avatar,
            birthday = "01/01/2000",
            password = "null",
            address = "đâu đó",
        )
    )
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    fun updateUserAddress(user: User) {
        _currentUser.update {
            it.copy(
                address = it.address
            )
        }
    }

    fun updateUser(user: User) {
        _currentUser.value = user
    }

    fun addFavoriteProduct(productId: String) {
        _currentUser.update {
            it.copy(
                favoriteProducts = it.favoriteProducts + productId
            )
        }
    }

    fun removeFavoriteProduct(productId: String) {
        _currentUser.update {
            it.copy(
                favoriteProducts = it.favoriteProducts - productId
            )
        }
    }
}