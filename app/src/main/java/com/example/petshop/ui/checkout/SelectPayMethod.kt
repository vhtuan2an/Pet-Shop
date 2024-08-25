package com.example.petshop.ui.checkout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petshop.R
import com.example.petshop.model.PaymentMethod
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun SelectPayMethod(
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel,
) {
    val order by orderViewModel.currentOrder.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PayMethod(
                imageId = R.drawable.cash,
                title = "Thanh toán khi nhận hàng",
                description = "Miễn phí thu hộ",
                isSelected = order.paymentMethod == PaymentMethod.CASH,
                onClick = {
                    orderViewModel.updateOrder(
                        order.copy(paymentMethod = PaymentMethod.CASH)
                    )
                })
            PayMethod(
                imageId = R.drawable.transfer,
                title = "Chuyển khoản ngân hàng",
                description = "(Tự động kiểm tra)",
                isSelected = order.paymentMethod == PaymentMethod.BANK,
                onClick = {
                    orderViewModel.updateOrder(
                        order.copy(paymentMethod = PaymentMethod.BANK)
                    )
                })
            Divider(color = Color(0xFFD9D9D9))
            AddPayMethod(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Đang phát triển",
                            actionLabel = "Đóng",
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            )
        }
    }

}

@Composable
fun PayMethod(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    @DrawableRes imageId: Int,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        RadioButton(
            selected = isSelected,
            onClick = onClick,
        )
    }
}

@Composable
fun AddPayMethod(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_pay_method),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(48.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Thẻ tín dụng",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Visa, Mastercard, AMEX, và JCB",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46AE7C))
        ) {
            Text(
                text = "Thêm",
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}


@Preview
@Composable
fun SelectPayMethodPreview() {
    PetShopTheme {
        SelectPayMethod(
            orderViewModel = OrderViewModel()
        )
    }
}