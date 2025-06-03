package com.ujjwal.medicamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
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
    val date: String = "",
    val time: String = "",
    val phone: String = "",
    val email: String = "",
    val isSaved: Boolean = false
) : Serializable
