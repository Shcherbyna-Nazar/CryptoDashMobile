package com.plcoding.cryptodash.presentation.coin_list

import com.plcoding.cryptodash.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
