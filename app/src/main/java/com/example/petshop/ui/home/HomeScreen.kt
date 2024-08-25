package com.example.petshop.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Product
import com.example.petshop.model.ToyProduct
import com.example.petshop.view_model.BannerViewModel
import com.example.petshop.view_model.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

fun filterProductByRate(products: List<Product>, rateFilter: String): List<Product> {
    if (rateFilter == "") {
        return products
    }
    val star = when (rateFilter) {
        "< 1 sao" -> 1
        "1-2 sao" -> 2
        "2-3 sao" -> 3
        "3-4 sao" -> 4
        "4-5 sao" -> 5
        else -> 5
    }
    return products.filter { product ->
        product.star >= star - 1 && product.star <= star
    }
}

fun filterProductByDiscount(products: List<Product>, discountFilter: String): List<Product> {
    if (discountFilter == "") {
        return products
    }
    val isDiscount = when (discountFilter) {
        "Đang giảm giá" -> true
        "Không giảm giá" -> false
        else -> false
    }
    return products.filter { product ->
        if (isDiscount) {
            product.oldPrice > product.price
        } else {
            product.oldPrice == product.price
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController? = null,
    productViewModel: ProductViewModel,
    bannerViewModel: BannerViewModel,
    onProductClick: (String) -> Unit = {},
    rateFilter: String,
    discountFilter: String,
) {
    val allProducts by productViewModel.allProducts.collectAsState()
    val firstTabProduct = allProducts.filterIsInstance<FoodProduct>()
    val secondTabProduct = allProducts.filterIsInstance<ToyProduct>()
    val thirdTabProduct = allProducts.filterIsInstance<ClothesProduct>()
    val bannerItems = bannerViewModel.allBanners

    var selectedTabIndex by remember { mutableStateOf(0) }

    val selectedProducts = when (selectedTabIndex) {
        0 -> filterProductByRate(
            filterProductByDiscount(firstTabProduct, discountFilter),
            rateFilter
        )

        1 -> filterProductByRate(
            filterProductByDiscount(secondTabProduct, discountFilter),
            rateFilter
        )

        2 -> filterProductByRate(
            filterProductByDiscount(thirdTabProduct, discountFilter),
            rateFilter
        )

        else -> listOf()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            HorizontalBanner(bannerItems = bannerItems)
        }
        stickyHeader {
            ProductTabs(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { tab ->
                    selectedTabIndex = tab
                }
            )
        }

        if (selectedProducts.isEmpty()) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp)
                ) {
                    Text(
                        text = "Không có sản phẩm nào!\nHãy thử đổi bộ lọc",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        } else {
            items(selectedProducts.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    for (product in rowProducts) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            SquareProductWithStar(
                                product = product,
                                onProductClick = onProductClick
                            )
                        }
                    }

                    // Add an empty box to fill the row if the number of products is odd
                    if (rowProducts.size < 2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {}
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(70.dp)) }
    }

}

@Composable
fun ProductTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp)
                    .background(color = Color(0xFF5D373F))
                    .offset(y = 13.dp)
            )
        },
        modifier = Modifier
    ) {
        val tabs = listOf("Thức ăn", "Dụng cụ", "Thời trang")
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun ProductWithStar(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier.clickable { onProductClick(product.id) }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)

        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(69.dp)
                    .height(69.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avt),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(69.dp)
                        .height(69.dp)
                        .shadow(
                            elevation = 2.2138051986694336.dp,
                            spotColor = Color(0x05000000),
                            ambientColor = Color(0x05000000)
                        )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(69.dp)
                        .padding(top = 8.dp) // spacing between image and rating
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            2.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(43.dp)
                            .height(20.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .padding(start = 4.dp, top = 1.dp, end = 4.dp, bottom = 1.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "image description",
                            contentScale = ContentScale.None
                        )
                        Text(
                            text = product.star.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f),
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
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = product.price.toString().replace(".0", "") + " đ",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                )
                Text(
                    text = product.oldPrice.toString().replace(".0", "") + " đ",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

@Composable
fun HorizontalBanner(bannerItems: List<Int>) {
    val pagerState = rememberPagerState(pageCount = { bannerItems.size })
    var isAutoScrollEnabled by remember { mutableStateOf(true) }
    var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(pagerState) {
        while (true) {
            yield()
            delay(5000)
            if (isAutoScrollEnabled) {
                val nextPage = (pagerState.currentPage + 1) % bannerItems.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        isAutoScrollEnabled = false
        lastInteractionTime = System.currentTimeMillis()
        delay(5000)
        if (System.currentTimeMillis() - lastInteractionTime >= 5000) {
            isAutoScrollEnabled = true
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) { page ->
        val bannerItem = bannerItems[page]
        Image(
            painter = painterResource(id = bannerItem),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        productViewModel = ProductViewModel(),
        bannerViewModel = BannerViewModel(),
        onProductClick = {},
        rateFilter = "",
        discountFilter = ""
    )
}

@Composable
fun SquareProductWithStar(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(size = 10.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { onProductClick(product.id) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier

        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                    //.width(100.dp)
                    //.height(100.dp)
                )

            }
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,

                    )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        2.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .align(Alignment.Bottom)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .offset(x = 0.dp, y = (-2).dp),
                    )
                    Text(
                        text = product.star.toString(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = product.price.toString().replace(".0", "") + " đ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End,
                    )
                    if (product.oldPrice > product.price) {
                        Text(
                            text = product.oldPrice.toString().replace(".0", "") + " đ",
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
    }
}

@Preview
@Composable
fun SquareProductWithStarPreview() {
    SquareProductWithStar(
        product = FoodProduct(
            id = "1",
            name = "Thức ăn cho mèo dành cho mèo trưởng thành",
            description = "Thức ăn cho mèo dành cho mèo trưởng thành cho mèo dành cho mèo trưởngcho mèo dành cho mèo trưởng",
            price = 100000.0,
            oldPrice = 120000.0,
            star = 4.5,
            detailDescription = "Thức ăn cho mèo dành cho mèo trưởng thành",
            image = R.drawable.avt,
            quantity = 1
        )
    )
}
