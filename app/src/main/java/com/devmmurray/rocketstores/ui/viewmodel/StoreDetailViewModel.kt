package com.devmmurray.rocketstores.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import kotlinx.coroutines.launch

class StoreDetailViewModel(application: Application): MainActivityViewModel(application) {

    private val _store by lazy { MutableLiveData<StoreObject>() }
    val store: LiveData<StoreObject> get() = _store


    fun mapIt(lat: String, long: String) {
        locateOnMap(lat, long)
    }

    private fun locateOnMap(lat: String, long: String) {
        val gmmIntentUri = Uri.parse("geo:$lat, $long")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
//        context?.startActivity(mapIntent)
    }

    fun getStore(id: Long) {
        getStoreById(id)
    }

    private fun getStoreById(id: Long) {
        viewModelScope.launch {
            val store = repository.getStoreById(id)
            _store.postValue(store)
        }
    }

}