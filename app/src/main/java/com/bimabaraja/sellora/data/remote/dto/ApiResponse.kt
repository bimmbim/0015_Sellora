package com.bimabaraja.sellora.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T?
)

data class ApiPaginatedData<T>(
    @SerializedName("items") val items: List<T>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)
