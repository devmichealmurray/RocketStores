package com.devmmurray.rocketstores.data.repo

import com.devmmurray.rocketstores.data.api.RocketApiService
import com.devmmurray.rocketstores.data.model.dto.StoreDTO
import retrofit2.Response

object RocketApiRepo {

    suspend fun getRocketStores(): Response<StoreDTO> {
        return RocketApiService.apiClient.getRocketStoreJson()
    }
}