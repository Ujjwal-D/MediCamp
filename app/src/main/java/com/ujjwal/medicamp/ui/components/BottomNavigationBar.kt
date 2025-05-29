package com.ujjwal.medicamp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ujjwal.medicamp.R

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onSavedClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = false, // You can control selected state from caller
            onClick = onHomeClick,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSavedClick,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_saved),
                    contentDescription = "Saved"
                )
            },
            label = { Text("Saved") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") }
        )
    }
}
