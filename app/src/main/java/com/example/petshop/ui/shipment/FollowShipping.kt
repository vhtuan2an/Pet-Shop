package com.example.petshop.ui.shipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.OrderStatus
import com.example.petshop.model.Product
import com.example.petshop.model.ToyProduct
import com.example.petshop.view_model.OrderViewModel

@Composable
fun FollowShippingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    orderViewModel: OrderViewModel,
    orderId: String = ""
) {
    val allOrders by orderViewModel.allOrders.collectAsState()
    val order = allOrders.find { it.id == orderId }

    LazyColumn(
        modifier = modifier
    ) {
        items(order?.products ?: listOf()) { product ->
            ShippingProducts(
                product = product,
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Divider()
                ShippingStatus(
                    orderStatus = order?.status ?: OrderStatus.PREPARE,
                )
                ConfirmReceived(
                    enabled = order?.status == OrderStatus.DELIVERED,
                    modifier = Modifier.padding(top = 30.dp),
                    onClick = {
                        navController?.popBackStack()
                    }
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingProducts(
    modifier: Modifier = Modifier,
    product: Product,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = "image description",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .shadow(
                        elevation = 2.2138051986694336.dp,
                        spotColor = Color(0x05000000),
                        ambientColor = Color(0x05000000)
                    )
                    .shadow(
                        elevation = 5.32008171081543.dp,
                        spotColor = Color(0x07000000),
                        ambientColor = Color(0x07000000)
                    )
                    .shadow(
                        elevation = 10.017241477966309.dp,
                        spotColor = Color(0x09000000),
                        ambientColor = Color(0x09000000)
                    )
                    .shadow(
                        elevation = 17.869047164916992.dp,
                        spotColor = Color(0x0B000000),
                        ambientColor = Color(0x0B000000)
                    )
                    .shadow(
                        elevation = 33.422088623046875.dp,
                        spotColor = Color(0x0D000000),
                        ambientColor = Color(0x0D000000)
                    )
                    .width(69.dp)
                    .height(69.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Column(
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    // định dạng không có dấu chấm
                    text = product.price.toString().replace(".0", "") + " đ",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                )
                Text(
                    text = "x${product.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            when (product) {
                is FoodProduct -> {
                    item {
                        FilterChip(
                            label = {
                                Text(
                                    text = product.selectedFlavor.value,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = { /*TODO*/ },
                            selected = true,
                            colors = FilterChipDefaults.filterChipColors(
                                labelColor = Color(0xFF000000),
                                selectedLabelColor = Color.White,
                                selectedContainerColor = Color(0xFF5D4037),
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                    item {
                        FilterChip(
                            label = {
                                Text(
                                    text = "${product.selectedWeight.value}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = { /*TODO*/ },
                            selected = true,
                            colors = FilterChipDefaults.filterChipColors(
                                labelColor = Color(0xFF000000),
                                selectedLabelColor = Color.White,
                                selectedContainerColor = Color(0xFF5D4037),
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }

                is ToyProduct -> {
                    item {
                        FilterChip(
                            label = {
                                Text(
                                    text = product.selectedSize.value,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = { /*TODO*/ },
                            selected = true,
                            colors = FilterChipDefaults.filterChipColors(
                                labelColor = Color(0xFF000000),
                                selectedLabelColor = Color.White,
                                selectedContainerColor = Color(0xFF5D4037),
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }

                is ClothesProduct -> {
                    item {
                        FilterChip(
                            label = {
                                Text(
                                    text = product.selectedSize.value,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = { /*TODO*/ },
                            selected = true,
                            colors = FilterChipDefaults.filterChipColors(
                                labelColor = Color(0xFF000000),
                                selectedLabelColor = Color.White,
                                selectedContainerColor = Color(0xFF5D4037),
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShippingStatus(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus = OrderStatus.PREPARE
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        StatusItem(
            text = "Shop đang chuẩn bị hàng",
            isCompleted = orderStatus == OrderStatus.PREPARE || orderStatus == OrderStatus.SHIPPING || orderStatus == OrderStatus.DELIVERED,
        )
        StatusItem(
            text = "Đơn vị vận chuyển đang giao hàng",
            isCompleted = orderStatus == OrderStatus.SHIPPING || orderStatus == OrderStatus.DELIVERED,
        )
        StatusItem(
            text = "Shipper đang trên đường đến",
            isCompleted = orderStatus == OrderStatus.DELIVERED,
            isLast = true,
            additionalText = "Hãy chú ý điện thoại, shipper sẽ gọi cho bạn"
        )
    }
}

@Composable
fun StatusItem(
    text: String,
    isCompleted: Boolean,
    isLast: Boolean = false,
    additionalText: String? = null
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(30.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(if (isLast) 30.dp else 18.dp)
                    .height(if (isLast) 30.dp else 18.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isCompleted) Color(0xFF43936C) else Color(0xFFCACACA)
                    )
            ) {
                if (isLast) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            // nếu không phải item cuối cùng thì vẽ đường thẳng
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(if (isCompleted) Color(0xFF43936C) else Color(0xFFCACACA))
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = text, style = MaterialTheme.typography.titleSmall)
            additionalText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ConfirmReceived(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5D4037) // Màu nâu cho button
            ),
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Đã nhận hàng",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Chọn \"Đã nhận hàng\" khi bạn đã nhận được hàng",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun ShippingProductsPreview() {
    ShippingProducts(
        product = FoodProduct(
            id = "1",
            name = "Đồ ăn cho chó",
            description = "Thơm ngon, bổ dưỡng",
            price = 15000.0,
            oldPrice = 20000.0,
            star = 4.8,
            quantity = 1,
            image = R.drawable.avt,
            detailDescription = "Chi tiết sản phẩm"
        )
    )
}

@Preview
@Composable
fun FollowShippingScreenPreview() {
    FollowShippingScreen(
        orderViewModel = OrderViewModel(),
        navController = rememberNavController(),
    )
}
