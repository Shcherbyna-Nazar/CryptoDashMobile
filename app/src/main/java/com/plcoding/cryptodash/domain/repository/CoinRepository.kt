package com.plcoding.cryptodash.domain.repository

import com.plcoding.cryptodash.data.remote.dto.CoinDetailDto
import com.plcoding.cryptodash.data.remote.dto.Page

interface CoinRepository {

    suspend fun getCoins(page: Int, size: Int): Page

    suspend fun getCoinById(coinId: String): CoinDetailDto
}