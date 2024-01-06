package com.plcoding.cryptodash.di

import com.plcoding.cryptodash.common.Constants
import com.plcoding.cryptodash.data.remote.CryptoDashApi
import com.plcoding.cryptodash.data.repository.CoinRepositoryImpl
import com.plcoding.cryptodash.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): CryptoDashApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoDashApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CryptoDashApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}