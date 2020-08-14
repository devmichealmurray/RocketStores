package com.devmmurray.rocketstores.data.repo

import com.devmmurray.rocketstores.data.database.StoreDAO
import com.devmmurray.rocketstores.data.model.entity.StoreEntity

class DatabaseRepo(
    private val storeDataSource: StoreDAO
) {

    /**
     *  Access to Database Functions via Repository
     */

    suspend fun addStore(store: StoreEntity) =
        storeDataSource.addStore(store)

    suspend fun getStores() =
        storeDataSource.getStores().map { it.toStoreObject() }

    suspend fun deleteAllStores() =
        storeDataSource.deleteStores()

    suspend fun getStoreById(id: Long) =
        storeDataSource.getStoreById(id)

    suspend fun getStoreByCity(city: String) =
        storeDataSource.getStoreByCity(city).map { it.toStoreObject() }
}