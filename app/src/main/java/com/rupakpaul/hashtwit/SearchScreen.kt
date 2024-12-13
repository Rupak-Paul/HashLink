package com.rupakpaul.hashtwit

import android.R
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hedera.hashgraph.sdk.AccountId
import com.rupakpaul.hashtwit.backend.HashTwitAccount
import com.rupakpaul.hashtwit.backend.HederaAccount
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController, hederaAccountId: String, hederaPrivateKey: String) {
    var profilePicture by remember { mutableStateOf<ByteArray?>(null) }
    var name by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("HashTwit") })
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("home/$hederaAccountId/$hederaPrivateKey") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = true,
                    onClick = { /* Do nothing */ }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search...") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        maxLines = 1,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = androidx.compose.ui.graphics.Color.White,
                            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Blue,
                            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.LightGray,
                            textColor = androidx.compose.ui.graphics.Color.Black
                        )
                    )

                    Button(
                        onClick = {
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    val hederaAccount = HederaAccount(hederaAccountId, hederaPrivateKey)
                                    var profile = HashTwitAccount.getProfile(
                                        hederaAccount,
                                        AccountId.fromString(query)
                                    )

                                    profilePicture = profile.profilePicture
                                    name = profile.name
                                    about = profile.about
                                    dob = profile.dob
                                    location = profile.location
                                } catch (e: Exception) {
                                    profilePicture = null
                                    name = ""
                                    about = ""
                                    dob = ""
                                    location = ""

                                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                                isLoading = false
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary), // Use primary color from theme
                        content = {
                            Text("Search", color = androidx.compose.ui.graphics.Color.White) // Text color
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp)) // Increase the spacing below the search bar

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally), // Center align the Box
                    contentAlignment = Alignment.Center
                ) {
                    profilePicture?.let {
                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } ?: Image(
                        painter = painterResource(id = R.drawable.ic_menu_camera),
                        contentDescription = "No Profile Picture",
                        modifier = Modifier.size(50.dp)
                    )
                }

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = about,
                    onValueChange = { about = it },
                    label = { Text("About") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text("Date of Birth") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}