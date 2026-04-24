package com.travelwaka.app.network.model

data class WisataListResponse(
    val status: Boolean,
    val message: String,
    val data: List<Wisata>?
)

data class WisataDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Wisata?
)

data class CategoryListResponse(
    val status: Boolean,
    val message: String,
    val data: List<Category>?
)