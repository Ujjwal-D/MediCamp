package com.ujjwal.medicamp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event

@Composable
fun SavedEventCard(
    event: Event,
    onShare: () -> Unit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 0.98f else 1f)

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    pressed = true
                    onClick()
                    pressed = false
                }
            )
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE3F2FD),
                            Color(0xFFBBDEFB).copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(60.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                ) {
                    val (month, day) = extractMonthDay(event.date)
                    Text(
                        text = month,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                    Text(text = day, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pin),
                            contentDescription = "Location",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.location,
                            fontSize = 14.sp,
                            color = Color(0xFF212121)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "Time",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.time.ifBlank { "Time not specified" },
                            fontSize = 14.sp,
                            color = Color(0xFF212121)
                        )
                    }
                }

                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

private fun extractMonthDay(date: String): Pair<String, String> {
    return try {
        val parts = date.split("-")
        val month = parts[1].toInt()
        val day = parts[2]
        val monthName = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )[month - 1]
        monthName to day
    } catch (e: Exception) {
        "N/A" to "??"
    }
}
