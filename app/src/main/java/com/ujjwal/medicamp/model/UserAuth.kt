package com.ujjwal.medicamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_auth")
data class UserAuth(
    @PrimaryKey val username: String,
    val passwordHash: String
)
