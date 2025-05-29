package com.ujjwal.medicamp.network.dto

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("property") val events: List<EventDto>
)

data class EventDto(
    val id: String,
    val address: String,
    val price: Int,
    val phone: String,
    val lat: Double,
    val lon: Double
)
