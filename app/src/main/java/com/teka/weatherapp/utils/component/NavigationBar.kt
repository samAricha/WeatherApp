package com.teka.weatherapp.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.teka.weatherapp.navigation.Screen
import kotlin.collections.forEach
import com.teka.weatherapp.R
import com.teka.weatherapp.navigation.NavScreen
import kotlin.let


sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = NavScreen.HomeScreen.route,
        icon = Icons.Outlined.Home
    )

    object Search : BottomNavItem(
        route = NavScreen.SearchCityScreen.route,
        icon = Icons.Outlined.Search
    )

    object Forecast : BottomNavItem(
        route = Screen.Forecast.route,
        icon = Icons.Outlined.Analytics
    )

    object Settings : BottomNavItem(
        route = Screen.Settings.route,
        icon = Icons.Outlined.Settings
    )
}

@Composable
fun NavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Forecast,
        BottomNavItem.Settings
    )
    val defaultCity = "default"
    NavigationBar (
        modifier = Modifier
//            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clip(
                RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)
            ),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = Color.White
    ) {


        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        items.forEach { item ->
            var color = Color.White.copy(alpha = 0.4f)

            NavigationBarItem(
                icon = {
                    if (currentRoute == item.route) {
                        color = Color(0xFFd68118)
                    } else if (item == BottomNavItem.Home) {
                        if (currentRoute !=BottomNavItem.Search.route &&
                            currentRoute != BottomNavItem.Forecast.route &&
                            currentRoute != BottomNavItem.Settings.route
                        ) {
                            color = Color(0xFFd68118)
                        }
                    }
                    Icon(item.icon, contentDescription = null, tint = color)
                },
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(selectedIconColor =  Color.Transparent),
                onClick = {
                    if (item != BottomNavItem.Home) {
                        if (item == BottomNavItem.Settings) {
                            /*TODO(/*Not yet Implemented*/)*/
                        } else {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // Avoid multiple copies of the same destination when re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    } else {
                        navController.navigate(item.route + "/$defaultCity") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}