package com.plcoding.cryptodash.domain.use_case.get_coins

import android.util.Log
import com.plcoding.cryptodash.common.Resource
import com.plcoding.cryptodash.data.remote.dto.Page
import com.plcoding.cryptodash.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(page: Int, size: Int): Flow<Resource<Page>> = flow {
        try {
            emit(Resource.Loading<Page>())
            val page = repository.getCoins(page, size)
            emit(Resource.Success<Page>(page))
        } catch (e: HttpException) {
            emit(Resource.Error<Page>(e.localizedMessage ?: "An unexpected error occured"))
            Log.e("GetCoins", e.response().toString())
        } catch (e: IOException) {
            Log.e("GetCoins", e.printStackTrace().toString())
            emit(Resource.Error<Page>("Couldn't reach server. Check your internet connection."))
        }
    }
}