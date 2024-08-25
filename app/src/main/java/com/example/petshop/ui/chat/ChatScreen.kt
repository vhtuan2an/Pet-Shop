package com.example.petshop.ui.chat


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petshop.R
import com.example.petshop.model.Message
import com.example.petshop.model.Screen
import com.example.petshop.ui.TopAppBarNoSearch
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.ChatViewModel
import com.example.petshop.view_model.ProductViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    productId: String = "",
    navController: NavController,
    cartViewModel: CartViewModel,
    chatViewModel: ChatViewModel,
) {
    val messages by chatViewModel.messages.collectAsState()
    val cartNumber by cartViewModel.productsInCart.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarNoSearch(
                title = "Chat",
                onBackClick = { navController.popBackStack() },
                isCartEnable = true,
                onCartClick = { navController.navigate(Screen.ShoppingCartScreen.route) },
                cartNumber = cartNumber.size
            )
        }
    ) { paddingValues ->
        if (messages.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(500.dp))
                Text(
                    text = "Hãy bắt đầu cuộc trò chuyện!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Thêm khoảng trống để đẩy các tin nhắn xuống dưới

            MessageList(messages = messages)
            MessageInput(onMessageSent = { text ->
                chatViewModel.sendMessage(text)
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MessageList(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            Alignment.Bottom
        ) // Căn tin nhắn xuống dưới cùng
    ) {
        items(messages) { message ->
            MessageItem(message = message)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .sizeIn(maxWidth = 240.dp)
                .background(
                    color = if (message.isMe) Color(0xFFD3B8AE) else Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = message.time,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(if (message.isMe) Alignment.End else Alignment.Start)
                )
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSent: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Column {
                Spacer(modifier = Modifier.height(9.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(1.dp, Color(0xFFD3B8AE), RoundedCornerShape(20.dp))
                        .background(Color.Transparent)
                )
            }
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        "Nhập tin nhắn...",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(color = Color.Black.copy(alpha = 0.5f))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onMessageSent(message)
                        message = ""
                    }
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = {
            onMessageSent(message)
            message = ""
        }) {
            Icon(
                painter = painterResource(id = R.drawable.send),
                contentDescription = "Send",
                tint = Color(0xFFA05F47)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MessageInputPreview() {
    MessageInput(onMessageSent = {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        chatViewModel = ChatViewModel(),
        navController = rememberNavController(),
        cartViewModel = CartViewModel(productViewModel = ProductViewModel())
    )
}
