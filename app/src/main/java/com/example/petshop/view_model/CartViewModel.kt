package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petshop.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel(
    private val productViewModel: ProductViewModel
) : ViewModel() {

    var user_id: String? = null
    lateinit var database: DatabaseReference

    private var _selectedProducts = MutableStateFlow<List<Product>>(listOf())
    var selectedProducts: StateFlow<List<Product>> = _selectedProducts.asStateFlow()

    private var _totalAmount = MutableStateFlow(0.0)
    var totalAmount: StateFlow<Double> = _totalAmount.asStateFlow()

    private var _productsInCart = MutableStateFlow<List<Product>>(listOf())
    var productsInCart: StateFlow<List<Product>> = _productsInCart.asStateFlow()

    private var _cartNumber = MutableStateFlow(0)
    var cartNumber: StateFlow<Int> = _cartNumber.asStateFlow()

    fun getProductsByUserId() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.value as? Map<String, Map<String, Any>> ?: return
                println(data)

                val newCartProducts = data.mapNotNull { (productId, productData) ->
                    val type = productData["type"] as? String ?: return@mapNotNull null
                    val quantity = (productData["quantity"] as? Long)?.toInt() ?: return@mapNotNull null

                    val product = productViewModel.allProducts.value.find { it.id == productId } ?: return@mapNotNull null
                    when (type) {
                        "food" -> {
                            val selectedFlavor = Flavor.valueOf(productData["selectedFlavor"] as? String ?: return@mapNotNull null)
                            val selectedWeight = Weight.valueOf(productData["selectedWeight"] as? String ?: return@mapNotNull null)
                            (product as? FoodProduct)?.copy(quantity = quantity, selectedFlavor = selectedFlavor, selectedWeight = selectedWeight)
                        }
                        "toy" -> {
                            val selectedSize = Size.valueOf(productData["selectedSize"] as? String ?: return@mapNotNull null)
                            (product as? ToyProduct)?.copy(quantity = quantity, selectedSize = selectedSize)
                        }
                        "clothes" -> {
                            val selectedSize = Size.valueOf(productData["selectedSize"] as? String ?: return@mapNotNull null)
                            (product as? ClothesProduct)?.copy(quantity = quantity, selectedSize = selectedSize)
                        }
                        else -> null
                    }
                }

                _productsInCart.update { newCartProducts }
                updateCartNumber()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    fun addProductToSelected(product: Product) {
        val products = _selectedProducts.value.toMutableList()
        products.add(product)
        _selectedProducts.update { products }
        updateTotalAmount()
    }

    fun up_set_UserId(id: String) {
        this.user_id = id
        this.database = FirebaseDatabase.getInstance().reference.child("Carts").child(id)
    }

    fun removeProductFromSelected(product: Product) {
        val products = _selectedProducts.value.toMutableList()
        products.remove(product)
        _selectedProducts.update { products }
        updateTotalAmount()
    }

    fun updateTotalAmount() {
        _totalAmount.update { _selectedProducts.value.sumOf { it.price * it.quantity } }
    }

    fun updateSelectedProduct(products: List<Product>) {
        _selectedProducts.update { _ -> products }
    }

    fun addProductToCart(product: Product) {
        val products = _productsInCart.value.toMutableList()
        val existingProductIndex = products.indexOfFirst {
            it.id == product.id && (
                    (it is FoodProduct && product is FoodProduct &&
                            it.selectedFlavor == product.selectedFlavor &&
                            it.selectedWeight == product.selectedWeight) ||
                            (it is ToyProduct && product is ToyProduct &&
                                    it.selectedSize == product.selectedSize) ||
                            (it is ClothesProduct && product is ClothesProduct &&
                                    it.selectedSize == product.selectedSize)
                    )
        }

        if (existingProductIndex != -1) {
            val existingProduct = products[existingProductIndex]
            products[existingProductIndex] = when (existingProduct) {
                is FoodProduct -> existingProduct.copy(quantity = existingProduct.quantity + product.quantity)
                is ToyProduct -> existingProduct.copy(quantity = existingProduct.quantity + product.quantity)
                is ClothesProduct -> existingProduct.copy(quantity = existingProduct.quantity + product.quantity)
            }
        } else {
            products.add(0, product)
        }

        _productsInCart.update { products }

        val data = when (product) {
            is FoodProduct -> mapOf(
                "type" to "food",
                "quantity" to product.quantity,
                "selectedFlavor" to product.selectedFlavor.toString(),
                "selectedWeight" to product.selectedWeight.toString()
            )
            is ToyProduct -> mapOf(
                "type" to "toy",
                "quantity" to product.quantity,
                "selectedSize" to product.selectedSize.toString()
            )
            is ClothesProduct -> mapOf(
                "type" to "clothes",
                "quantity" to product.quantity,
                "selectedSize" to product.selectedSize.toString()
            )
        }

        // Kiểm tra sự tồn tại của sản phẩm trong cơ sở dữ liệu trước khi thêm hoặc cập nhật
        val productRef = database.child(product.id)
        productRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // Sản phẩm đã tồn tại, cập nhật số lượng
                val existingQuantity = snapshot.child("quantity").getValue(Int::class.java) ?: 0
                val newQuantity = existingQuantity + product.quantity
                productRef.child("quantity").setValue(newQuantity)
            } else {
                // Sản phẩm chưa tồn tại, thêm mới
                productRef.setValue(data)
            }
        }.addOnFailureListener {
            // Xử lý lỗi nếu có
        }

        updateCartNumber()
    }


    fun removeProductFromCart(product: Product) {
        val products = _productsInCart.value.toMutableList()
        products.remove(product)
        _productsInCart.update { products }

        database.child(product.id).removeValue()

        updateCartNumber()
    }

    fun updateCartNumber() {
        _cartNumber.update { _productsInCart.value.size }
        println("Cart number: ${_cartNumber.value}")
    }
}

class CartViewModelFactory(
    private val productViewModel: ProductViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(productViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
/** Lý do sử dụng CartViewModelFactory:
 * Đúng vậy, việc khởi tạo CartViewModel một cách thủ công như
 * val cartViewModel = CartViewModel(productViewModel = productViewModel)
 * có thể gây ra vấn đề trong việc cập nhật số sản phẩm vì CartViewModel
 * không được quản lý bởi ViewModelProvider của Compose.
 * Bạn nên để ViewModelProvider quản lý tất cả các ViewModel
 * để đảm bảo chúng được khởi tạo và duy trì đúng cách.
 */

