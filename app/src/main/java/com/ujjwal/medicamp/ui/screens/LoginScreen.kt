package com.ujjwal.medicamp.ui.screens

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

@Composable
fun LoginScreen(
    viewModel: EventViewModel,
    onAuthSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isSignupMode by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFC7F2FF), Color(0xFF4DBFE9))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MediCamp",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username/Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "User Icon"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible)
                                    R.drawable.ic_visibility
                                else
                                    R.drawable.ic_visibility_off
                            ),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )

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
                        modifier = Modifier.clickable { onForgotPasswordClick() }
                    )
                }
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        errorMessage = "Username and password cannot be empty"
                        return@Button
                    }
                    if (isSignupMode) {
                        viewModel.signup(username, password,
                            onSuccess = {
                                errorMessage = null
                                onAuthSuccess()
                            },
                            onFail = {
                                errorMessage = "Signup failed"
                            }
                        )
                    } else {
                        viewModel.login(username, password,
                            onSuccess = {
                                errorMessage = null
                                onAuthSuccess()
                            },
                            onFail = {
                                errorMessage = "Invalid credentials"
                            }
                        )
                    }
                },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = if (isSignupMode) "Sign Up" else "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = if (isSignupMode) "Already have an account? " else "Don’t have an account? "
                )
                Text(
                    text = if (isSignupMode) "Login" else "Sign Up",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { isSignupMode = !isSignupMode }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("  OR  ", fontWeight = FontWeight.SemiBold, color = Color.Gray)
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
        }
    }
}
