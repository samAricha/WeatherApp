package com.teka.weatherapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teka.weatherapp.screens.home.HomeScreen
import com.teka.weatherapp.screens.home.HomeViewModel
import com.teka.weatherapp.screens.search.SearchCityScreen
import com.teka.weatherapp.screens.search.SearchCityViewModel
import com.teka.weatherapp.utils.component.NavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    homeViewModel: HomeViewModel,
    searchCityViewModel: SearchCityViewModel
) {
    val navController = rememberNavController()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavBar(navController)
        },
    ) {


        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.HomeScreen.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    navController = navController,
                    onNavigateToSearchCityScreen = { navController.navigate(NavScreen.SearchCityScreen.route) }
                )
            }
            composable(NavScreen.SearchCityScreen.route) {
                SearchCityScreen(
                    viewModel = searchCityViewModel,
                    onNavigateToHomeScreen = {
                        navController.navigate(NavScreen.HomeScreen.route) {
                            launchSingleTop = true
                            popUpTo(NavScreen.HomeScreen.route)
                        }
                                             },
                        navController = navController
                )

            }
        }
    }

}