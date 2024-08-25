package com.example.petshop.model

import kotlin.random.Random

val random: Random = Random(0)

enum class OrderStatus {
    PREPARE,
    SHIPPING,
    DELIVERED,
}

enum class DeliveryMethod {
    NORMAL,
    FAST,
}

enum class PaymentMethod {
    CASH,
    BANK,
}

data class Order(
    var id: String = random.nextLong(999999999999).toString(),

    var user: User = User(),
    var products: List<Product> = listOf(),

    var deliveryMethod: DeliveryMethod = DeliveryMethod.NORMAL,
    var paymentMethod: PaymentMethod = PaymentMethod.CASH,
    var voucher: Voucher? = null,
    var discount: Double = 0.0,

    var productTotal: Double = 0.0,
    var shippingFee: Double = 12000.0,
    var total: Double = 0.0,

    var status: OrderStatus = OrderStatus.PREPARE
)