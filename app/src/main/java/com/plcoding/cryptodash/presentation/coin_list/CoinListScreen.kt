package com.plcoding.cryptodash.presentation.coin_list

import androidx.compose.foundation.layout.*
import com.plcoding.cryptodash.presentation.coin_list.components.CoinListItem
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cryptodash.presentation.Screen

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    // Use a Column to stack the coin list and the pagination controls
    Column(modifier = Modifier.fillMaxSize()) {
        // Coin list
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.coins) { coin ->
                    CoinListItem(
                        coin = coin,
                        onItemClick = {
                            navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                        }
                    )
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        // Pagination controls at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.previousPage() },
                enabled = viewModel.page > 0  // Disable if it's the first page
            ) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Page: ${viewModel.page + 1} of ${viewModel.totalPages}")
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { viewModel.nextPage() },
                enabled = viewModel.page < viewModel.totalPages - 1  // Disable if it's the last page
            ) {
                Text("Next")
            }
        }
    }
}
