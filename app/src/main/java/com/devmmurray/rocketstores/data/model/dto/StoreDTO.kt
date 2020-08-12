package com.devmmurray.rocketstores.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreDTO(
    @Json(name = "stores")
    val stores: List<StoreData>?
)

@JsonClass(generateAdapter = true)
data class StoreData(
    @Json(name = "address")
    val address: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "latitude")
    val latitude: String?,
    @Json(name = "zipcode")
    val zipcode: String?,
    @Json(name = "storeLogoURL")
    val logo: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "longitude")
    val longitude: String?,
    @Json(name = "storeID")
    val storeId: String?,
    @Json(name = "state")
    val state: String?
) {
    val storeLogoAddress: String? by lazy { buildURL() }
    private fun buildURL(): String? {
        val subUrl = logo?.substring(5)
        return "https:$subUrl".trim()
    }
}