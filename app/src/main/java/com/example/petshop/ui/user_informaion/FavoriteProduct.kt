package com.example.petshop.ui.user_informaion

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
import com.example.petshop.ui.home.SquareProductWithStar
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.ProductViewModel
import com.example.petshop.view_model.UserViewModel


@Composable
fun FavoriteProductScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel,
    userViewModel: UserViewModel,
    onProductClick: (String) -> Unit
) {
    val cartNumber by cartViewModel.productsInCart.collectAsState()
    val allProducts by productViewModel.allProducts.collectAsState()
    val user by userViewModel.currentUser.collectAsState()
    val favoriteProductsId = user.favoriteProducts

    val favoriteProducts = allProducts.filter { product ->
        favoriteProductsId.contains(product.id)
    }

    Scaffold(
        topBar = {
            TopAppBarNoSearch(
                title = Screen.FavoriteProductScreen.title,
                isCartEnable = true,
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.ShoppingCartScreen.route) },
                cartNumber = cartNumber.size
            )
        }
    ) {
        if (favoriteProducts.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Bạn chưa có sản phẩm yêu thích nào!",
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
                items(favoriteProducts) { product ->
                    SquareProductWithStar(
                        product = product,
                        onProductClick = onProductClick
                    )
                }
            }
    }
}