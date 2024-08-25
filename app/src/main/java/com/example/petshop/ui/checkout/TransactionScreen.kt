package com.example.petshop.ui.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.DeliveryMethod
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.PaymentMethod
import com.example.petshop.model.Product
import com.example.petshop.model.Screen
import com.example.petshop.model.ToyProduct
import com.example.petshop.ui.login_register.Button
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.OrderViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    orderViewModel: OrderViewModel,
) {
    val allOrders by orderViewModel.allOrders.collectAsState()
    val order = allOrders.last()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            ActionBottomBar(
                modifier = Modifier.padding(16.dp),
                onGoHome = {
                    navController?.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                },
                onTrackOrder = {
                    navController?.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }

                    navController?.navigate(Screen.ProfileScreen.route)
                    navController?.navigate(Screen.ShipmentStateScreen1.route)
                    navController?.navigate(Screen.FollowShipping.createRoute(order.id))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.padding(top = 45.dp)
                    ) {
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFEFEBE9),
                                        shape = RoundedCornerShape(size = 16.dp)
                                    )
                                    .padding(24.dp)
                                    .weight(1f)
                            ) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = "Cảm ơn bạn!",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "Thanh toán thành công!",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(
                                            start = 8.dp,
                                            top = 5.dp,
                                            end = 8.dp,
                                            bottom = 5.dp
                                        )
                                ) {
                                    val currentDateTime = LocalDateTime.now()

                                    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                                    val dateText = currentDateTime.format(dateFormatter)

                                    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                                    val timeText = currentDateTime.format(timeFormatter)
                                    ItemBold(title = "Mã đơn hàng:", detail = order.id)
                                    ItemBold(title = "Ngày:", detail = dateText)
                                    ItemBold(title = "Thời gian:", detail = timeText)
                                }
                                Divider()
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                ) {
                                    Text(
                                        text = "Vật phẩm:",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    order.products.forEach { product ->
                                        ProductItem(product = product)
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                ) {
                                    Text(
                                        text = "Chi tiết thanh toán:",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ItemMedium(
                                        title = "Tổng tiền hàng:",
                                        detail = order.productTotal.toInt().toString() + " đ"
                                    )
                                    ItemMedium(
                                        title = "Phí vận chuyển:",
                                        detail = order.shippingFee.toInt().toString() + " đ"
                                    )
                                    ItemMedium(
                                        title = "Voucher:",
                                        detail = "- ${order.discount.toInt()} đ"
                                    )
                                    ItemBold(
                                        title = "Tổng cộng:",
                                        detail = order.total.toInt().toString() + " đ"
                                    )
                                }
                                Divider()
                                ItemMedium(
                                    title = "Phương thức thanh toán:",
                                    detail = when (order.paymentMethod) {
                                        PaymentMethod.CASH -> "Tiền mặt"
                                        PaymentMethod.BANK -> "Chuyển khoản"
                                    },
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                )
                                ItemMedium(
                                    title = "Thời gian dự kiến:",
                                    detail = when (order.deliveryMethod) {
                                        DeliveryMethod.NORMAL -> "1-2 ngày"
                                        DeliveryMethod.FAST -> "khoảng 30 phút"
                                    },
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.pay_done),
                        contentDescription = null,
                        modifier = Modifier
                            .height(75.dp)
                            .width(75.dp) // Đặt kích thước hình ảnh
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ItemBold(
    modifier: Modifier = Modifier,
    title: String,
    detail: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        if (detail != null)
            Text(
                text = detail,
                style = MaterialTheme.typography.bodyMedium
            )
    }
}

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "x${product.quantity}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.width(20.dp)
            )
        }
        Text(
            text = when (product) {
                is FoodProduct -> "Phân loại: ${product.selectedFlavor.value} - ${product.selectedWeight.value}"
                is ToyProduct -> "Phân loại: ${product.selectedSize.value}"
                is ClothesProduct -> "Phân loại: ${product.selectedSize.value}"
            },
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
        )

    }
}


@Composable
fun ItemMedium(
    modifier: Modifier = Modifier,
    title: String,
    detail: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (detail != null)
            Text(
                text = detail,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Right
            )
    }
}

@Composable
fun ActionBottomBar(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit = {},
    onTrackOrder: () -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        Button(
            onClick = onGoHome,
            title = "Trở về trang chủ",
            isDisable = false,
            color = Color(0xFFA1887F),
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = onTrackOrder,
            title = "Theo dõi đơn hàng",
            isDisable = false,
            color = Color(0xFF46AE7C),
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    PetShopTheme {
        ProductItem(
            product = FoodProduct(
                id = "1",
                oldPrice = 20000.0,
                star = 4.8,
                name = "Thức ăn hạt mềm Zenith",
                price = 45000.0,
                description = "Thức ăn hạt mềm Zenith dành cho chó",
                quantity = 1,
                image = R.drawable.avt,
                detailDescription = "Thức ăn hạt mềm Zenith dành cho chó"
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TransactionPreview() {
    PetShopTheme {
        TransactionScreen(
            orderViewModel = OrderViewModel()
        )
    }
}