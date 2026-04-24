package com.travelwaka.app.network.model

data class Wisata(
    val id: Int,
    val user_id: Int,
    val category_id: Int,
    val name: String,
    val description: String,
    val location: String,
    val latitude: Double?,
    val longitude: Double?,
    val price: String,
    val opening_hours: String?,
    val rating: Float,
    val review_count: Int,
    val category: Category?,
    val photos: List<Photo>?,
    val cover_photo: Photo?,
    val created_at: String?
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String?
)

data class Photo(
    val id: Int,
    val wisata_id: Int,
    val photo_url: String,
    val is_cover: Int  // ← ganti dari Boolean ke Int
)