package com.plcoding.cryptocurrencyappyt.domain.model

import com.plcoding.cryptocurrencyappyt.data.remote.dto.MarketData

data class CoinDetail(
    val id: String,
    val name: String,
    val description: Map<String, String>,
    val symbol: String,
    val image: Map<String, String>,
    val sentimentVotesUpPercentage: Double,
    val sentimentVotesDownPercentage: Double,
    val categories: List<String>,
    val developerScore: Double,
    val communityScore: Double,
    val links: Map<String?, Any?>,
    val marketData: MarketData?,
    val rank: Int,
    val lastUpdated: String?

)
