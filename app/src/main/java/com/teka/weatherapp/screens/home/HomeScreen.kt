package com.teka.weatherapp.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.composeweatherapp.core.helpers.EpochConverter
import com.teka.weatherapp.R
import com.teka.weatherapp.domain.model.Forecast
import com.teka.weatherapp.ui.theme.poppinsFamily
import com.teka.weatherapp.utils.AppStrings
import com.teka.weatherapp.utils.ErrorCardConsts
import com.teka.weatherapp.utils.ExceptionTitles
import com.teka.weatherapp.utils.component.CircularProgressBar
import com.teka.weatherapp.utils.component.CurrentWeatherDetailRow
import com.teka.weatherapp.utils.component.ErrorCard
import com.teka.weatherapp.utils.component.ForecastLazyRow
import com.teka.weatherapp.utils.component.ForecastTitle
import com.teka.weatherapp.utils.component.NavBar
import com.teka.weatherapp.utils.helpers.SetError
import kotlin.text.clear

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter",
    "ContextCastToActivity"
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSearchCityScreen: () -> Unit,
    navController: NavController
) {
    val homeCurrentWeatherState by viewModel.homeForecastState.collectAsState()
    val activity = (LocalContext.current as? Activity)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        BackgroundImage()
//            MenuIcon { onNavigateToSearchCityScreen() }
        WeatherSection(homeCurrentWeatherState) { activity?.finish() }
    }

}

@Composable
private fun BackgroundImage() {
    val gradientColors = listOf(Color(0xFF060620), MaterialTheme.colorScheme.primary)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    )
}

@Composable
private fun WeatherSection(currentWeatherState: HomeForecastState, errorCardOnClick: () -> Unit) {
    when (currentWeatherState) {
        is HomeForecastState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }
        is HomeForecastState.Success -> {
            if (currentWeatherState.forecast != null) {
                CurrentWeatherSection(currentWeatherState.forecast)
                Spacer(modifier = Modifier.height(100.dp))
//                DetailsSection(currentWeatherState.forecast)
            }
        }
        is HomeForecastState.Error -> {
            ErrorCard(
                modifier = Modifier.fillMaxSize(),
                errorTitle = currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR,
                errorDescription = SetError.setErrorCard(
                    currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR
                ),
                errorButtonText = ErrorCardConsts.BUTTON_TEXT,
                errorCardOnClick,
                cardModifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
                    .padding(horizontal = 64.dp)
            )
        }
    }
}

@Composable
private fun CurrentWeatherSection(todayWeather: Forecast) {
    val icon = todayWeather.weatherList[0].weatherStatus[0].icon
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.LocationOn,
                contentDescription = stringResource(R.string.location_icon),
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                todayWeather.cityDtoData.cityName,
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier.background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary,
                        Color.Transparent,
                    ), tileMode = TileMode.Clamp
                ),
                alpha = 0.7F
            )
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "WeatherIcon",
                modifier = Modifier
                    .size(150.dp)
                    .scale(0.8F)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFamily
                        )
                    ) {
                        append("${todayWeather.weatherList[0].weatherData.temp.toInt()}")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFd68118),
                            fontSize = 45.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFamily
                        )
                    ) {
                        append("°")
                    }
                })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                todayWeather.weatherList[0].weatherStatus[0].description.split(' ')
                    .joinToString(separator = " ") { word -> word.replaceFirstChar { it.uppercase() } },
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = poppinsFamily,
                color = Color.Gray
            )
        }


        Text(
            text = "H:${todayWeather.cityDtoData.coordinate.longitude}°  L:${todayWeather.cityDtoData.coordinate.latitude}°",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pressure),
                    contentDescription = "PressureIcon",
                    modifier = Modifier
                        .scale(
                            1F
                        )
                        .size(80.dp)
                )
                Text(
                    todayWeather.weatherList[0].weatherData.pressure.toString() + "hPa",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Pressure",
                    fontSize = 12.sp,
                    fontFamily = poppinsFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.wind),
                    contentDescription = "WindIcon",
                    modifier = Modifier
                        .scale(
                            1F
                        )
                        .size(80.dp)

                )
                Text(
                    todayWeather.weatherList[0].wind.speed.toString() + "m/s",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Wind", fontSize = 12.sp, fontFamily = poppinsFamily, color = MaterialTheme.colorScheme.onPrimary)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.humidity),
                    contentDescription = "HumidityIcon",
                    modifier = Modifier
                        .scale(
                            1F
                        )
                        .size(80.dp)
                )
                Text(
                    todayWeather.weatherList[0].weatherData.humidity.toString() + "%",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Humidity", fontSize = 12.sp, fontFamily = poppinsFamily, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
private fun DetailsSection(forecast: Forecast) {
    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 2),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ForecastSection(forecast)
                WeatherDetailSection(forecast)
            }
        }
    }
}

@Composable
private fun ForecastSection(forecastData: Forecast) {
    ForecastTitle(text = AppStrings.hourly_forecast)
    ForecastLazyRow(forecasts = forecastData.weatherList.take(8))
    ForecastTitle(text = AppStrings.daily_forecast)
    ForecastLazyRow(forecasts = forecastData.weatherList.takeLast(32))
}

@Composable
private fun WeatherDetailSection(currentWeather: Forecast) {
    CurrentWeatherDetailRow(
        title1 = AppStrings.temp,
        value1 = "${currentWeather.weatherList[0].weatherData.temp}${AppStrings.degree}",
        title2 = AppStrings.feels_like,
        value2 = "${currentWeather.weatherList[0].weatherData.feelsLike}${AppStrings.degree}"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.cloudiness,
        value1 = "${currentWeather.weatherList[0].cloudiness.cloudiness}%",
        title2 = AppStrings.humidity,
        value2 = "${currentWeather.weatherList[0].weatherData.humidity}%"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.sunrise,
        value1 = "${EpochConverter.readTimestamp(currentWeather.cityDtoData.sunrise)}AM",
        title2 = AppStrings.sunset,
        value2 = "${EpochConverter.readTimestamp(currentWeather.cityDtoData.sunset)}PM"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.wind,
        value1 = "${currentWeather.weatherList[0].wind.speed}${AppStrings.metric}",
        title2 = AppStrings.pressure,
        value2 = "${currentWeather.weatherList[0].weatherData.pressure}"
    )
}

@Composable
private fun MenuIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 24.dp, end = 24.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}