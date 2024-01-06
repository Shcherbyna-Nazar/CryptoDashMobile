package com.plcoding.cryptodash.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    var currentPrice: Map<String?, Double?>,
    var ath: Map<String?, Double?>,
    var atl: Map<String?, Double?>,
    @SerializedName("ath_change_percentage")
    var athChangePercentage: Map<String?, Double?>,
    @SerializedName("ath_date")
    var athDate: Map<String?, String?>,
    @SerializedName("atl_date")
    var atlDate: Map<String?, String?>,
    @SerializedName("market_cap")
    var marketCap: Map<String?, Double?>,
    @SerializedName("market_cap_rank")
    var marketCapRank: Int?,
    @SerializedName("fully_diluted_valuation")
    var fullyDilutedValuation: Map<String?, Double?>,
    @SerializedName("market_cap_fdv_ratio")
    var marketCapFdvRatio: Double?,
    @SerializedName("total_volume")
    var totalVolume: Map<String?, Double?>,
    @SerializedName("high_24h")
    var high24h: Map<String?, Double?>,
    @SerializedName("low_24h")
    var low24h: Map<String?, Double?>,
    @SerializedName("price_change_24h")
    var priceChange24h: Double?,
    @SerializedName("price_change_percentage_24h")
    var priceChangePercentage24h: Double?,
    @SerializedName("price_change_percentage_7d")
    var priceChangePercentage7d: Double?,
    @SerializedName("price_change_percentage_14d")
    var priceChangePercentage14d: Double?,
    @SerializedName("price_change_percentage_30d")
    var priceChangePercentage30d: Double?,
    @SerializedName("price_change_percentage_60d")
    var priceChangePercentage60d: Double?,
    @SerializedName("price_change_percentage_200d")
    var priceChangePercentage200d: Double?,
    @SerializedName("price_change_percentage_1y")
    var priceChangePercentage1y: Double?,
    @SerializedName("market_cap_change_24h_in_currency")
    var marketCapChange24hInCurrency: Map<String?, Double?>,
    @SerializedName("market_cap_change_percentage_24h_in_currency")
    var marketCapChangePercentage24hInCurrency: Map<String?, Double?>,
    @SerializedName("total_supply")
    var totalSupply: Long?,
    @SerializedName("max_supply")
    var maxSupply: Long?,
    @SerializedName("circulating_supply")
    var circulatingSupply: Long?,
    @SerializedName("last_updated")
    var lastUpdated: String?
)
