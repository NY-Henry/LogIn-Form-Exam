package com.plcoding.typesafecomposenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.typesafecomposenavigation.ui.theme.TypeSafeComposeNavigationTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TypeSafeComposeNavigationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenA
                ) {
                    composable<ScreenA> {
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var passwordVisible by remember { mutableStateOf(false) }
                        var errorMessage by remember { mutableStateOf("") }

                        // Updated background to a solid color 0xFF010101

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF010101))
                                .padding(24.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("LOGIN FORM", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                                Spacer(Modifier.height(32.dp))
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                                )
                                Spacer(Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password") },
                                    singleLine = true,
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Text(
                                                text = if (passwordVisible) "Hide" else "Show",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.White
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                                Spacer(Modifier.height(8.dp))
                                if (errorMessage.isNotEmpty()) {
                                    Text(
                                        errorMessage,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(8.dp))
                                }
                                TextButton(onClick = { /* TODO: navigate to reset */ }) {
                                    Text("Forgot password?", style = MaterialTheme.typography.bodySmall, color = Color.White)
                                }
                                Spacer(Modifier.height(24.dp))
                                Button(
                                    onClick = {
                                        errorMessage = ""
                                        if (email.isBlank() || !isEmailValid(email)) {
                                            errorMessage = "Please enter a valid email."
                                        } else if (password.isBlank()) {
                                            errorMessage = "Password cannot be empty."
                                        } else {
                                            // TODO: perform login
                                            navController.navigate(ScreenB(
                                                email = email,
                                            ))
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0054))
                                ) {
                                    Text("Log In", color = Color.White)
                                }
                                Spacer(Modifier.height(16.dp))
                                Text("Or log in with", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                                Spacer(Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    OutlinedButton(onClick = { /* TODO: login with Google */ }) {
                                        Text("Google", color = Color.White)
                                    }
                                    OutlinedButton(onClick = { /* TODO: login with GitHub */ }) {
                                        Text("GitHub", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                    // Kotlin
                    composable<ScreenB> {
                        val args = it.toRoute<ScreenB>()
                        var isPlaying by remember { mutableStateOf(false) }
                        val context = androidx.compose.ui.platform.LocalContext.current

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF000000))
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                Text(
                                    text = "Hello ${args.email}!",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White
                                )
                                // Dummy files section updated with clickable texts showing toast messages
                                val dummyFiles = listOf("Settings", "Profile", "Debug", "Study", "Chat")
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    dummyFiles.forEachIndexed { index, file ->
                                        Text(
                                            text = file,
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.clickable {
                                                android.widget.Toast.makeText(context, "You clicked $file", android.widget.Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                        if (index != dummyFiles.lastIndex) {
                                            androidx.compose.material3.Divider(color = Color.Gray)
                                        }
                                    }
                                }
                                // Audio player section with toast messages on button clicks
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = {
                                            android.widget.Toast.makeText(context, "You clicked Prev", android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                                    ) {
                                        Text("Prev", color = Color.White)
                                    }
                                    Button(
                                        onClick = {
                                            isPlaying = !isPlaying
                                            val message = if (isPlaying) "You clicked Play" else "You clicked Pause"
                                            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                                    ) {
                                        Text(if (isPlaying) "Pause" else "Play", color = Color.White)
                                    }
                                    Button(
                                        onClick = {
                                            android.widget.Toast.makeText(context, "You clicked Next", android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                                    ) {
                                        Text("Next", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val email: String?
)