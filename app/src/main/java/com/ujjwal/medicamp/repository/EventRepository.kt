package com.ujjwal.medicamp.repository

import com.ujjwal.medicamp.db.EventDao
import com.ujjwal.medicamp.model.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) {

    val allEvents: Flow<List<Event>> = dao.getAllEvents()

    suspend fun insertSeedIfEmpty(seed: List<Event>) {
        if (dao.getCount() == 0) {
            dao.insertAll(seed)
        }
    }
}
