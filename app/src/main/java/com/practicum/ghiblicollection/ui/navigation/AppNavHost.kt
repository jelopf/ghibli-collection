package com.practicum.ghiblicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practicum.ghiblicollection.ui.detail.FilmDetailScreen
import com.practicum.ghiblicollection.ui.filters.FilterScreen
import com.practicum.ghiblicollection.ui.favorites.FavoritesScreen
import com.practicum.ghiblicollection.ui.list.FilmListScreen
import com.practicum.ghiblicollection.ui.profile.ProfileScreen
import com.practicum.ghiblicollection.ui.profileedit.ProfileEditScreen

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
                },
                onFilterClick = {
                    navController.navigate(Destinations.FILTERS)
                }
            )
        }

        composable(Destinations.FAVORITES) {
            FavoritesScreen(
                onFilmClick = { filmId ->
                    navController.navigate(Destinations.detailRoute(filmId))
                }
            )
        }

        composable(
            route = Destinations.DETAIL,
            arguments = listOf(
                navArgument("filmId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getString("filmId").orEmpty()
            FilmDetailScreen(
                filmId = filmId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Destinations.FILTERS) {
            FilterScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable(Destinations.PROFILE) {
            ProfileScreen(
                onEditClick = {
                    navController.navigate(Destinations.PROFILE_EDIT)
                }
            )
        }

        composable(Destinations.PROFILE_EDIT) {
            ProfileEditScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}