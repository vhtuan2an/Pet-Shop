package com.example.petshop.ui.product_infor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.database.controller.AccountController
import com.example.petshop.database.controller.CartController
import com.example.petshop.database.model.CartItem
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.Flavor
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Order
import com.example.petshop.model.Product
import com.example.petshop.model.Screen
import com.example.petshop.model.Size
import com.example.petshop.model.ToyProduct
import com.example.petshop.model.User
import com.example.petshop.model.Weight
import com.example.petshop.ui.DetailProductBottomBar
import com.example.petshop.ui.TopAppBarNoSearch
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.OrderViewModel
import com.example.petshop.view_model.ProductViewModel
import com.example.petshop.view_model.UserViewModel

@Composable
fun getScreenWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@Composable
fun ProductDetail(
    productId: String,
    navController: NavController? = null,
    productViewModel: ProductViewModel,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    onBackClick: () -> Unit = {}
) {
    val allProducts by productViewModel.allProducts.collectAsState()
    val product by productViewModel.selectedProduct.collectAsState()
    val user by userViewModel.currentUser.collectAsState()
    val selectedProducts by cartViewModel.selectedProducts.collectAsState()
    val cartNumber by cartViewModel.cartNumber.collectAsState()

    val context = LocalContext.current

    productViewModel.setSelectedProduct(allProducts.find { it.id == productId }!!)

    Scaffold(
        topBar = {
            product?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface)
                )
                {

                    TopAppBarNoSearch(
                        title = it.name,
                        isCartEnable = true,
                        onCartClick = {
                            navController?.navigate(Screen.ShoppingCartScreen.route)
                        },
                        onBackClick = {
                            navController?.popBackStack()
                        },
                        cartNumber = cartNumber
                    )
                }
            }
        },
        bottomBar = {
            DetailProductBottomBar(
                onChatClicked = {
                    navController?.navigate(Screen.ChatScreen.route)
                },
                onAddToCartClicked = {
                    var user_id = user.user_id.toString()
                    cartViewModel.up_set_UserId(user_id)
                    cartViewModel.addProductToCart(product!!)
                    cartViewModel.updateSelectedProduct(listOf(product!!))
                    cartViewModel.updateCartNumber()

                    //==================================
//                    val newCartItem = CartItem(id_product = product!!.id, quantity = product!!.quantity) // Sử dụng id_sp thay vì id_product
//                    CartController.addCartItem(user.user_id, newCartItem) { success, message ->
//                        if (success) {
                            Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                        }
//                    }

                    //===============================
                    // Hiện toast thông báo đã thêm vào giỏ hàng

                },
                onBuyClicked = {
                    orderViewModel.updateOrder(
                        Order(
                            products = listOf(allProducts.find { it.id == productId }!!),
                            user = user,
                            discount = 0.0,
                            voucher = null,
                        )
                    )
                    cartViewModel.updateSelectedProduct(listOf(product!!))
                    navController?.navigate(Screen.CheckoutScreen.route)
                },
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()
        product?.let { product ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(padding)
            ) {
                ProductImageSection(product.image, scrollState)
                ProductInfoSection(
                    product = product,
                    onRateClick = {
                        Toast.makeText(context, "Chức năng đang phát triển", Toast.LENGTH_SHORT)
                            .show()
                    },
                    onFavoriteClick = {
                        productViewModel.toggleFavorite()
                        if (product.isFavorite)
                            userViewModel.removeFavoriteProduct(product.id)
                        else
                            userViewModel.addFavoriteProduct(product.id)
                    },
                    isFavorite = user.favoriteProducts.contains(product.id),
                    onMinusClick = { productViewModel.decreaseQuantity() },
                    onPlusClick = { productViewModel.increaseQuantity() }
                )
                ProductCustomizationSection(productViewModel = productViewModel)
                ProductDescriptionSection(product.detailDescription)

                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}


@Composable
private fun ProductImageSection(
    imageResource: Int,
    scrollState: ScrollState,
) {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
            .graphicsLayer {
                alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                translationY = 0.5f * scrollState.value
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ProductInfoSection(
    product: Product,
    onRateClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
        //.align(Alignment.CenterHorizontally)
    ) {
        ProductInfoCard(
            product = product,
            onRateClick = onRateClick,
            onFavoriteClick = onFavoriteClick,
            isFavorite = isFavorite,
            onMinusClick = onMinusClick,
            onPlusClick = onPlusClick
        )
    }
}

@Composable
private fun ProductInfoCard(
    modifier: Modifier = Modifier,
    product: Product,
    onRateClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    val roundedSize = 10.dp
    Box(
        modifier = modifier
            .width((getScreenWidth() - 30).dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(size = roundedSize))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            ProductTags(tags = product.tags)
            ProductTitleAndPrice(
                title = product.name,
                price = product.price,
                oldPrice = product.oldPrice
            )
            ProductDescription(
                description = product.description,
                quantity = product.quantity,
                onMinusClick = onMinusClick,
                onPlusClick = onPlusClick
            )
            ProductRatingAndFavorite(
                rating = product.star,
                onRateClick = onRateClick,
                onFavoriteClick = onFavoriteClick,
                isFavorite = isFavorite
            )
        }
    }
}

@Composable
private fun ProductTags(
    modifier: Modifier = Modifier,
    tags: List<String>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(350.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
            .padding(10.dp)

    ) {
        items(tags) { tag ->
            ProductTag(
                text = tag, color = when (tag) {
                    "Thức ăn" -> Color(0xFF008000)
                    "Đồ chơi" -> Color(0xFF0000FF)
                    "Quần áo" -> Color(0xFFFFA500)
                    "Được yêu thích" -> Color(0xFFFF0000)
                    else -> Color(0xFF3C3C3C)
                }
            )
        }
    }
}

@Composable
private fun ProductTag(text: String, color: Color) {
    AssistChip(
        //enabled = false,
        onClick = { },
        label = {
            Text(
                text,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            )
        },
    )
}

@Composable
private fun ProductTitleAndPrice(
    modifier: Modifier = Modifier,
    title: String,
    price: Double,
    oldPrice: Double
) {
    Row(
        //horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            //.fillMaxWidth()
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF5D4037)
            ),
            modifier = Modifier.width(240.dp)
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${price.toInt()} đ",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                ),
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            if (oldPrice > price) {
                Text(
                    text = oldPrice.toString().replace(".0", "") + " đ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    ),
                    textAlign = TextAlign.End,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

@Composable
private fun ProductDescription(
    modifier: Modifier = Modifier,
    description: String,
    quantity: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF555555)
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        ProductQuantitySelector(
            quantity = quantity,
            onMinusClick = onMinusClick,
            onPlusClick = onPlusClick
        )
    }
}

@Composable
private fun ProductQuantitySelector(
    modifier: Modifier = Modifier,
    quantity: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 1.dp,
                color = Color(0xFFCACACA)
            )
    ) {
        IconButton(
            onClick = onMinusClick,
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
            onClick = onPlusClick,
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

@Composable
private fun ProductRatingAndFavorite(
    modifier: Modifier = Modifier,
    rating: Double,
    onRateClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            //.width(350.dp)
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onRateClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = 0.dp, y = (-4).dp),
            )
            Text(
                text = rating.toString(),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xFF5D4037)
                )
            )
            Text(
                text = "21",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xFF5D4037)
                )
            )
            Text(
                text = "Đánh giá",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xFF5D4037)
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onFavoriteClick) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fav),
                    tint = Color.Red,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(32.dp)
                )
                if (isFavorite)
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = Color.Red,
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(32.dp)
                    )

            }
        }
    }
}

