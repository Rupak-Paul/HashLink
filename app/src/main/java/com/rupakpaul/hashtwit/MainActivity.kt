package com.rupakpaul.hashtwit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rupakpaul.hashtwit.ui.theme.HashTwitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HashTwitTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation() // This is the root navigation for the app
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HashTwitTheme {
        AppNavigation() // Preview the UI navigation
    }
}