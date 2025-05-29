package com.ujjwal.medicamp.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.utils.EmailUtils
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun EventDetailScreen(
    event: Event,
    viewModel: EventViewModel,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    var isSaved by remember { mutableStateOf(event.isSaved) }

    val defaultDescription = when (event.category) {
        "Skin Health" -> "Free skin cancer screenings by certified dermatologists. Walk-ins welcome. Bring a valid ID."
        "Mental Health" -> "Free consultations with licensed counselors and psychologists."
        "General Health" -> "Basic health checkups including BP, sugar, and BMI screenings."
        "Vaccination" -> "Free immunizations. Walk-in available with valid documents."
        "Dental Camp" -> "Oral health screenings and free dental hygiene kits."
        "Women’s Health" -> "Special focus on maternal wellness and gynecology."
        else -> "Free health camp with on-site professionals. Walk-ins welcome."
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFC7F2FF), Color(0xFF4DBFE9))
                )
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = event.category,
                    color = Color(0xFF1976D2),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = event.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = event.location,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = event.description.ifBlank { defaultDescription },
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share with others", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    val emailIntent = EmailUtils.createEmailIntent(context, event)
                    emailIntent?.let {
                        context.startActivity(Intent.createChooser(it, "Share via"))
                    }
                }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Map Preview
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clickable {
                        val intent = Intent(context, MapActivity::class.java)
                        intent.putExtra("event", event)
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tap to view on map",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Save/Unsave Button
            Button(
                onClick = {
                    val newSavedState = !isSaved
                    isSaved = newSavedState
                    viewModel.markSaved(event.id, newSavedState)
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = "Save",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSaved) "Saved" else "Save",
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}
