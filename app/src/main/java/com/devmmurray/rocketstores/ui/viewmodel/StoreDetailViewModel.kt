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

    // Function to retrieve individual store data using the unique UID passed from
    // StoreViewHolder
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