package com.teka.weatherapp.screens.search;

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teka.weatherapp.ui.theme.DarkBlue
import com.teka.weatherapp.ui.theme.poppinsFamily
import com.teka.weatherapp.R
import com.teka.weatherapp.ui.theme.White

@Composable
fun FavCard(
    context: Context,
    navController: NavController,
    degree: String,
    latitude: Double,
    longitude: Double,
    city: String,
    country: String,
    description: String,
    icon: String,
    onClick: () -> Unit = {},
    isItDb: Boolean = false
) {

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnected == true
                if (isConnected) {
                    navController.popBackStack()
//                    navController.navigate(BottomNavItem.Home.route + "/${favourite.city}")
                } else {
                    Toast
                        .makeText(context, "No internet connection!", Toast.LENGTH_LONG)
                        .show()
                }
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(500.dp),
        colors = CardDefaults.cardColors(DarkBlue),
    ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(
                        degree,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = White
                    )
                    Text(
                        description.split(' ')
                            .joinToString(separator = " ") { word -> word.replaceFirstChar { it.uppercase() } },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraLight,
                        fontFamily = poppinsFamily,
                        color = White
                    )
                }


                var image = R.drawable.sun_cloudy
                if (icon == "01d") {
                    image = R.drawable.sunny
                } else if (icon == "02d") {
                    image = R.drawable.sunny
                } else if (icon == "03d" || icon == "04d" || icon == "04n" || icon == "03n" || icon == "02n") {
                    image = R.drawable.cloudy
                } else if (icon == "09d" || icon == "10n" || icon == "09n") {
                    image = R.drawable.rainy
                } else if (icon == "10d") {
                    image = R.drawable.rainy_sunny
                } else if (icon == "11d" || icon == "11n") {
                    image = R.drawable.thunder_lightning
                } else if (icon == "13d" || icon == "13n") {
                    image = R.drawable.snow
                } else if (icon == "50d" || icon == "50n") {
                    image = R.drawable.fog
                } else if (icon == "01n") {
                    image = R.drawable.clear
                } else {
                    R.drawable.cloudy
                }

                Image(
                    painter = painterResource(id = image),
                    contentDescription = stringResource(R.string.weather_icon),
                    modifier = Modifier
                        .scale(
                            1F
                        )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                    Text(city, fontFamily = poppinsFamily, fontWeight = FontWeight.Medium, color = White)
                }
                Column(verticalArrangement = Arrangement.Bottom) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = stringResource(R.string.deleted_favourite),
                        tint = Color(0xFFd68118),
                        modifier = Modifier.clickable {
//                            favouriteViewModel.deleteFavourite(favourite)
                        })
                }
            }
    }
}