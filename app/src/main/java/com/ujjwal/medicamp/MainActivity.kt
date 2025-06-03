package com.ujjwal.medicamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.ui.screens.*
import com.ujjwal.medicamp.ui.theme.MedicampTheme
import com.ujjwal.medicamp.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicampTheme {
                val viewModel: EventViewModel = viewModel()

                // Start with login screen
                var isAuthenticated by remember { mutableStateOf(false) }

                // UI state for other screens
                var currentScreen by remember { mutableStateOf("home") }
                var selectedCategory by remember { mutableStateOf<String?>(null) }
                var selectedEvent by remember { mutableStateOf<Event?>(null) }

                // If not logged in, show login/signup screen
                if (!isAuthenticated) {
                    LoginScreen(
                        viewModel = viewModel,
                        onAuthSuccess = {
                            isAuthenticated = true
                            currentScreen = "home"
                        },
                        onForgotPasswordClick = {
                            // Optionally handle forgot password here
                        }
                    )
                } else {
                    when {
                        selectedEvent != null -> {
                            EventDetailScreen(
                                event = selectedEvent!!,
                                viewModel = viewModel,
                                onBack = { selectedEvent = null },
                                onHomeClick = {
                                    selectedEvent = null
                                    selectedCategory = null
                                    currentScreen = "home"
                                },
                                onSavedClick = {
                                    selectedEvent = null
                                    selectedCategory = null
                                    currentScreen = "saved"
                                },
                                onProfileClick = {
                                    selectedEvent = null
                                    selectedCategory = null
                                    currentScreen = "profile"
                                }
                            )
                        }

                        selectedCategory != null -> {
                            CategoryEventsScreen(
                                categoryName = selectedCategory!!,
                                viewModel = viewModel,
                                onEventClick = { selectedEvent = it },
                                onBack = { selectedCategory = null },
                                onHomeClick = {
                                    selectedCategory = null
                                    currentScreen = "home"
                                },
                                onSavedClick = {
                                    selectedCategory = null
                                    currentScreen = "saved"
                                },
                                onProfileClick = {
                                    selectedCategory = null
                                    currentScreen = "profile"
                                }
                            )
                        }

                        currentScreen == "saved" -> {
                            SavedScreen(
                                viewModel = viewModel,
                                onEventClick = { selectedEvent = it },
                                onBack = { currentScreen = "home" },
                                onHomeClick = { currentScreen = "home" },
                                onSavedClick = { /* no-op */ },
                                onProfileClick = {
                                    currentScreen = "profile"
                                }
                            )
                        }

                        currentScreen == "profile" -> {
                            ProfileScreen(
                                viewModel = viewModel,
                                onLogout = {
                                    viewModel.logout()
                                    isAuthenticated = false
                                    currentScreen = "home"
                                },
                                onHomeClick = { currentScreen = "home" },
                                onSavedClick = { currentScreen = "saved" },
                                onProfileClick = { currentScreen = "profile" }
                            )
                        }

                        else -> {
                            HomeScreen(
                                viewModel = viewModel,
                                onSavedClick = { currentScreen = "saved" },
                                onProfileClick = { currentScreen = "profile" },
                                onCategoryClick = { selectedCategory = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
