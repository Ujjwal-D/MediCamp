package com.ujjwal.medicamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String,
    val description: String,
    val location: String,
    val lat: Double,
    val lon: Double,
    val isSaved: Boolean = false
)
