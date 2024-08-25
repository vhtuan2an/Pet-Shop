package com.example.petshop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petshop.model.Screen
import com.example.petshop.ui.TopAppBarNoSearch
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.ProductViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel,
    query: String,
    onProductClick: (String) -> Unit
) {
    val allProducts by productViewModel.allProducts.collectAsState()
    val cartNumber by cartViewModel.productsInCart.collectAsState()

    val filteredProduct = allProducts.filter { product ->
        product.name.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBarNoSearch(
                title = query,
                isCartEnable = true,
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.ShoppingCartScreen.route) },
                cartNumber = cartNumber.size
            )
        }
    ) {
        if (filteredProduct.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Không tìm thấy sản phẩm nào!\nHãy thử lại!",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp)
                )
            }
        } else
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(filteredProduct) { product ->
                    SquareProductWithStar(
                        product = product,
                        onProductClick = onProductClick
                    )
                }
            }
    }
}