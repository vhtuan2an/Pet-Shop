package com.example.petshop.ui.shipment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petshop.ui.theme.PetShopTheme

@Composable
fun RatingStar2(maxStars: Int = 5, initialRating: Int = 0, onRatingChanged: (Int) -> Unit) {
    var rating by remember { mutableStateOf(initialRating) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxStars) {
            IconButton(
                onClick = { rating = i },
                modifier = Modifier
                    .size(36.dp) // Kích thước của mỗi sao
            ) {
                // Sử dụng điều kiện để chọn icon cho sao (filled hoặc unfilled)
                Icon(
                    imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.Star,
                    contentDescription = null,
                    tint = if (i <= rating) Color.Yellow else Color.Gray // Tô màu sao filled hoặc unfilled
                )
            }
        }
    }

    // Gọi hàm onRatingChanged khi trạng thái rating thay đổi
    onRatingChanged(rating)
}

@Preview(showBackground = true)
@Composable
fun RatingStarPreview() {
    PetShopTheme {
        RatingStar2(maxStars = 5, initialRating = 0) { rating ->
            // Xử lý sự kiện khi rating thay đổi
            // Ví dụ: lưu rating vào ViewModel hoặc gửi rating lên server
            // Ở đây chỉ hiển thị rating ra màn hình để minh họa
        }
    }
}