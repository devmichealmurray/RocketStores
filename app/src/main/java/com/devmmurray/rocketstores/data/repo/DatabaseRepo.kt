package com.devmmurray.rocketstores.data.repo

import com.devmmurray.rocketstores.data.database.StoreDAO
import com.devmmurray.rocketstores.data.model.entity.StoreEntity

class DatabaseRepo(
    private val storeDataSource: StoreDAO
) {

    /**
     *  Database Functions
     */

    suspend fun addStore(store: StoreEntity) =
        storeDataSource.addStore(store)

    suspend fun getStores() =
        storeDataSource.getStores().map { it.toStoreObject() }

    suspend fun checkDatabase() =
        storeDataSource.checkDatabase()

    suspend fun deleteAllStores() =
        storeDataSource.deleteStores()
}