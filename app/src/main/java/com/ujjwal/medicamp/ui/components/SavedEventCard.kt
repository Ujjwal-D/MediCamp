package com.ujjwal.medicamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event

@Composable
fun SavedEventCard(event: Event) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = event.title,
                color = Color(0xFF1976D2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Date Box
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(50.dp)
                        .background(Color.White, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val dateParts = event.date.split(" ")
                        val month = dateParts.getOrNull(0) ?: ""
                        val day = dateParts.getOrNull(1) ?: ""
                        Text(month, fontWeight = FontWeight.Bold)
                        Text(day, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Location and Time
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pin),
                            contentDescription = "Location",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = event.location, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "Time",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = event.time, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
