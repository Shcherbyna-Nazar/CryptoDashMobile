package com.plcoding.cryptodash.presentation.coin_detail

import com.plcoding.cryptodash.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
