package com.rupakpaul.hashtwit

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rupakpaul.hashtwit.backend.HashTwitAccount
import com.rupakpaul.hashtwit.backend.HederaAccount
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var accountId by remember { mutableStateOf("") }
    var privateKey by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HashLink",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = accountId,
            onValueChange = { accountId = it },
            label = { Text("Hedera Account Id") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = privateKey,
            onValueChange = { privateKey = it },
            label = { Text("Hedera Private Key") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            isLoading = true

            coroutineScope.launch {
                if (!HederaAccount.valid(accountId, privateKey)) {
                    errorMessage = "Invalid Hedera Account Id or Private Key"
                } else if (!HashTwitAccount.exist(HederaAccount(accountId, privateKey))) {
                    Toast.makeText(context, "HashTwit does not exist. Create a new HashTwit Account.", Toast.LENGTH_LONG).show()
                    navController.navigate("createAccount/$accountId/$privateKey")
                } else {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                    navController.navigate("home/$accountId/$privateKey")
                }
                isLoading = false
            }
        }) {
            Text("Login")
        }

        // Show a loading indicator if the API calls are in progress
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}