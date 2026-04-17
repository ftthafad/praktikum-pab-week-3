package com.travelwaka.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.travelwaka.app.ui.navigation.Routes

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Routes.HOME, Icons.Filled.Home),
    BottomNavItem("Explore", Routes.EXPLORE, Icons.Filled.Explore),
    BottomNavItem("Bookmark", Routes.BOOKMARK, Icons.Filled.Bookmark),
    BottomNavItem("Profil", Routes.PROFILE, Icons.Filled.Person)
)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.White,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label, style = MaterialTheme.typography.labelSmall)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = com.travelwaka.app.ui.theme.Primary,
                    selectedTextColor = com.travelwaka.app.ui.theme.Primary,
                    indicatorColor = com.travelwaka.app.ui.theme.PrimaryLight,
                    unselectedIconColor = com.travelwaka.app.ui.theme.TextSecondary,
                    unselectedTextColor = com.travelwaka.app.ui.theme.TextSecondary
                )
            )
        }
    }
}

private val Int.dp: androidx.compose.ui.unit.Dp
    get() = androidx.compose.ui.unit.Dp(this.toFloat())
