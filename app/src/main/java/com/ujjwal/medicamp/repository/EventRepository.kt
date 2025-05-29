package com.ujjwal.medicamp.repository

import com.ujjwal.medicamp.db.EventDao
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.model.UserAuth
import com.ujjwal.medicamp.network.RetrofitClient
import com.ujjwal.medicamp.network.dto.EventDto
import com.ujjwal.medicamp.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class EventRepository(private val dao: EventDao) {

    val allEvents: Flow<List<Event>> = dao.getAllEvents()
    val savedEvents: Flow<List<Event>> = dao.getSavedEvents()


    suspend fun insertSeedIfEmpty(seed: List<Event>) {
        if (dao.getCount() == 0) {
            dao.insertAll(seed)
        }
    }

    suspend fun registerUser(user: UserAuth) {
        dao.registerUser(user)
    }

    suspend fun authenticateUser(username: String, passwordHash: String): Boolean {
        val user = dao.getUser(username)
        return user?.passwordHash == passwordHash
    }

    suspend fun syncRemoteToLocal() {
        val events = fetchRemoteEvents()
        dao.insertAll(events)
    }

    suspend fun markEventSaved(id: String, isSaved: Boolean) {
        dao.updateIsSaved(id, isSaved)
    }

    fun getUserProfile(): Flow<UserProfile?> = dao.getUserProfile()

    suspend fun saveUserProfile(profile: UserProfile) {
        dao.insertUserProfile(profile)
    }

    private suspend fun fetchRemoteEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            try {
                val service = RetrofitClient.retrofit.create(
                    com.ujjwal.medicamp.network.EventApiService::class.java
                )
                val response = service.fetchEvents()

                response.events.map { dto: EventDto ->
                    Event(
                        id = dto.id,
                        title = "Health Camp - ${dto.price}",
                        category = "General",
                        description = dto.phone,
                        location = dto.address,
                        lat = dto.lat,
                        lon = dto.lon
                    )
                }
            } catch (e: IOException) {
                throw RuntimeException("Network error: ${e.message}")
            } catch (e: HttpException) {
                throw RuntimeException("HTTP error: ${e.message}")
            }
        }
    }
}
