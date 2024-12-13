package com.rupakpaul.hashtwit

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("createAccount/{hederaAccountId}/{hederaPrivateKey}") { backStackEntry ->
            val hederaAccountId = backStackEntry.arguments?.getString("hederaAccountId") ?: ""
            val hederaPrivateKey = backStackEntry.arguments?.getString("hederaPrivateKey") ?: ""
            CreateAccountScreen(navController, hederaAccountId, hederaPrivateKey)
        }
        composable("home/{hederaAccountId}/{hederaPrivateKey}") { backStackEntry ->
            val hederaAccountId = backStackEntry.arguments?.getString("hederaAccountId") ?: ""
            val hederaPrivateKey = backStackEntry.arguments?.getString("hederaPrivateKey") ?: ""
            HomeScreen(navController, hederaAccountId, hederaPrivateKey)
        }
        composable("search/{hederaAccountId}/{hederaPrivateKey}") { backStackEntry ->
            val hederaAccountId = backStackEntry.arguments?.getString("hederaAccountId") ?: ""
            val hederaPrivateKey = backStackEntry.arguments?.getString("hederaPrivateKey") ?: ""
            SearchScreen(navController, hederaAccountId, hederaPrivateKey)
        }
        composable("profile/{hederaAccountId}/{hederaPrivateKey}") { backStackEntry ->
            val hederaAccountId = backStackEntry.arguments?.getString("hederaAccountId") ?: ""
            val hederaPrivateKey = backStackEntry.arguments?.getString("hederaPrivateKey") ?: ""
            ProfileScreen(navController, hederaAccountId, hederaPrivateKey)
        }
    }
}