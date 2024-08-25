package com.example.petshop.model

import com.example.petshop.R

data class Voucher(
    var code: String = "",

    var title: String = "",
    var description: String = "",
    var image: Int = R.drawable.voucher,

    var isDiscountByPercent: Boolean = true,
    var discount: Double = 0.0,
    var discountPercent: Double = 0.0,
    var discountMax: Double = 0.0,
    var minOrderValue: Double = 0.0,
)