package com.ujjwal.medicamp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ujjwal.medicamp.db.EventDatabase
import com.ujjwal.medicamp.repository.EventRepository
import com.ujjwal.medicamp.utils.SeedData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventRepository

    // Declare this after initializing `repository`
    val allEvents: Flow<List<com.ujjwal.medicamp.model.Event>>

    init {
        val dao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(dao)

        // Now it's safe to access repository
        allEvents = repository.allEvents
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        seedIfEmpty()
    }

    private fun seedIfEmpty() {
        viewModelScope.launch {
            repository.insertSeedIfEmpty(SeedData.defaultEvents())
        }
    }
}
