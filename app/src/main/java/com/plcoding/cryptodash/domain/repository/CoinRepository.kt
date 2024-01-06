package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.Page

interface CoinRepository {

    suspend fun getCoins(page: Int, size: Int): Page

    suspend fun getCoinById(coinId: String): CoinDetailDto
}