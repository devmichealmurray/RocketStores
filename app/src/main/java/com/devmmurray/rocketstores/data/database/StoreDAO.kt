package com.devmmurray.rocketstores.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.rocketstores.data.model.entity.StoreEntity

@Dao
interface StoreDAO {

    // Add Stores to DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStore(store: StoreEntity)

    // Retrieve Stores From DB
    @Query("SELECT * FROM stores")
    suspend fun getStores(): MutableList<StoreEntity>

    // Check DB Content
    @Query("SELECT * FROM stores LIMIT 1")
    suspend fun checkDatabase(): StoreEntity

    // Delete All Stores From DB
    @Query("DELETE FROM stores")
    suspend fun deleteStores()
}