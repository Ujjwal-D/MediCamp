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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event
import com.ujjwal.medicamp.ui.components.BottomNavigationBar
import com.ujjwal.medicamp.utils.EmailUtils
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun EventDetailScreen(
    event: Event,
    viewModel: EventViewModel,
    onBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSavedClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
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

    val location = LatLng(event.lat, event.lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 12f)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onSavedClick = onSavedClick,
                onProfileClick = onProfileClick
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF34ebde).copy(alpha = 0.5f), Color.White)
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = event.category,
                    color = Color(0xFF1976D2),
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = event.title,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = 20.sp,
                color = Color(0xFF212121),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = event.location,
                fontSize = 16.sp,
                color = Color(0xFF212121),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = event.description.ifBlank { defaultDescription },
                fontSize = 16.sp,
                color = Color(0xFF212121),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text("Contact", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(6.dp))
            Text("☎ ${event.phone}", fontSize = 15.sp, color = Color(0xFF212121))
            Text("📧 ${event.email}", fontSize = 15.sp, color = Color(0xFF212121))
            Spacer(modifier = Modifier.height(16.dp))

            // Share row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share with others", fontSize = 16.sp, color = Color(0xFF1976D2))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    val emailIntent = EmailUtils.createEmailIntent(context, event)
                    emailIntent?.let {
                        context.startActivity(Intent.createChooser(it, "Share via"))
                    }
                }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color(0xFF0288D1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Map Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = false,
                        mapType = MapType.NORMAL
                    )
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = event.title
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Save Button
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
                    fontSize = 16.sp,
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}
