package com.ujjwal.medicamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ujjwal.medicamp.ui.navigation.MediCampNavGraph
import com.ujjwal.medicamp.ui.screens.HomeScreen
import com.ujjwal.medicamp.ui.theme.MedicampTheme
import com.ujjwal.medicamp.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            MedicampTheme {
//                val viewModel: EventViewModel = viewModel()
//                val navController = rememberNavController()
//
//                MediCampNavGraph(
//                    navController = navController,
//                    viewModel = viewModel
//                )
//            }
//        }
        setContent {
            val viewModel: EventViewModel = viewModel()

            HomeScreen(
                viewModel = viewModel,
                onSavedClick = {},
                onProfileClick = {}
            )
        }
    }
}
