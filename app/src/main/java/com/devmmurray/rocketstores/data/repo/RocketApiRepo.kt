package com.devmmurray.rocketstores.data.repo

import com.devmmurray.rocketstores.data.api.RocketApiService
import com.devmmurray.rocketstores.data.model.dto.StoreDTO
import retrofit2.Response

object RocketApiRepo {

    /**
     *  Access to the network call accessible only through the repo
     */

    suspend fun getRocketStores(): Response<StoreDTO> {
        return RocketApiService.apiClient.getRocketStoreJson()
    }

}