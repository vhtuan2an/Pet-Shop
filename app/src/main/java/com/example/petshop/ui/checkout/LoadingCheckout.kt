package com.example.petshop.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.model.Screen
import kotlinx.coroutines.delay

@Preview
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    var isPayDone by remember { mutableStateOf(false) }
    var isNextScreen by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // Đợi vài giây
            isPayDone = true
            delay(2000)
            navController?.navigate(Screen.TransactionScreen.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!isPayDone)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Đang thanh toán...",
                    // Title/16/Medium
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        else
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_tranfer),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                //Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Yeayy!!\nThanh toán thành công ",
                    // Heading/20/Bold
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cửa hàng đã nhận đơn đặt hàng của bạn",
                    // Title/16/Medium
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
    }
}

