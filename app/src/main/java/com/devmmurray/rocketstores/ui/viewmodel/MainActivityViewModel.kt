package com.devmmurray.rocketstores.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.rocketstores.data.database.RoomDatabaseClient
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.data.model.entity.StoreEntity
import com.devmmurray.rocketstores.data.repo.DatabaseRepo
import com.devmmurray.rocketstores.data.repo.RocketApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

// Current Time for TimeStamp
private val CURRENT_TIME: Long = System.currentTimeMillis()
private const val TIME_LAPSE: Long = 2_592_000_000

open class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     *  Live Data Values To Update Main Activity
     */
    private val _storesUpToDate by lazy { MutableLiveData<Boolean>() }
    val storesUpToDate: LiveData<Boolean> get() = _storesUpToDate

    private val _storeList by lazy { MutableLiveData<ArrayList<StoreObject>>() }
    val storeList: LiveData<ArrayList<StoreObject>> get() = _storeList

    private val _ioExceptionAlert by lazy { MutableLiveData<Boolean>() }
    val ioExceptionAlert: LiveData<Boolean> get() = _ioExceptionAlert

    private val _unknownException by lazy { MutableLiveData<String>() }
    val unknownException: LiveData<String> get() = _unknownException

    private val _error by lazy { MutableLiveData<Boolean>() }
    val error: LiveData<Boolean> get() = _error

    /**
     *  Set Up Database
     */

    val repository: DatabaseRepo

    init {
        val storeDAO = RoomDatabaseClient.getDbInstance(application).storeDao()
        repository = DatabaseRepo(storeDAO)
    }


    /**
     *  Database Functions
     */

    private fun addStore(store: StoreEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStore(store)
        }

    fun deleteData() {
        viewModelScope.launch {
            repository.deleteAllStores()
        }
    }

    fun checkForUpdate() {
        updateDB()
    }

    private fun updateDB() {
        viewModelScope.launch {
            val checkDB = repository.getStores()

            if (checkDB.isNotEmpty()) {
                if ((checkDB.size < 20) || (CURRENT_TIME - checkDB[0].timeStamp!! > TIME_LAPSE)) {
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

            try {
                val result = RocketApiRepo.getRocketStores()

                if (result.isSuccessful) {
                    result.body()?.stores?.forEach {
                        val store = StoreEntity(
                            timeStamp = CURRENT_TIME,
                            address = it.address,
                            city = it.city,
                            name = it.name,
                            latitude = it.latitude,
                            zipcode = it.zipcode,
                            logo = it.storeLogoAddress,
                            phone = it.phone,
                            longitude = it.longitude,
                            storeId = it.storeId,
                            state = it.state
                        )
                        addStore(store)
                    }
                } else {
                    _error.postValue(true)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _ioExceptionAlert.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _unknownException.value = e.message
            }
            _storesUpToDate.postValue(true)
        }

    }

    fun getStoreList() {
        getStoreListFromDB()
    }

    private fun getStoreListFromDB() {
        val tempList = ArrayList<StoreObject>()
        viewModelScope.launch {
            tempList.addAll(repository.getStores())
            _storeList.postValue(tempList)
        }
    }

}