package com.practicum.ghiblicollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.practicum.ghiblicollection.ui.MainScreen
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GhibliCollectionTheme {
                MainScreen()
            }
        }
    }
}