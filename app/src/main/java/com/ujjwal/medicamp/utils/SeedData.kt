package com.ujjwal.medicamp.utils

import com.ujjwal.medicamp.model.Event

object SeedData {
    fun defaultEvents(): List<Event> = listOf(
        Event(
            title = "Skin Check Camp",
            category = "Skin Health",
            description = "Free skin checkups for early detection",
            location = "City Hall, Brisbane",
            lat = -27.4705,
            lon = 153.0260
        ),
        Event(
            title = "Vaccination Drive",
            category = "Vaccination",
            description = "Free flu and COVID-19 shots",
            location = "Red Cross Centre, Sydney",
            lat = -33.8688,
            lon = 151.2093
        )
    )
}
