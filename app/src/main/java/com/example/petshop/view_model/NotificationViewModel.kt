package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import com.example.petshop.R
import com.example.petshop.model.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationViewModel : ViewModel() {

    private val _allNotifications = MutableStateFlow<List<Notification>>(
        listOf(
            Notification(
                title = "Chào mừng bạn đến với PetShop",
                description = "Hãy khám phá các sản phẩm mới nhất ở trang chủ nào!",
            ),
            Notification(
                title = "Ưu đãi dành riêng cho bạn",
                description = "Thêm voucher giảm giá không giới hạn lần sử dụng",
                image = R.drawable.voucher,
            ),
            Notification(
                title = "Đừng bỏ lỡ sản phẩm này",
                description = "Áo siêu nhân cho thú cưng làm cho boss trở nên ngầu hơn!",
            ),
            Notification(
                title = "Cập nhật sản phẩm mới",
                description = "Hãy ghé qua và xem thú cưng của bạn thích gì nhé!",
            ),
            Notification(
                title = "Chào mừng ngày 30/4 - 1/5",
                description = "Giảm giá 30% cho tất cả sản phẩm trong 2 ngày 30/4 và 1/5",
                image = R.drawable.voucher,
            ),
        )
    )
    val allNotifications: StateFlow<List<Notification>> = _allNotifications.asStateFlow()

    fun markAsSeen(notification: Notification) {
        val index = _allNotifications.value.indexOf(notification)
        val newNotification = notification.copy(isSeen = true)
        val newNotifications = _allNotifications.value.toMutableList()
        newNotifications[index] = newNotification
        _allNotifications.update { newNotifications }
    }
}