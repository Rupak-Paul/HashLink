package com.rupakpaul.hashtwit

import android.R
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.rupakpaul.hashtwit.backend.HashTwitAccount
import com.rupakpaul.hashtwit.backend.HederaAccount
import com.rupakpaul.hashtwit.backend.containers.Profile
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, hederaAccountId: String, hederaPrivateKey: String) {
    var profilePicture by remember { mutableStateOf<ByteArray?>(null) }
    var profilePictureUri by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profilePictureUri = uri
        profilePicture = uriToByteArray(context.contentResolver, uri)
    }

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
                    selected = false,
                    onClick = {
                        navController.navigate("search/$hederaAccountId/$hederaPrivateKey")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = true,
                    onClick = { /* Nothing */ }
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                profilePicture?.let {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                } ?:
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_camera),
                        contentDescription = "No Profile Picture",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = about,
                    onValueChange = { about = it },
                    label = { Text("About") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        isLoading = true
                        coroutineScope.launch {
                            val hederaAccount = HederaAccount(hederaAccountId, hederaPrivateKey)
                            var profile = HashTwitAccount.getProfile(hederaAccount)

                            profilePicture = profile.profilePicture
                            name = profile.name
                            about = profile.about
                            dob = profile.dob
                            location = profile.location

                            isLoading = false
                        }
                    }) {
                        Text("Get")
                    }

                    Button(onClick = {
                        isLoading = true
                        coroutineScope.launch {
                            val hederaAccount = HederaAccount(hederaAccountId, hederaPrivateKey)
                            var profile = Profile(profilePicture, name, about, dob, location)

                            HashTwitAccount.update(hederaAccount, profile)
                            Toast.makeText(context, "Account Updated!", Toast.LENGTH_SHORT).show()

                            isLoading = false
                        }
                    }) {
                        Text("Update")
                    }
                }
            }
        }
    )
}