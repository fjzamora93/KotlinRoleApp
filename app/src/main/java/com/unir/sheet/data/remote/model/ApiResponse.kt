package com.unir.sheet.data.remote.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,

    @SerializedName("results") val results: List<Map<String, Any>>

)