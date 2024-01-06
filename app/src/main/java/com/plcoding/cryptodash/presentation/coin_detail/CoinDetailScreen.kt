package com.plcoding.cryptodash.presentation.coin_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.plcoding.cryptodash.domain.model.CoinDetail
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CoinDetailScreen(viewModel: CoinDetailViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val context = LocalContext.current

    MaterialTheme(colors = darkThemeColors) {
        Box(modifier = Modifier.fillMaxSize()) {
            state.coin?.let { coin ->
                CoinDetailContent(coin, context)
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun CoinDetailContent(coin: CoinDetail, context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueTheme.background)
    ) {
        item { CoinImageHeader(coin) }
        item { CoinMetaData(coin) }
        item { CoinDescription(coin) }
        item { MarketDataSection(coin = coin) }
        item { LinksSection(coin, context) }
    }
}

@Composable
fun CoinImageHeader(coin: CoinDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkBlueTheme.primaryVariant,
                        DarkBlueTheme.secondaryVariant
                    )
                )
            )
    ) {
        coin.image["large"]?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "${coin.name} logo",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CoinMetaData(coin: CoinDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${coin.name} (${coin.symbol.uppercase()})",
                style = MaterialTheme.typography.h6.copy(
                    color = DarkBlueTheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Market Cap Rank: ${coin.rank}",
                style = MaterialTheme.typography.subtitle1.copy(color = DarkBlueTheme.onSurface)
            )
            // Add other metadata as needed
        }
        coin.marketData?.priceChangePercentage24h?.let {
            PriceChangeIndicator(it)
        }
    }
}

@Composable
fun PriceChangeIndicator(priceChangePercentage24h: Double) {
    val color = if (priceChangePercentage24h > 0) Color.Green else Color.Red
    val sign = if (priceChangePercentage24h > 0) "+" else ""
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .background(color = color, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$sign${String.format("%.2f", priceChangePercentage24h)}%",
            style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
        )
    }
}

@Composable
fun CoinDescription(coin: CoinDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = DarkBlueTheme.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.h6.copy(
                    color = DarkBlueTheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // WebView to render HTML content with larger text
            val context = LocalContext.current
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String?
                            ): Boolean {
                                // Open links in the user's browser instead of this WebView
                                url?.let { webUrl ->
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                                    context.startActivity(intent)
                                }
                                return true // Indicate that we've handled the URL
                            }
                        }
                        settings.apply {
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            builtInZoomControls = false // Disable zoom controls for a cleaner look
                            displayZoomControls = false
                            textZoom =
                                150 // Significantly increase text size for better readability
                        }
                        val customHtml = """
                            <html>
                            <head>
                            <style type="text/css">
                                body {
                                    color: #ECEFF1; /* Light text color for readability */
                                    background-color: #263238; /* Dark background for contrast */
                                    font-family: 'Roboto', sans-serif; /* Modern font */
                                    font-size: 18px; /* Larger font size */
                                    line-height: 1.6; /* Increase line height for readability */
                                    padding: 10px; /* Add some padding around the text */
                                }
                                a {
                                    color: #FFAB40; /* Accent color for links */
                                    text-decoration: none; /* No underline on links */
                                }
                                a:hover {
                                    text-decoration: underline; /* Underline on hover for links */
                                }
                            </style>
                            </head>
                            <body>${coin.description["en"] ?: "No description available."}</body>
                            </html>
                        """.trimIndent()
                        loadDataWithBaseURL(null, customHtml, "text/html", "UTF-8", null)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Adjusted height for larger text and more content visibility
            )
        }
    }
}


@Composable
fun LinksSection(coin: CoinDetail, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Explore More",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlueTheme.onSurface
        )
        Spacer(modifier = Modifier.height(10.dp))

        coin.links.forEach { (type, link) ->
            var newLink = link
            if (newLink == "") newLink = "Unavailable"
            val isLinkValid = isValidUrl(newLink)
            val buttonText = if (isLinkValid) {
                type?.replace('_', ' ')?.uppercase()  // Use the original link name
            } else {
                "${type?.replace('_', ' ')?.uppercase()} -> (${newLink})"
            }
            val textColor =
                if (isLinkValid) Color.White else Color.Gray  // Change color based on link validity

            Button(
                onClick = {
                    if (isLinkValid) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link as String))
                        context.startActivity(intent)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlueTheme.primaryVariant)
            ) {
                buttonText?.let {
                    Text(
                        text = it,
                        color = textColor  // Apply the color based on the link's validity
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun isValidUrl(url: Any?): Boolean {
    return when {
        url is String && (url.startsWith("http://", true) || url.startsWith(
            "https://",
            true
        )) -> true
        else -> false
    }
}


@Composable
fun MarketDataSection(coin: CoinDetail) {
    coin.marketData?.let { marketData ->
        Card(
            backgroundColor = DarkBlueTheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Market Data",
                    style = MaterialTheme.typography.h6.copy(
                        color = DarkBlueTheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                MarketDataRow("Current Price", marketData.currentPrice["usd"])
                MarketDataRow("24h High", marketData.high24h["usd"])
                MarketDataRow("24h Low", marketData.low24h["usd"])
                MarketDataRow("Market Cap", marketData.marketCap["usd"])
                MarketDataRow("Total Volume", marketData.totalVolume["usd"])
                MarketDataRow("Updated", marketData.lastUpdated)
                // Add more rows for other market data as needed
            }
        }
    }
}

@Composable
fun MarketDataRow(label: String, value: Any?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.subtitle1.copy(color = DarkBlueTheme.onPrimary)
        )
        Text(
            text = formatValue(value),
            style = MaterialTheme.typography.subtitle1.copy(color = DarkBlueTheme.secondary)
        )
    }
}

fun formatValue(value: Any?): String {
    return when (value) {
        is Double -> String.format("%.2f USD", value)
        is Int -> value.toString()
        is String? -> formatDateString(value)
        else -> "N/A"
    }
}

fun formatDateString(dateString: String?): String {
    return try {
        // Assuming the dateString is in ISO ZonedDateTime format
        val zonedDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ZonedDateTime.parse(dateString)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val zoneId = ZoneId.systemDefault()  // Get the current system's default time zone
        val formattedDate = zonedDateTime.withZoneSameInstant(zoneId)
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))
        formattedDate
    } catch (e: Exception) {
        "Invalid date"
    }
}


// Define your theme colors and styles here
object DarkBlueTheme {
    val primary = Color(0xFF263238)
    val primaryVariant = Color(0xFF1C262A)
    val secondary = Color(0xFF66BB6A)
    val secondaryVariant = Color(0xFF43A047)
    val background = Color(0xFF121212)
    val surface = Color(0xFF1E272C)
    val onPrimary = Color.White
    val onSurface = Color.White
}

val darkThemeColors = darkColors(
    primary = DarkBlueTheme.primary,
    primaryVariant = DarkBlueTheme.primaryVariant,
    secondary = DarkBlueTheme.secondary,
    background = DarkBlueTheme.background,
    surface = DarkBlueTheme.surface,
    onPrimary = DarkBlueTheme.onPrimary,
    onSurface = DarkBlueTheme.onSurface
)
