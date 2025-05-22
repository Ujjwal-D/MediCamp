package com.ujjwal.medicamp.db

import androidx.room.*
import com.ujjwal.medicamp.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Event>)

    @Query("SELECT * FROM events ORDER BY title ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT COUNT(*) FROM events")
    suspend fun getCount(): Int
}
