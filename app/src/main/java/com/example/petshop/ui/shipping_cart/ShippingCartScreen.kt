package com.example.petshop.ui.shipping_cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Order
import com.example.petshop.model.Product
import com.example.petshop.model.Screen
import com.example.petshop.model.ToyProduct
import com.example.petshop.ui.CheckoutBottomBar
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.OrderViewModel
import com.example.petshop.view_model.ProductViewModel
import com.example.petshop.view_model.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    userViewModel: UserViewModel
) {
    val selectedProducts by cartViewModel.selectedProducts.collectAsState()
    val totalAmount by cartViewModel.totalAmount.collectAsState()
    val user by userViewModel.currentUser.collectAsState()
    val productsInCart by cartViewModel.productsInCart.collectAsState()

    cartViewModel.up_set_UserId(user.user_id)
    cartViewModel.getProductsByUserId()


    // Thêm biến trạng thái để kiểm tra xem reset đã được thực hiện chưa
    var isInitialized by remember { mutableStateOf(false) }

    // Reset các sản phẩm đã chọn nếu chưa được thực hiện
//    if (!isInitialized) {
//        cartViewModel.resetSelectedProducts()
//        isInitialized = true
//    }
    if (productsInCart.isEmpty()) {
        return Column(
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Giỏ hàng trống!!\nHãy thêm sản phẩm vào giỏ hàng nào!",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (selectedProducts.isNotEmpty())
                CheckoutBottomBar(
                    total = totalAmount,
                    onBuyClicked = {
                        orderViewModel.updateOrder(
                            Order(
                                products = selectedProducts,
                                user = user,
                                discount = 0.0,
                                voucher = null,
                            )
                        )
                        println(orderViewModel.currentOrder.value.products)
                        navController?.navigate(Screen.CheckoutScreen.route)
                    }
                )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it),
        ) {
            items(productsInCart) { product ->
                BoughtItemCart(
                    product = product,
                    onQuantityChange = { isChecked, newQuantity ->
                        if (isChecked) {
                            if (!selectedProducts.contains(product)) {
                                cartViewModel.addProductToSelected(product)
                            }
                            product.quantity = newQuantity
                        } else {
                            cartViewModel.removeProductFromSelected(product)
                        }
                        cartViewModel.updateTotalAmount()
                    },
                    onDeleteClick = {
                        cartViewModel.removeProductFromCart(product)
                        cartViewModel.updateTotalAmount()
                    },
                )
                if (productsInCart.indexOf(product) != productsInCart.size - 1) {
                    Divider(color = Color(0xFFD9D9D9))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoughtItemCart(
    modifier: Modifier = Modifier,
    product: Product,
    onQuantityChange: (Boolean, Int) -> Unit,
    onDeleteClick: () -> Unit = {},
    initCheck: Boolean = false,
) {
    var quantity by remember { mutableStateOf(product.quantity) }
    var checkedState by remember { mutableStateOf(initCheck) }
    var showDialog by remember { mutableStateOf(false) }

    // Hiện dialog xác nhận xóa sản phẩm khỏi giỏ hàng
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Xác nhận xóa",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                checkedState = !checkedState
                onQuantityChange(checkedState, quantity)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp)
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    onQuantityChange(checkedState, quantity)
                },
                modifier = Modifier
                    .padding(end = 14.dp)
                    .width(14.dp)
                    .height(14.dp)
            )
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
                modifier = Modifier.fillMaxWidth()
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
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    // Hiển thị các thông số của sản phẩm theo loại sản phẩm
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.trash),
                            contentDescription = null,
                            modifier = Modifier.width(28.dp)
                        )
                    }

                    // Nút giảm tăng số lượng sản phẩm
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(8.dp),
                                width = 1.dp,
                                color = Color(0xFFCACACA)
                            )
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity > 1) quantity--
                                product.quantity = quantity
                                onQuantityChange(checkedState, quantity)
                            },
                            modifier = Modifier
                                .padding(1.dp)
                                .width(30.dp)
                                .height(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.width(36.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = {
                                quantity++
                                product.quantity = quantity
                                onQuantityChange(checkedState, quantity)
                            },
                            modifier = Modifier
                                .padding(1.dp)
                                .width(30.dp)
                                .height(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Text(
                text = "${(product.price * quantity).toString().replace(".0", "")}đ",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BoughtItemPreview() {
    PetShopTheme {
        ShoppingCartScreen(
            cartViewModel = CartViewModel(productViewModel = ProductViewModel()),
            userViewModel = UserViewModel(),
            orderViewModel = OrderViewModel(),
        )
    }
}
