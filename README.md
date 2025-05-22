# MediCamp
Medicamp aims to improve access to preventive healthcare by helping users discover and share free local health camps organized by hospitals, NGOs, and government bodies.

# Project Objective

To design and implement a lightweight, user-friendly mobile app that centralizes the discovery of free community health services like vaccination drives, skin checkups, and mental health camps. MediCamp ensures that vital event information reaches the right audience in time.

# Tech Stack

    Language: Kotlin

    IDE: Android Studio 2025.1.1 (Narwhal)

    Architecture: MVVM (Model-View-ViewModel)

    Database: SQLite with Jetpack Room

    Libraries: LiveData, ViewModel, Retrofit, Gson, Google Maps

# Core Features

    Discover local health events categorized by type (e.g., vaccination, skin health)

    View event locations on an interactive Google Map

    Share event details via email

    Save and view favorite events offline

    Sync data via HTTP JSON fetch from server

    Polished UI with category filters, profile tab, and accessible navigation


# Security Highlights

    OAuth 2.0 Google Sign-In

    JWT session management

    HTTPS with TLS 1.2+

    Encrypted local storage (AES-256)

    Rate limiting, IP throttling, and XSS-safe API endpoints
