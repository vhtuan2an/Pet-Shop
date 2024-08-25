package com.example.petshop.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Product
import com.example.petshop.model.Screen
import com.example.petshop.model.ToyProduct
import com.example.petshop.ui.NewCheckoutEndBar
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.OrderViewModel
import com.example.petshop.view_model.UserViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    userViewModel: UserViewModel,
) {
    val order by orderViewModel.currentOrder.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NewCheckoutEndBar(
                total = order.total,
                onCheckoutClick = {
                    // Thêm sản phẩm vào order mới
                    orderViewModel.addOrder(order)
                    orderViewModel.autoUpdateOrderStatus(order.id)
                    // Xóa sản phẩm đã chọn khỏi giỏ hàng
                    for (product in order.products) {
                        cartViewModel.removeProductFromCart(product)
                    }
                    navController?.navigate(Screen.LoadingCheckout.route)
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(order.products) { product ->
                CheckoutItem(product = product)
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(color = Color(0xFFEFEBE9))
                )
                Column {
                    Information(
                        modifier.padding(10.dp),
                        userViewModel = userViewModel,
                        onEditAddressClick = {
                            navController?.navigate(Screen.EditProfileScreen.route)
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(color = Color(0xFFEFEBE9))
                    )
                    Delivery(
                        modifier = Modifier.padding(10.dp),
                        orderViewModel = orderViewModel,
                        onPaymentClick = {
                            navController?.navigate(Screen.SelectPayMethod.route)
                        },
                        onVoucherClick = {
                            navController?.navigate(Screen.SelectVoucher.route)
                        }

                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(color = Color(0xFFEFEBE9))
                    )
                    PaymentDetail(
                        modifier = Modifier.padding(10.dp),
                        orderViewModel = orderViewModel,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutItem(
    modifier: Modifier = Modifier,
    product: Product = FoodProduct(
        id = "1",
        name = "Đồ ăn cho chó",
        description = "Chó rất thích ăn nó",
        price = 10000.0,
        oldPrice = 15000.0,
        star = 4.5,
        quantity = 1,
        image = R.drawable.avt,
        detailDescription = "Chi tiết sản phẩm"
    ),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 13.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = null,
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
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = product.price.toString().replace(".0", "") + "đ",
                    style = MaterialTheme.typography.titleMedium,

                    )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "x${product.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            /*Text(
                text = "Phân loại:",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(12.dp))*/
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
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
                                        text = product.selectedWeight.value,
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
}

@Preview
@Composable
fun CheckoutItemPreview() {
    PetShopTheme {
        CheckoutItem()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckoutScreenPreview() {
    PetShopTheme {
        CheckoutScreen(
            cartViewModel = viewModel(),
            orderViewModel = viewModel(),
            userViewModel = viewModel(),
        )
    }
}