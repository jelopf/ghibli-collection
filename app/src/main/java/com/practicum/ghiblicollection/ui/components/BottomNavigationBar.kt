package com.practicum.ghiblicollection.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.practicum.ghiblicollection.ui.navigation.Destinations
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

private val bottomNavItems = listOf(
    BottomNavItem(
        route = Destinations.FILMS,
        label = "Фильмы",
        icon = { Icon(Icons.Default.Menu, contentDescription = null) }
    ),
    BottomNavItem(
        route = Destinations.HOME,
        label = "Главная",
        icon = { Icon(Icons.Default.Face, contentDescription = null) }
    )
)

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        ) {
        bottomNavItems.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                icon = item.icon,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    GhibliCollectionTheme {
        BottomNavigationBar(
            currentDestination = null,
            onItemClick = {}
        )
    }
}