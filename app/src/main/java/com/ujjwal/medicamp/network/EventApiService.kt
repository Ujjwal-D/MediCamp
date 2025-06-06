package com.ujjwal.medicamp.network

import com.ujjwal.medicamp.network.dto.EventResponse
import retrofit2.http.GET

interface EventApiService {
    @GET("6b6c5af7-0393-4441-b593-368ddd38bba0")
    suspend fun fetchEvents(): EventResponse
}
