package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import com.example.petshop.data.dummyProductData
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.Flavor
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Product
import com.example.petshop.model.Size
import com.example.petshop.model.ToyProduct
import com.example.petshop.model.Weight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(dummyProductData)
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    fun setSelectedProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun setFlavor(flavor: Flavor) {
        val currentProduct = _selectedProduct.value
        if (currentProduct is FoodProduct) {
            _selectedProduct.value = currentProduct.copy(selectedFlavor = flavor)
        }
    }

    fun setWeight(weight: Weight) {
        val currentProduct = _selectedProduct.value
        if (currentProduct is FoodProduct) {
            _selectedProduct.value = currentProduct.copy(selectedWeight = weight)
        }
    }

    fun setSize(size: Size) {
        when (val currentProduct = _selectedProduct.value) {
            is ToyProduct -> _selectedProduct.value = currentProduct.copy(selectedSize = size)
            is ClothesProduct -> _selectedProduct.value = currentProduct.copy(selectedSize = size)
            is FoodProduct -> TODO()
            null -> TODO()
        }
    }

    fun toggleFavorite() {
        _selectedProduct.update {
            it?.let { product ->
                when (product) {
                    is FoodProduct -> product.copy(isFavorite = !product.isFavorite)
                    is ToyProduct -> product.copy(isFavorite = !product.isFavorite)
                    is ClothesProduct -> product.copy(isFavorite = !product.isFavorite)
                }
            }
        }
    }

    fun decreaseQuantity() {
        if (_selectedProduct.value?.quantity == 1) return
        _selectedProduct.update {
            it?.let { product ->
                when (product) {
                    is FoodProduct -> product.copy(quantity = product.quantity - 1)
                    is ToyProduct -> product.copy(quantity = product.quantity - 1)
                    is ClothesProduct -> product.copy(quantity = product.quantity - 1)
                }
            }
        }
    }

    fun increaseQuantity() {
        _selectedProduct.update {
            it?.let { product ->
                when (product) {
                    is FoodProduct -> product.copy(quantity = product.quantity + 1)
                    is ToyProduct -> product.copy(quantity = product.quantity + 1)
                    is ClothesProduct -> product.copy(quantity = product.quantity + 1)
                }
            }
        }
    }
}
