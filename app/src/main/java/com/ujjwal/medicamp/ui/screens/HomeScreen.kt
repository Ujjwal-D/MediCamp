package com.ujjwal.medicamp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.ui.components.BottomNavigationBar
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun HomeScreen(
    viewModel: EventViewModel,
    onSavedClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = {},
                onSavedClick = onSavedClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Translucent Top Banner with Title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.header_banner),
                    contentDescription = "Header Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.White.copy(alpha = 0.9f))
                            )
                        )
                )
                Text(
                    text = "MediCamp",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Greeting
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = buildAnnotatedString {
                        append("Goodmorning ")
                        withStyle(SpanStyle(color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)) {
                            append("John")
                        }
                    },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "How can we help you ?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // Category Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between Categories and grid

            // Category Grid
            val categories = listOf(
                Triple("General Health", R.drawable.ic_general_health, "General Health"),
                Triple("Mental Health", R.drawable.ic_mental_health, "Mental Health"),
                Triple("Dental Camp", R.drawable.ic_dental_camp, "Dental Camp"),
                Triple("Skin Health", R.drawable.ic_skin_health, "Skin Health"),
                Triple("Immunization", R.drawable.ic_immunization, "Vaccination"),
                Triple("Women's Health", R.drawable.ic_women_health, "Women’s Health")
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
            ) {
                for ((index, row) in categories.chunked(3).withIndex()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = if (index == 0) 20.dp else 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        row.forEach { (label, icon, _) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        Toast
                                            .makeText(context, "$label clicked", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            ) {
                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    modifier = Modifier
                                        .size(88.dp)
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = icon),
                                            contentDescription = label,
                                            modifier = Modifier.size(64.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = label,
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                // Divider above nav bar
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(0xFFB0BEC5),
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}
