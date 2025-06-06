package com.ujjwal.medicamp.network.dto

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("property") val events: List<EventDto>
)

data class EventDto(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val location: String,
    val lat: Double,
    val lon: Double,
    val date: String,
    val time: String,
    val phone: String,
    val email: String,
    val isSaved: Boolean
)
