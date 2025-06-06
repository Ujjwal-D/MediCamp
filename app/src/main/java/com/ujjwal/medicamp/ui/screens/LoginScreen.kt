package com.ujjwal.medicamp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.viewmodel.EventViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: EventViewModel,
    onAuthSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    // State variables for login/signup inputs
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isSignupMode by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // State for forgot password dialog
    var showResetDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }
    var resetSent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF34ebde).copy(alpha = 0.5f), Color.White)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Title
            Text(
                text = "MediCamp",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Username/Email Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username/Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "User Icon",
                        modifier = Modifier.size(30.dp)
                    )
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (isSignupMode && password.length < 6) {
                        errorMessage = "Password must be at least 6 characters long"
                    } else {
                        errorMessage = null
                    }
                },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon",
                        modifier = Modifier.size(30.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            ),
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Forgot Password link (only for login mode)
            if (!isSignupMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 14.sp,
                        color = Color(0xFF1976D2),
                        modifier = Modifier.clickable { showResetDialog = true })
                }
            }

            // Display error message if any
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login/Signup button
            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        errorMessage = "Username and password cannot be empty"
                        return@Button
                    }
                    if (isSignupMode && password.length < 6) {
                        errorMessage = "Password must be at least 6 characters long"
                        return@Button
                    }
                    if (isSignupMode) {
                        viewModel.signup(username.trim(), password.trim(), onSuccess = {
                            errorMessage = null
                            isSignupMode = false
                        }, onFail = {
                            errorMessage = "Signup failed"
                        })
                    } else {
                        viewModel.login(username.trim(), password.trim(), onSuccess = {
                            errorMessage = null
                            onAuthSuccess()
                        }, onFail = {
                            errorMessage = "Invalid credentials"
                        })
                    }
                },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (isSignupMode) "Sign Up" else "Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Toggle between login and signup modes with animation
            AnimatedContent(targetState = isSignupMode, label = "auth toggle") { signup ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (signup) "Already have an account? " else "Don’t have an account? ",
                        fontSize = 16.sp
                    )
                    Text(
                        text = if (signup) "Login" else "Sign Up",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { isSignupMode = !isSignupMode })
                }
            }
        }

        // Forgot Password Dialog logic
        if (showResetDialog) {
            AlertDialog(onDismissRequest = {
                showResetDialog = false
                resetSent = false
                resetEmail = ""
            }, title = { Text("Reset Password") }, text = {
                if (resetSent) {
                    Text("Reset instructions sent to $resetEmail")
                } else {
                    OutlinedTextField(
                        value = resetEmail,
                        onValueChange = { resetEmail = it },
                        placeholder = { Text("Enter your email or username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }, confirmButton = {
                TextButton(
                    onClick = {
                        if (resetEmail.isNotBlank()) {
                            resetSent = true
                        }
                    }) {
                    Text(if (resetSent) "OK" else "Send")
                }
            }, dismissButton = {
                if (!resetSent) {
                    TextButton(onClick = {
                        showResetDialog = false
                        resetEmail = ""
                    }) {
                        Text("Cancel")
                    }
                }
            })
        }
    }
}
