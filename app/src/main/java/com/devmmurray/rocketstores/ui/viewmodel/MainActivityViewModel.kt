package com.devmmurray.rocketstores.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.rocketstores.data.database.RoomDatabaseClient
import com.devmmurray.rocketstores.data.model.entity.StoreEntity
import com.devmmurray.rocketstores.data.repo.DatabaseRepo
import com.devmmurray.rocketstores.data.repo.RocketApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Current Time for TimeStamp
private val CURRENT_TIME: Long = System.currentTimeMillis()
private const val TIME_LAPSE: Long = 2_592_000_000

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     *  Live Data Values To Update Main Activity
     */
    private val _storesUpToDate by lazy { MutableLiveData<Boolean>() }
    val storesUpToDate: LiveData<Boolean> get() = _storesUpToDate

    /**
     *  Set Up Database
     */

    private val repository: DatabaseRepo

    init {
        val storeDAO = RoomDatabaseClient.getDbInstance(application).storeDao()
        repository = DatabaseRepo(storeDAO)
    }


    /**
     *  Database Functions
     */

    private fun deleteData() {
        viewModelScope.launch {
            repository.deleteAllStores()
        }
    }

    fun checkForUpdate() {
        updateDB()
    }

    private fun updateDB() {
        viewModelScope.launch {
            val checkDB = repository.checkDatabase()
            if (checkDB.timeStamp != null) {
                val timestamp = checkDB.timeStamp
                if (CURRENT_TIME - timestamp > TIME_LAPSE) {
                    deleteData()
                    getAllStores()
                } else {
                    _storesUpToDate.postValue(true)
                }
            } else {
                getAllStores()
            }
        }
    }

    private fun getAllStores() {
        viewModelScope.launch {
            val result = RocketApiRepo.getRocketStores()
            result.body()?.stores?.forEach {
                val store = StoreEntity(
                    timeStamp = CURRENT_TIME,
                    address = it.address,
                    city = it.city,
                    name = it.name,
                    latitude = it.latitude,
                    zipcode = it.zipcode,
                    logo = it.logo,
                    phone = it.phone,
                    longitude = it.longitude,
                    storeId = it.storeId,
                    state = it.state
                )
                addStore(store)
            }
        }
        _storesUpToDate.postValue(true)
    }


    private fun addStore(store: StoreEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStore(store)
        }
}