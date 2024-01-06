package com.plcoding.cryptocurrencyappyt.data.remote

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.Page
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoDashApi {

    @GET("api/v1/crypto/all")
    suspend fun getCoins(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 15,
        @Query("sortBy") sortBy: String = "id"
    ): Page

    @GET("api/v1/crypto/details/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto
}