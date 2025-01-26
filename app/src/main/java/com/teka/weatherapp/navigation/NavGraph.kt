package com.teka.weatherapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teka.weatherapp.screens.home.HomeScreen
import com.teka.weatherapp.screens.home.HomeViewModel
import com.teka.weatherapp.screens.search.SearchCityScreen
import com.teka.weatherapp.screens.search.SearchCityViewModel

@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    homeViewModel: HomeViewModel,
    searchCityViewModel: SearchCityViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.HomeScreen.route) {
                HomeScreen(homeViewModel) { navController.navigate(NavScreen.SearchCityScreen.route) }
            }
            composable(NavScreen.SearchCityScreen.route) {
                SearchCityScreen(searchCityViewModel) {
                    navController.navigate(NavScreen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(NavScreen.HomeScreen.route)
                    }
                }
            }
        }
    }
}