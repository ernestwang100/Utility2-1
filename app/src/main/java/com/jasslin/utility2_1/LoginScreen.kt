package com.jasslin.utility2_1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClicked: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggedIn by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Example list of registered users
    val registeredUsers = listOf(
        RegisteredUser("user1", "password1"),
        RegisteredUser("user2", "password2"),
        // Add more users as needed
    )

    if (isLoggedIn) {
        OptionsDisplay(optionList = listOf("Option 1", "Option 2", "Option 3"))
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") }
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {
                        var snackbarText = ""
                        // Check if the entered credentials match any registered user
                        if (isUserRegistered(username, password, registeredUsers)) {
                            snackbarText = "Welcome $username!"
                            scope.launch {
                                snackbarHostState.showSnackbar(snackbarText)
                                // Delay execution of onLoginClicked to allow Snackbar to be shown
                                delay(2000) // Adjust the delay time as needed
                                onLoginClicked(username, password)
                                isLoggedIn = true
                            }
                        } else {
                            // Handle invalid login
                            // You might want to show an error message or take appropriate action
                            // For simplicity, print a message to the console
                            snackbarText = "Invalid login credentials"
                            println("Invalid login credentials")
                            scope.launch {
                                snackbarHostState.showSnackbar(snackbarText)
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Login")
                }
            }
        }
    }
}

data class RegisteredUser(val username: String, val password: String)

fun isUserRegistered(username: String, password: String, registeredUsers: List<RegisteredUser>): Boolean {
    // Check if the provided username and password match any registered user
    return registeredUsers.any { it.username == username && it.password == password }
}
