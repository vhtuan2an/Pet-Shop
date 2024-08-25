package com.example.petshop.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationBarItem(
    val label: String,
    val selectIcon: Painter,
    val unSelectIcon: Painter,
    val onClick: () -> Unit = {},
)