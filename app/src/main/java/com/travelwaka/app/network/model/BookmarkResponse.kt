package com.travelwaka.app.network.model

import com.google.gson.annotations.SerializedName

data class BookmarkStatusResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("is_bookmarked") val isBookmarked: Boolean = false
)

data class BookmarkListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<BookmarkItem> = emptyList()
)

data class BookmarkItem(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("wisata_id") val wisataId: Int,
    @SerializedName("wisata") val wisata: Wisata
)