package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Order
import com.example.petshop.model.OrderStatus
import com.example.petshop.model.ToyProduct
import com.example.petshop.model.Voucher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _currentOrder = MutableStateFlow<Order>(Order())
    val currentOrder: StateFlow<Order> = _currentOrder.asStateFlow()

    private val _allOrders = MutableStateFlow<List<Order>>(listOf())
    val allOrders: StateFlow<List<Order>> = _allOrders.asStateFlow()

    private var _allVouchers = MutableStateFlow<List<Voucher>>(
        listOf(
            Voucher(
                title = "Giảm 10% tối đa 20.000 đ",
                description = "Không yêu cầu giá trị đơn hàng",
                image = R.drawable.voucher,
                isDiscountByPercent = true,
                discountPercent = 10.0,
                minOrderValue = 0.0,
                discountMax = 20000.0,
            ),
            Voucher(
                title = "Giảm 15% tối đa 35.000 đ",
                description = "Đơn tối thiểu 20.000 đ",
                image = R.drawable.voucher,
                isDiscountByPercent = true,
                discountPercent = 15.0,
                minOrderValue = 20000.0,
                discountMax = 35000.0,
            ),
            Voucher(
                title = "Giảm 75.000 đ",
                description = "Đơn tối thiểu 280.000 đ",
                image = R.drawable.bca,
                isDiscountByPercent = false,
                discount = 75000.0,
                minOrderValue = 280000.0,
            )
        )
    )
    val allVouchers: StateFlow<List<Voucher>> = _allVouchers.asStateFlow()

    init {
        // Ensure correct initial calculation
        updateOrder(_currentOrder.value)
    }

    private fun calculateProductTotal(order: Order): Double {
        val productTotal = order.products.sumOf { it.price * it.quantity }
        val total = productTotal + order.shippingFee - order.discount

        return total
    }

    fun calculateDiscount(order: Order, voucher: Voucher?): Double {
        if (voucher == null) return 0.0
        val discount = when {
            voucher.isDiscountByPercent -> {
                val discount = order.productTotal * voucher.discountPercent / 100
                if (discount > voucher.discountMax) voucher.discountMax else discount
            }
            else -> voucher.discount
        }
        return discount
    }

    fun updateOrderStatus(status: OrderStatus) {
        _currentOrder.update { order ->
            order.copy(status = status)
        }
    }

    fun autoUpdateOrderStatus(
        orderId: String,
    ) {
        val time: Long = 1000 * 10 // 5 seconds
        viewModelScope.launch {
            delay(time)
            _allOrders.update { orders ->
                orders.map {
                    it.copy(
                        status = if (orderId == it.id) OrderStatus.SHIPPING else it.status
                    )
                }
            }

            delay(time)
            _allOrders.update { orders ->
                orders.map {
                    it.copy(
                        status = if (orderId == it.id) OrderStatus.DELIVERED else it.status
                    )
                }
            }
        }
    }

    fun updateOrder(newOrder: Order) {
        _currentOrder.update {
            // duma toi da nham "newOrder" la "it"
            val discount = calculateDiscount(newOrder, newOrder.voucher)
            val total = calculateProductTotal(newOrder)

            newOrder.copy(
                productTotal = newOrder.products.sumOf { product -> product.price * product.quantity },
                discount = discount,
                total = total,
            )
        }
    }

    fun resetOrder() {
        _currentOrder.update { Order() }
    }

    fun addOrder(order: Order) {
        _allOrders.update { orders ->
            orders.toMutableList().apply { add(order) }
        }

        _allOrders.value.forEach {
            println("Order ID: ${it.id}")
            println("Products count: ${it.products.size}")
            println("Shipping status: ${it.status}")
        }
    }

    fun getStarOfProduct(order: Order, productId: String): Int {
        return order.products.find { it.id == productId }?.star?.toInt() ?: 0
    }

    fun changeRateOfProduct(order: Order, productId: String, newRate: Int) {
        _allOrders.update { orders ->
            orders.map {
                if (it.id == order.id) {
                    it.copy(products = it.products.map { product ->
                        if (product.id == productId) {
                            when (product) {
                                is FoodProduct -> {
                                    product.copy(star = newRate.toDouble())
                                }

                                is ToyProduct -> {
                                    product.copy(star = newRate.toDouble())
                                }

                                is ClothesProduct -> {
                                    product.copy(star = newRate.toDouble())
                                }
                            }
                        } else {
                            product
                        }
                    })
                } else {
                    it
                }
            }
        }
    }

}

