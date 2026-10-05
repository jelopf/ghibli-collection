package com.practicum.ghiblicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            FilmListScreen(
                onFilmClick = { filmId ->
                    navController.navigate(Destinations.detailRoute(filmId))
                }
            )
        }

        composable(Destinations.HOME) {
            HomeScreen()
        }

        composable(
            route = Destinations.DETAIL,
            arguments = listOf(
                navArgument("filmId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getString("filmId").orEmpty()
            FilmDetailScreen(filmId = filmId)
        }
    }
}