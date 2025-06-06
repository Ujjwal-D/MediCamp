package com.ujjwal.medicamp.db

import androidx.room.*
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.model.UserAuth
import com.ujjwal.medicamp.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Event>)

    @Query("SELECT * FROM events ORDER BY title ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT COUNT(*) FROM events")
    suspend fun getCount(): Int

    @Query("UPDATE events SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateIsSaved(id: String, isSaved: Boolean)

    @Query("SELECT * FROM events WHERE isSaved = 1 ORDER BY title ASC")
    fun getSavedEvents(): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserAuth)

    @Query("SELECT * FROM user_auth WHERE username = :username")
    suspend fun getUser(username: String): UserAuth?

    // method to change password securely
    @Query("UPDATE user_auth SET passwordHash = :newHash WHERE username = :username")
    suspend fun updatePassword(username: String, newHash: String)

    @Query("UPDATE events SET isSaved = 0")
    suspend fun clearAllSavedEvents()

}
