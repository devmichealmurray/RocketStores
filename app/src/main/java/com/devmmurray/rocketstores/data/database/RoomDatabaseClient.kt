package com.devmmurray.rocketstores.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmmurray.rocketstores.data.model.entity.StoreEntity

const val DATABASE_SCHEMA_VERSION = 7
const val DB_NAME = "stores-db"

@Database(
    version = DATABASE_SCHEMA_VERSION,
    entities = [StoreEntity::class],
    exportSchema = false
)
abstract class RoomDatabaseClient: RoomDatabase() {

    abstract fun storeDao(): StoreDAO

    companion object {
        private var instance: RoomDatabaseClient? = null

        private fun createDatabase(context: Context): RoomDatabaseClient {
            return Room
                .databaseBuilder(context, RoomDatabaseClient::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getDbInstance(context: Context): RoomDatabaseClient =
            (instance ?: createDatabase(context)).also {
                instance = it
            }
    }
}