package com.ujjwal.medicamp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ujjwal.medicamp.ui.screens.*
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun MediCampNavGraph(
    navController: NavHostController,
    viewModel: EventViewModel
) {

    val startDestination = "home"


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onAuthSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    // TODO: handle forgot password
                }
            )
        }

        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onSavedClick = { navController.navigate("saved") },
                onProfileClick = { navController.navigate("profile") }
            )
        }

        composable("saved") {
            SavedScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("profile") {
            ProfileScreen(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
