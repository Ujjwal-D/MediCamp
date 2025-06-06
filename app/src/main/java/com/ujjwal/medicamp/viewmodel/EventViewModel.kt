package com.ujjwal.medicamp.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ujjwal.medicamp.db.EventDatabase
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.model.UserAuth
import com.ujjwal.medicamp.model.UserProfile
import com.ujjwal.medicamp.repository.EventRepository
import com.ujjwal.medicamp.utils.SeedData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.security.MessageDigest

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventRepository

    val allEvents: StateFlow<List<Event>>
    val savedEvents: StateFlow<List<Event>>
    val userProfile: StateFlow<UserProfile?>

    private var _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private var _loggedInUsername: String? = null
    private val _usernameState = mutableStateOf<String?>(null)
    val usernameState: State<String?> = _usernameState

    init {
        val dao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(dao)

        // Observe event and profile data from DB
        allEvents = repository.allEvents
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        savedEvents = repository.savedEvents
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        userProfile = repository.getUserProfile()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

        // Initial data operations
        seedIfEmpty()
        syncFromServer()
    }

    private fun seedIfEmpty() {
        viewModelScope.launch {
            repository.insertSeedIfEmpty(SeedData.defaultEvents())
        }
    }

    fun markSaved(id: String, isSaved: Boolean) {
        viewModelScope.launch {
            repository.markEventSaved(id, isSaved)
        }
    }

    fun syncFromServer() {
        viewModelScope.launch {
            try {
                repository.syncRemoteToLocal()
                Log.d("EventViewModel", "Remote data synced successfully.")
            } catch (e: Exception) {
                Log.e("EventViewModel", "Sync failed: ${e.message}", e)
            }
        }
    }

    fun saveUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.saveUserProfile(profile)
        }
    }

    // Handles login with password hash match
    fun login(username: String, password: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        viewModelScope.launch {
            val hash = hashPassword(password)
            if (repository.authenticateUser(username, hash)) {
                _loggedInUsername = username
                _isLoggedIn.value = true
                _usernameState.value = username
                repository.clearSavedStatusForAllEvents()
                onSuccess()
            } else {
                onFail()
            }
        }
    }

    // Handles signup and sets login state
    fun signup(username: String, password: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        viewModelScope.launch {
            val hash = hashPassword(password)
            try {
                repository.registerUser(UserAuth(username, hash))
                _loggedInUsername = username
                _isLoggedIn.value = true
                _usernameState.value = username
                onSuccess()
            } catch (e: Exception) {
                onFail()
            }
        }
    }

    // Handles logout logic
    fun logout() {
        _isLoggedIn.value = false
        _loggedInUsername = null
        _usernameState.value = null
    }

    // Updates password if old hash matches
    fun changePassword(
        oldPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val username = _loggedInUsername
        if (username == null) {
            onFail()
            return
        }

        viewModelScope.launch {
            val oldHash = hashPassword(oldPassword)
            val newHash = hashPassword(newPassword)
            val result = repository.changePassword(username, oldHash, newHash)
            if (result) {
                onSuccess()
            } else {
                onFail()
            }
        }
    }

    fun getLoggedInUsername(): String? {
        return _loggedInUsername
    }

    // Utility to hash password using SHA-256
    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}
