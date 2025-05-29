package com.ujjwal.medicamp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.ui.components.SavedEventCard
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun SavedScreen(
    viewModel: EventViewModel,
    onBack: () -> Unit = {}
) {
    val savedEvents by viewModel.savedEvents.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE0F7FA))
                )
            )
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Saved",
                    fontSize = 22.sp,
                    color = Color(0xFF1976D2),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(savedEvents) { event ->
                    SavedEventCard(event)
                }
            }
        }
    }
}
