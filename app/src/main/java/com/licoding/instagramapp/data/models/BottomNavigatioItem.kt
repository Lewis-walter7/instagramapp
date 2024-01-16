package com.licoding.instagramapp.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigatioItem(
    val icon: ImageVector,
    val route: String? = null,
)