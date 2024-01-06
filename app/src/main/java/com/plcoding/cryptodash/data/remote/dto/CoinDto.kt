package com.plcoding.cryptodash.data.remote.dto


import com.plcoding.cryptodash.domain.model.Coin

data class CoinDto(
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Double,
    val current_price: Double,
    val fully_diluted_valuation: Long,
    val high_24h: Double,
    val id: String,
    val image: String,
    val lastUpdated: String,
    val low_24h: Double,
    val market_cap: Long,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Int,
    val max_supply: Double,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val symbol: String,
    val total_supply: Double,
    val total_volume: Long
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        image = image,
        name = name,
        rank = market_cap_rank,
        symbol = symbol,
        change24h = price_change_percentage_24h
    )
}