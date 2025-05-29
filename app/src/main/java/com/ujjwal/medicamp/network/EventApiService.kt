package com.ujjwal.medicamp.network

import com.ujjwal.medicamp.network.dto.EventResponse
import retrofit2.http.GET

interface EventApiService {
    @GET("properties.json")
    suspend fun fetchEvents(): EventResponse
}
