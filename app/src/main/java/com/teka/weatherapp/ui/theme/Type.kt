package com.teka.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.teka.weatherapp.R


val fonts = FontFamily(
    Font(R.font.sourcesanspro_extralight, FontWeight.Light),
    Font(R.font.sourcesansoro_semibold, FontWeight.SemiBold),
    Font(R.font.sourcesanspro_regular, FontWeight.Normal)
)


private val defaultTypography = Typography()


val customTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = fonts),
    displayMedium =defaultTypography.displayMedium.copy(fontFamily = fonts),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = fonts),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fonts),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fonts),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fonts),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = fonts),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = fonts),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = fonts),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fonts),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fonts),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = fonts),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = fonts),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = fonts),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = fonts)
)