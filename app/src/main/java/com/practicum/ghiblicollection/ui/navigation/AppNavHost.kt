package com.practicum.ghiblicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.ghiblicollection.ui.detail.FilmDetailScreen
import com.practicum.ghiblicollection.ui.home.HomeScreen
import com.practicum.ghiblicollection.ui.list.FilmListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.FILMS,
        modifier = modifier
    ) {
        composable(Destinations.FILMS) {
            FilmListScreen()
        }

        composable(Destinations.HOME) {
            HomeScreen()
        }

        composable(Destinations.DETAIL) {
            FilmDetailScreen()
        }
    }
}