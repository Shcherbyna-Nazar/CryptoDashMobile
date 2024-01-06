package com.plcoding.cryptodash.data.repository

import com.plcoding.cryptodash.data.remote.CryptoDashApi
import com.plcoding.cryptodash.data.remote.dto.CoinDetailDto
import com.plcoding.cryptodash.data.remote.dto.Page
import com.plcoding.cryptodash.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CryptoDashApi
) : CoinRepository {

    override suspend fun getCoins(page: Int, size: Int): Page {
        return api.getCoins(page, size)
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}