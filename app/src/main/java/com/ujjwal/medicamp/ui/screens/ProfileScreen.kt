package com.ujjwal.medicamp.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.UserProfile
import com.ujjwal.medicamp.ui.components.BottomNavigationBar
import com.ujjwal.medicamp.viewmodel.EventViewModel

@Composable
fun ProfileScreen(
    viewModel: EventViewModel,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onSavedClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val savedProfile by viewModel.userProfile.collectAsState()
    var fullName by remember { mutableStateOf(savedProfile?.fullName ?: "") }
    var email by remember { mutableStateOf(savedProfile?.email ?: "") }
    var phone by remember { mutableStateOf(savedProfile?.phone ?: "") }

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(savedProfile == null) }
    var showPasswordFields by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var successText by remember { mutableStateOf("") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var encodedImage by remember { mutableStateOf(savedProfile?.imageBase64) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT)
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onSavedClick = onSavedClick,
                onProfileClick = onProfileClick
            )
        }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "Logout",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        selectedImageUri != null -> {
                            val inputStream = context.contentResolver.openInputStream(selectedImageUri!!)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            bitmap?.let {
                                Image(bitmap = it.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize())
                            }
                        }
                        !encodedImage.isNullOrBlank() -> {
                            val decoded = Base64.decode(encodedImage, Base64.DEFAULT)
                            val bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
                            Image(bitmap = bmp.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize())
                        }
                        else -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Logged in as: ${savedProfile?.fullName ?: "Guest"}",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { if (it.length <= 10) fullName = it },
                    label = { Text("UserName (max 10 chars)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {
                    Button(
                        onClick = {
                            if (fullName.isBlank() || email.isBlank()) {
                                errorText = "Name and Email are required."
                                return@Button
                            }
                            viewModel.saveUserProfile(
                                UserProfile(
                                    id = "singleton_user",
                                    fullName = fullName,
                                    email = email,
                                    phone = phone,
                                    imageBase64 = encodedImage
                                )
                            )
                            successText = "Profile saved successfully!"
                            errorText = ""
                            isEditing = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("Save Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    TextButton(onClick = {
                        showPasswordFields = !showPasswordFields
                        successText = ""
                        errorText = ""
                    }) {
                        Text(if (showPasswordFields) "Hide Password Fields" else "Change Password")
                    }

                    AnimatedVisibility(
                        visible = showPasswordFields,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column {
                            OutlinedTextField(
                                value = oldPassword,
                                onValueChange = { oldPassword = it },
                                label = { Text("Old Password") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation()
                            )

                            OutlinedTextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                label = { Text("New Password") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation()
                            )

                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                label = { Text("Confirm Password") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation()
                            )

                            Button(
                                onClick = {
                                    when {
                                        oldPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() ->
                                            errorText = "All fields are required."
                                        newPassword != confirmPassword ->
                                            errorText = "Passwords do not match."
                                        else -> {
                                            viewModel.changePassword(
                                                oldPassword,
                                                newPassword,
                                                onSuccess = {
                                                    errorText = ""
                                                    successText = "Password updated successfully!"
                                                    oldPassword = ""
                                                    newPassword = ""
                                                    confirmPassword = ""
                                                    showPasswordFields = false
                                                },
                                                onFail = {
                                                    successText = ""
                                                    errorText = "Old password incorrect."
                                                }
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text("Update Password")
                            }
                        }
                    }
                }

                if (errorText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = errorText, color = Color.Red, fontSize = 14.sp)
                }

                if (successText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = successText, color = Color(0xFF2E7D32), fontSize = 14.sp)
                }
            }

            if (!isEditing && savedProfile != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        onClick = { isEditing = true },
                        modifier = Modifier
                            .background(Color(0xFF1976D2), shape = CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }
            }

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            onLogout()
                        }) { Text("Logout") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
                    },
                    title = { Text("Confirm Logout") },
                    text = { Text("Are you sure you want to logout?") }
                )
            }
        }
    }
}
