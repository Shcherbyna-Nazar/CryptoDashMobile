package com.plcoding.cryptocurrencyappyt.data.remote.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class CoinDetailDto(
    val id: String,
    val symbol: String,
    val name: String,
    val assetPlatformId: String?,
    val platforms: Map<String, String>,
    val hashingAlgorithm: String?,
    val categories: List<String>,
    val description: Map<String, String>,
    val links: List<LinkDto>, // Adjusted to match the given JSON structure
    val image: Map<String, String>,
    val countryOrigin: String?,
    val genesisDate: String?,
    val sentimentVotesUpPercentage: Double,
    val sentimentVotesDownPercentage: Double,
    val marketCapRank: Int,
    val coingeckoRank: Int?,
    val coingeckoScore: Double?,
    val developerScore: Double?,
    val communityScore: Double?,
    val liquidityScore: Double?,
    val publicInterestScore: Double?,
    val marketData: String?,
    val lastUpdated: String
)

fun CoinDetailDto.toCoinDetail(): CoinDetail {

    val linksMap = mutableMapOf<String?, Any?>()
    this.links.forEach { link ->
        linksMap[link.linkType] = link.linkValue
    }
    val gson = Gson()
    val marketDataObj = marketData?.let { gson.fromJson(it, MarketData::class.java) }

    val lastUpdatedDateTime = ZonedDateTime.parse(marketDataObj?.lastUpdated)

    // Format the LocalDateTime object into a more readable string
    // Adjust the pattern as needed to match your desired format
    val formattedLastUpdated =
        lastUpdatedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    marketDataObj?.let {
        it.lastUpdated = formattedLastUpdated
    }



    return CoinDetail(
        id = this.id,
        name = this.name,
        description = this.description,
        symbol = this.symbol,
        image = this.image,
        sentimentVotesUpPercentage = this.sentimentVotesUpPercentage,
        sentimentVotesDownPercentage = this.sentimentVotesDownPercentage,
        categories = this.categories,
        developerScore = this.developerScore ?: 0.0, // default to 0.0 if null
        communityScore = this.communityScore ?: 0.0, // default to 0.0 if null
        links = linksMap,
        marketData = marketDataObj,
        rank = marketCapRank,
        lastUpdated = lastUpdated
    )
}

data class LinkDto(
    val id: Int,
    @SerializedName("linkType")
    val linkType: String,
    @SerializedName("linkValue")
    val linkValue: String
)

