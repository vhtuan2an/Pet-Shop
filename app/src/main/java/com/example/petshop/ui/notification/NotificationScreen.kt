package com.example.petshop.ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petshop.R
import com.example.petshop.model.Notification
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.NotificationViewModel


@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationViewModel: NotificationViewModel,
) {
    val notifications by notificationViewModel.allNotifications.collectAsState()

    if (notifications.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Không có thông báo nào",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else
        LazyColumn(
            modifier = modifier.fillMaxWidth()
        ) {
            items(notifications) { notification ->
                Notification(
                    notification = notification,
                    onClick = {
                        notificationViewModel.markAsSeen(notification)
                    }
                )
            }
        }
}

@Composable
fun Notification(
    notification: Notification,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = if (notification.isSeen)
                    MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.surfaceVariant,
            )
            .clickable(enabled = !notification.isSeen, onClick = onClick)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .fillMaxWidth()

        ) {
            Column {
                Image(
                    painter = painterResource(id = notification.image),
                    contentDescription = notification.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(shape = CircleShape)
                )
            }
            Column {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = notification.description,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationDemo() {
    PetShopTheme {
        Notification(
            notification = Notification(
                title = "Thông báo 1",
                description = "Nội dung",
                image = R.drawable.noti_icon,
                isSeen = false
            ),
            onClick = {}
        )
    }
}