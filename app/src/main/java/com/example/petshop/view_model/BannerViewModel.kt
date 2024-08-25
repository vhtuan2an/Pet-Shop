package com.example.petshop.view_model

import androidx.lifecycle.ViewModel
import com.example.petshop.R

class BannerViewModel : ViewModel() {
    var allBanners: List<Int> = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
    )
        private set
}