@Composable
private fun ProductCustomizationSection(
    productViewModel: ProductViewModel,
) {
    val product by productViewModel.selectedProduct.collectAsState()

    Box(
        modifier = Modifier
            .width((getScreenWidth() - 30).dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(size = 10.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tiêu đề phân loại sản phẩm
            ProductCustomizationTitle()
            when (product) {
                is FoodProduct -> ProductFoodCustomization(
                    onOption1Click = { option ->
                        productViewModel.setFlavor(Flavor.entries.find { it.value == option }!!)
                        //product.selectedFlavor = Flavor.entries.find { it.value == option }!!
                    },
                    onOption2Click = { option ->
                        productViewModel.setWeight(Weight.entries.find { it.value == option }!!)
                        //product.selectedWeight = Weight.entries.find { it.value == option }!!
                    },
                    selectedOption1 = (product as FoodProduct).selectedFlavor.value,
                    selectedOption2 = (product as FoodProduct).selectedWeight.value
                )

                is ToyProduct -> ProductToyCustomization(
                    onOptionClick = { option ->
                        productViewModel.setSize(Size.entries.find { it.value == option }!!)
                        //product.selectedSize = Size.entries.find { it.value == option }!!
                    },
                    selectedOption = (product as ToyProduct).selectedSize.value
                )

                is ClothesProduct -> ProductClothesCustomization(
                    onOptionClick = { option ->
                        productViewModel.setSize(Size.entries.find { it.value == option }!!)
                        //product.selectedSize = Size.entries.find { it.value == option }!!
                    },
                    selectedOption = (product as ClothesProduct).selectedSize.value
                )

                else -> {}
            }
        }
    }
}

@Composable
private fun ProductCustomizationTitle() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "Chọn phân loại sản phẩm:",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF3C3C3C)
            )
        )
    }
}


