package com.licoding.instagramapp.presentation.main.search

import com.licoding.instagramapp.data.remote.dto.PostResponse

data class SearchUIState(
    val images: List<PostResponse>
)