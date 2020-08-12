package com.devmmurray.rocketstores.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmmurray.rocketstores.data.model.domain.StoreObject

@Entity(tableName = "stores")
class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "created")
    val timeStamp: Long?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "latitude")
    val latitude: String?,
    @ColumnInfo(name = "zipcode")
    val zipcode: String?,
    @ColumnInfo(name = "logo")
    val logo: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "longitude")
    val longitude: String?,
    @ColumnInfo(name = "storeId")
    val storeId: String?,
    @ColumnInfo(name = "state")
    val state: String?
) {

    companion object {

        fun toStoreEntity(store: StoreObject) =
            StoreEntity(
                store.uid,
                store.timeStamp,
                store.address,
                store.city,
                store.name,
                store.latitude,
                store.zipcode,
                store.logo,
                store.phone,
                store.longitude,
                store.storeId,
                store.state
            )
    }

        fun toStoreObject() =
            StoreObject(
                uid,
                timeStamp,
                address,
                city,
                name,
                latitude,
                zipcode,
                logo,
                phone,
                longitude,
                storeId,
                state
            )

}






