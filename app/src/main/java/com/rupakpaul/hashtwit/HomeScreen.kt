package com.rupakpaul.hashtwit

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person

@Composable
fun HomeScreen(navController: NavController, hederaAccountId: String, hederaPrivateKey: String) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("HashTwit") })
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Nothing */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = false,
                    onClick = {
                        navController.navigate("search/$hederaAccountId/$hederaPrivateKey")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        navController.navigate("profile/$hederaAccountId/$hederaPrivateKey")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.MailOutline, contentDescription = "Messages") },
                    label = { Text("Messages") },
                    selected = false,
                    onClick = { /* Open Messages */ }
                )
            }
        },
        content = { innerPadding ->
            // Use the innerPadding to respect the Scaffold's padding
            // Add content for the home screen here
            Text("Home Screen Content", modifier = Modifier.padding(innerPadding))
        }
    )
}