package com.devmmurray.rocketstores.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.rocketstores.data.model.entity.StoreEntity
import kotlinx.coroutines.launch

class StoreDetailViewModel(application: Application): MainActivityViewModel(application) {

    private val _store by lazy { MutableLiveData<StoreEntity>() }
    val store: LiveData<StoreEntity> get() = _store

//
//    fun mapIt(lat: String?, long: String?) {
//        locateOnMap(lat, long)
//    }
//
//    private fun locateOnMap(lat: String?, long: String?) {
//        val gmmIntentUri = Uri.parse("geo:$lat, $long")
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        getApplication().startActivity(mapIntent)
//    }

    fun getStore(id: Long?) {
        getStoreById(id)
    }

    private fun getStoreById(id: Long?) {
        viewModelScope.launch {
            val store = id?.let { repository.getStoreById(it) }
            _store.postValue(store)
        }
    }

}