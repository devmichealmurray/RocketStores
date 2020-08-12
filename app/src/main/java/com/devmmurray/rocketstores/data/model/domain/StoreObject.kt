package com.devmmurray.rocketstores.data.model.domain

class StoreObject(
    var uid: Long = 0L,
    val timeStamp: Long?,
    val address: String?,
    val city: String?,
    val name: String?,
    val latitude: String?,
    val zipcode: String?,
    val logo: String?,
    val phone: String?,
    val longitude: String?,
    val storeId: String?,
    val state: String?
)