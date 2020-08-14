package com.devmmurray.rocketstores.data.api

import com.devmmurray.rocketstores.data.model.dto.StoreDTO
import retrofit2.Response
import retrofit2.http.GET

interface RocketApi {

    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    suspend fun getRocketStoreJson(): Response<StoreDTO>

}