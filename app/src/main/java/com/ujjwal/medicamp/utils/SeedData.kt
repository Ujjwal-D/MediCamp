package com.ujjwal.medicamp.utils

import com.ujjwal.medicamp.model.Event

object SeedData {
    fun defaultEvents(): List<Event> = listOf(
        Event(
            title = "Wandal Skin Cancer Camp",
            category = "Skin Health",
            description = "",
            location = "Wandal Road, Wandal, Rockhampton",
            lat = -23.3754,
            lon = 150.4826
        ),
        Event(
            title = "Free Mental Wellness Check",
            category = "Mental Health",
            description = "",
            location = "MindSpace Center, Melbourne",
            lat = -37.8136,
            lon = 144.9631
        ),
        Event(
            title = "Dental Hygiene Camp",
            category = "Dental Camp",
            description = "",
            location = "SmileCare Clinic, Adelaide",
            lat = -34.9285,
            lon = 138.6007
        ),
        Event(
            title = "General Health Screening",
            category = "General Health",
            description = "",
            location = "Brisbane City Health Center",
            lat = -27.4698,
            lon = 153.0251
        ),
        Event(
            title = "Child Immunization Drive",
            category = "Immunization",
            description = "",
            location = "Westmead Hospital, Sydney",
            lat = -33.8036,
            lon = 150.9886
        ),
        Event(
            title = "Women’s Health Awareness",
            category = "Women’s Health",
            description = "",
            location = "St. Mary's Clinic, Hobart",
            lat = -42.8821,
            lon = 147.3272
        )
    )
}
