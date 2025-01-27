package com.teka.weatherapp.utils.component;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CachedDataIndicator(isReadingCachedData: Boolean?, lastUpdated: Long?) {
    if (isReadingCachedData == true) {
        val formattedTime = remember(lastUpdated) {
            lastUpdated?.let {
                java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault()).format(it)
            } ?: "Unknown"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.Gray)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(
                text = "Displaying cached data. Last updated: $formattedTime",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