@Composable
private fun ProductFoodCustomization(
    onOption1Click: (String) -> Unit,
    onOption2Click: (String) -> Unit,
    selectedOption1: String,
    selectedOption2: String,
) {
    Column {
        ProductCustomizationOption(
            title = "Vị:",
            options = Flavor.entries.map { it.value },
            onOptionClick = onOption1Click,
            selectedOption = selectedOption1,
        )
        ProductCustomizationOption(
            title = "Kích cỡ:",
            options = Weight.entries.map { it.value },
            onOptionClick = onOption2Click,
            selectedOption = selectedOption2,
        )
    }
}

@Composable
private fun ProductClothesCustomization(
    onOptionClick: (String) -> Unit,
    selectedOption: String,
) {
    Column {
        ProductCustomizationOption(
            title = "Kích cỡ:",
            options = Size.entries.map { it.value },
            onOptionClick = onOptionClick,
            selectedOption = selectedOption,
        )
    }
}

@Composable
private fun ProductToyCustomization(
    onOptionClick: (String) -> Unit,
    selectedOption: String,
) {
    Column {
        ProductCustomizationOption(
            title = "Kích cỡ:",
            options = Size.entries.map { it.value },
            onOptionClick = onOptionClick,
            selectedOption = selectedOption,
        )
    }
}

@Composable
fun ProductCustomizationOption(
    title: String,
    options: List<String>,
    onOptionClick: (String) -> Unit,
    selectedOption: String,
    backgroundColor: Color = Color.White
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C3C3C)
            ),
            modifier = Modifier
                .width(80.dp)
                .padding(start = 10.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            modifier = Modifier.weight(1f)
        ) {
            items(options) { option ->
                ProductCustomizationOptionItem(
                    option = option,
                    onClick = { onOptionClick(option) },
                    isSelected = selectedOption == option
                )
            }
        }
    }
}

@Composable
private fun ProductCustomizationOptionItem(
    option: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    AssistChip(
        onClick = onClick,
        colors = AssistChipDefaults.assistChipColors(
            containerColor =
            if (isSelected) Color(0xFF5D4037)
            else Color.White,
        ),
        label = {
            Text(
                option,
                style = MaterialTheme.typography.labelLarge.copy(
                    color =
                    if (isSelected) Color.White
                    else Color(0xFF5D4037)
                )
            )
        },
    )
}

@Composable
private fun ProductDescriptionSection(description: String) {
    Box(
        modifier = Modifier
            .width((getScreenWidth() - 30).dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(size = 10.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            ProductDescriptionTitle()
            ProductDescriptionText(description)
        }
    }
}

@Composable
private fun ProductDescriptionTitle() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF))
            .padding(10.dp)
    ) {
        Text(
            text = "Thông tin sản phẩm:",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF3C3C3C)
            )
        )
    }
}

@Composable
private fun ProductDescriptionText(description: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF555555)
            ),
            textAlign = TextAlign.Justify
        )
    }
}


@Preview
@Composable
fun ProductDetailPreview() {
    ProductDetail(
        productViewModel = ProductViewModel(),
        userViewModel = UserViewModel(),
        cartViewModel = CartViewModel(productViewModel = ProductViewModel()),
        orderViewModel = OrderViewModel(),
        productId = "1"
    )
}
