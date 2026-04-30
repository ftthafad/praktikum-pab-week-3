package com.travelwaka.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.travelwaka.app.ui.navigation.Home
import com.travelwaka.app.ui.navigation.Explore
import com.travelwaka.app.ui.navigation.Bookmark
import com.travelwaka.app.ui.navigation.Profile

data class BottomNavItem(
    val label: String,
    val route: NavKey,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Home, Icons.Filled.Home),
    BottomNavItem("Explore", Explore, Icons.Filled.Explore),
    BottomNavItem("Bookmark", Bookmark, Icons.Filled.Bookmark),
    BottomNavItem("Profil", Profile, Icons.Filled.Person)
)

@Composable
fun BottomNavBar(
    currentRoute: NavKey?,
    onNavigate: (NavKey) -> Unit
) {
    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.White,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        onNavigate(item.route)
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
