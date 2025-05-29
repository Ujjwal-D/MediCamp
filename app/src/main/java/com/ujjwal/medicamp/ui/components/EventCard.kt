package com.ujjwal.medicamp.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.utils.EmailUtils

@Composable
fun EventCard(event: Event, onClick: () -> Unit = {}) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = event.title, style = MaterialTheme.typography.titleMedium)
                Text(text = event.category, style = MaterialTheme.typography.bodySmall)
                Text(text = event.location, style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = {
                val emailIntent = EmailUtils.createEmailIntent(context, event)
                emailIntent?.let {
                    context.startActivity(Intent.createChooser(it, "Share via"))
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share event"
                )
            }
        }
    }
}
