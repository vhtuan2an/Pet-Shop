@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.petshop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petshop.R
import com.example.petshop.model.BottomNavigationBarItem
import com.example.petshop.ui.login_register.Button
import com.example.petshop.ui.product_infor.ProductCustomizationOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNoSearch(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    isCartEnable: Boolean = false,
    cartNumber: Int = 0,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        if (isCartEnable) {
            IconButton(onClick = onCartClick) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.shopping_cart),
                        contentDescription = "Giỏ hàng",
                        tint = Color(0xFF5D4037),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                    if (cartNumber > 0)
                        Badge(
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 4.dp, y = (-5).dp)
                        ) {
                            Text(
                                cartNumber.toString(),
                                style = TextStyle(
                                    fontSize = if (cartNumber > 9) 7.sp else 12.sp,
                                    fontWeight = FontWeight(700),
                                    color = Color.White
                                )
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun CheckoutBottomBar(
    modifier: Modifier = Modifier,
    total: Double = 0.0,
    onBuyClicked: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(70.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 14.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = "Tổng\nthanh toán:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(end = 5.dp)
        )
        Text(
            text = total.toString().replace(".0", "") + "đ",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Red
            ),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)

        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = onBuyClicked,
            title = "Mua hàng",
            isDisable = false,
            color = Color(0xFFEE2828),
        )
    }
}


@Composable
fun PetShopNavigationBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onUserClick: () -> Unit = {},
    updateIndex: (Int) -> Unit = {},
    selectedIndex: Int = 0
) {
    val items = listOf(
        BottomNavigationBarItem(
            label = "Trang chủ",
            selectIcon = painterResource(id = R.drawable.home_icon_selected),
            unSelectIcon = painterResource(id = R.drawable.home_icon_unselected),
            onClick = onHomeClick
        ),
        BottomNavigationBarItem(
            label = "Chat",
            selectIcon = painterResource(id = R.drawable.message_icon_unselected),
            unSelectIcon = painterResource(id = R.drawable.message_icon_unselected),
            onClick = onChatClick
        ),
        BottomNavigationBarItem(
            label = "Tài khoản",
            selectIcon = painterResource(id = R.drawable.user_icon_selected),
            unSelectIcon = painterResource(id = R.drawable.user_icon_unselected),
            onClick = onUserClick
        )
    )

    NavigationBar(
        modifier = modifier,
    ) {
        items.forEach { item ->
            val index = items.indexOf(item)
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if (index == selectedIndex) item.selectIcon else item.unSelectIcon,
                        contentDescription = null,
                        tint = Color.Unspecified // Use Color.Unspecified to keep the original color of the icons
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (index == selectedIndex) Color(0xFF5D4037) else Color.Gray
                    )
                },
                selected = (index == selectedIndex),
                onClick = {
                    updateIndex(index)
                    item.onClick()
                },
                // Sửa màu sắc của NavigationBarItem
                colors = NavigationBarItemDefaults.colors(
                    //selectedIconColor = Color(0xFF5D4037),
                    //unselectedIconColor = Color.Gray,
                    //selectedTextColor = Color(0xFF5D4037),
                    //unselectedTextColor = Color.Gray,
                    //indicatorColor = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearch(
    onSearchTextChanged: (String) -> Unit,
    onSearchIconClicked: () -> Unit,
    filterClicked: () -> Unit,
    notificationClicked: () -> Unit,
    cartClicked: () -> Unit,
    searchText: String = "",
    cartNumber: Int = 0,
    notiNumber: Int = 0,
    isSheetOpen: Boolean = false,
    onDismissRequset: () -> Unit = {},

    sheetState: SheetState,
    selectedRatingOption: String,
    selectedDiscountOption: String,
    onRateChange: (String) -> Unit,
    onDiscountChange: (String) -> Unit,
    resetFilterClick: () -> Unit,

    ) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val ratingList: List<String> = listOf("< 1 sao", "1-2 sao", "2-3 sao", "3-4 sao", "4-5 sao")
    val discountList: List<String> = listOf("Đang giảm giá", "Không giảm giá")
    // Filter

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp)
        //.background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            if (isSheetOpen)
                ModalBottomSheet(
                    onDismissRequest = onDismissRequset,
                    sheetState = sheetState,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text = "Lọc",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Row {
                            TextButton(
                                onClick = {
                                    resetFilterClick()
                                    onDismissRequset()
                                }
                            ) {
                                Text("Đặt lại", style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(onClick = onDismissRequset) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done",
                                    modifier = Modifier.size(32.dp),
                                    tint = Color(0xFF09DF1F)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    ProductCustomizationOption(
                        "Đánh giá",
                        options = ratingList,
                        onOptionClick = onRateChange,
                        selectedOption = selectedRatingOption,
                        backgroundColor = Color.Transparent
                    )
                    ProductCustomizationOption(
                        "Khuyến mãi",
                        options = discountList,
                        onOptionClick = onDiscountChange,
                        selectedOption = selectedDiscountOption,
                        backgroundColor = Color.Transparent
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                }
            // hết modal bottom sheet

            Column {
                Spacer(modifier = Modifier.height(9.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .border(1.dp, Color(0xFFCACACA), RoundedCornerShape(16.dp))

                        // nền trong suốt
                        .background(Color.Transparent)
                )
            }
            OutlinedTextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        "Tìm kiếm",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(color = Color(0xFFCACACA))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchIconClicked()
                        // Đóng bàn phím khi nhấn search
                        keyboardController?.hide()
                    },
                ),
                leadingIcon = {
                    IconButton(
                        onClick = onSearchIconClicked,
                        modifier = Modifier.padding(0.dp),
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF5D4037),
                            modifier = Modifier.padding(0.dp),
                        )
                    }
                },
                // làm mờ viền để tự thêm viền của mình
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp) // Remove padding from the TextField itself
            )
        }

        IconButton(onClick = filterClicked) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Lọc",
                tint = Color(0xFF5D4037),
            )
        }
        IconButton(onClick = notificationClicked) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Giỏ hàng",
                    tint = Color(0xFF5D4037),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                if (notiNumber > 0)
                    Badge(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-5).dp)
                    ) {
                        Text(
                            notiNumber.toString(),
                            style = TextStyle(
                                fontSize = if (notiNumber > 9) 7.sp else 12.sp,
                                fontWeight = FontWeight(700),
                                color = Color.White
                            )
                        )
                    }
            }
        }
        IconButton(onClick = cartClicked) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = "Giỏ hàng",
                    tint = Color(0xFF5D4037),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                if (cartNumber > 0)
                    Badge(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-5).dp)
                    ) {
                        Text(
                            cartNumber.toString(),
                            style = TextStyle(
                                fontSize = if (cartNumber > 9) 7.sp else 12.sp,
                                fontWeight = FontWeight(700),
                                color = Color.White
                            )
                        )
                    }
            }
        }
    }
}

