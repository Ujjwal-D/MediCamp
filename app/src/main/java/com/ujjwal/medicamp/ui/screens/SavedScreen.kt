package com.ujjwal.medicamp.ui.screens

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.ui.components.BottomNavigationBar
import com.ujjwal.medicamp.ui.components.SavedEventCard
import com.ujjwal.medicamp.utils.EmailUtils
import com.ujjwal.medicamp.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    viewModel: EventViewModel,
    onEventClick: (event: Event) -> Unit = {},
    onBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSavedClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val savedEvents by viewModel.savedEvents.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Events") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onSavedClick = onSavedClick,
                onProfileClick = onProfileClick
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF34ebde).copy(alpha = 0.5f), Color.White)
                    )
                )
                .padding(padding)
        ) {
            if (savedEvents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "You haven't saved any events yet.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(savedEvents) { event ->
                        SavedEventCard(
                            event = event,
                            onShare = {
                                val emailIntent = EmailUtils.createEmailIntent(context, event)
                                emailIntent?.let {
                                    context.startActivity(Intent.createChooser(it, "Share via"))
                                }
                            },
                            onClick = { onEventClick(event) }
                        )
                    }
                }
            }
        }
    }
}
