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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.typesafecomposenavigation.ui.theme.TypeSafeComposeNavigationTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

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
                    composable<ScreenA> { LoginScreen(navController) }
                    composable<ScreenB> { backStackEntry ->
                        val args = backStackEntry.toRoute<ScreenB>()
                        ScreenBContent(navController, args.email)
                    }
                    composable<ScreenDetail> { backStackEntry ->
                        val args = backStackEntry.toRoute<ScreenDetail>()
                        ScreenDetailContent(args.itemName)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: androidx.navigation.NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
                        navController.navigate(ScreenB(email = email))
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

@Composable
fun ScreenBContent(navController: androidx.navigation.NavHostController, email: String?) {
    var isPlaying by remember { mutableStateOf(false) }
    val dummyFiles = listOf("Settings", "Profile", "Debug", "Study", "Chat")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101820))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Hello ${email.orEmpty()}!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(dummyFiles) { file ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(ScreenDetail(file))
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF23272F)),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Icon(
                                imageVector = when (file) {
                                    "Settings" -> Icons.Default.Settings
                                    "Profile" -> Icons.Default.Person
                                    "Debug" -> Icons.Default.Build
                                    "Study" -> Icons.Default.Place
                                    "Chat" -> Icons.Default.MailOutline
                                    else -> Icons.Default.Info
                                },
                                contentDescription = file,
                                tint = Color(0xFFFF0050),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.width(18.dp))
                            Text(
                                text = file,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        // Removed toast
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                ) {
                    Text("Prev", color = Color.White)
                }
                Button(
                    onClick = {
                        isPlaying = !isPlaying
                        // Removed toast
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                ) {
                    Text(if (isPlaying) "Pause" else "Play", color = Color.White)
                }
                Button(
                    onClick = {
                        // Removed toast
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050))
                ) {
                    Text("Next", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ScreenDetailContent(itemName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101820))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF23272F)),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFFFF0050),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = itemName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "This is the detail screen for $itemName.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
            }
        }
    }
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