@Composable
fun CheckoutEndBar(
    total: Double = 0.0,
    onCheckoutClick: () -> Unit = {}
) {
    BottomAppBar(
        modifier = Modifier
            .height(70.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 14.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Tổng cộng",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "${total.toInt()}đ", // Format the total without .0
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onSecondaryContainer, // Ensure color consistency
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                title = "Thanh toán",
                onClick = onCheckoutClick,
                modifier = Modifier.padding(horizontal = 8.dp),
                isDisable = false,
                color = Color(0xFFEE2828)
            )
        }
    }
}

@Composable
fun NewCheckoutEndBar(
    modifier: Modifier = Modifier,
    total: Double = 0.0,
    onCheckoutClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(70.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 14.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = "Tổng cộng:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(end = 5.dp)
        )
        Text(
            text = total.toString().replace(".0", "") + "đ",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Red
            ),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)

        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = onCheckoutClick,
            title = "Thanh toán",
            isDisable = false,
            color = Color(0xFFEE2828),
        )
    }
}

@Composable
fun DetailProductBottomBar(
    modifier: Modifier = Modifier,
    onChatClicked: () -> Unit = {},
    onAddToCartClicked: () -> Unit = {},
    onBuyClicked: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 70.dp, maxHeight = 200.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        // Chat button
        IconButton(onClick = onChatClicked) {
            Image(
                painter = painterResource(id = R.drawable.message_icon_selected),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        // Add to cart button
        Button(
            onClick = onAddToCartClicked,
            title = "Thêm vào\ngiỏ hàng",
            isDisable = false,
            color = Color(0xFF46AE7C),
            modifier = Modifier.height(55.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Buy button
        Button(
            onClick = onBuyClicked,
            title = "Mua hàng",
            isDisable = false,
            color = Color(0xFFEE2828),
            modifier = Modifier.height(55.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetShopTopAppBarPreview() {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    TopAppBarWithSearch(
        filterClicked = { },
        notificationClicked = { },
        cartClicked = { },
        onSearchIconClicked = { },
        onSearchTextChanged = { },
        isSheetOpen = isSheetOpen,
        onDismissRequset = { isSheetOpen = false },
        sheetState = sheetState,
        selectedDiscountOption = "Đang giảm giá",
        selectedRatingOption = "4-5 sao",
        onDiscountChange = { },
        onRateChange = { },
        resetFilterClick = { }
    )
}

/*@Preview
@Composable
fun DetailProductBottomBarPreview() {
    DetailProductBottomBar()
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBarNoSearch(
        title = "test voi title dai qua them nua ddi van chua du dai",
        isCartEnable = true
    )
}*/

/*
@Preview(showBackground = true)
@Composable
fun NewCheckoutEndBarPreview() {
    NewCheckoutEndBar(total = 120000.0)
}

@Preview(showBackground = true)
@Composable
fun CheckoutEndBarPreview() {
    CheckoutEndBar(total = 120000.0)
}

@Preview(showBackground = true)
@Composable
fun PetShopNavigationBarPreview() {
    PetShopNavigationBar()
}



@Preview
@Composable
fun CheckoutBottomBarPreview() {
    CheckoutBottomBar(
        total = 120000.0
    )
}
*/


