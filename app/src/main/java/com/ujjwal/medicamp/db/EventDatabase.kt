package com.ujjwal.medicamp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.model.UserAuth
import com.ujjwal.medicamp.model.UserProfile

@Database(
    entities = [Event::class, UserProfile::class, UserAuth::class],
    version = 2,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_db"
                ).fallbackToDestructiveMigration(true)// optional: wipe DB on schema mismatch
                    .build().also { INSTANCE = it }
            }
        }
    }
}
