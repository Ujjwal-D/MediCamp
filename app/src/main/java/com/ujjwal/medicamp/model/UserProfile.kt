package com.ujjwal.medicamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: String = "singleton_user",
    val fullName: String,
    val email: String,
    val phone: String
) : Serializable
