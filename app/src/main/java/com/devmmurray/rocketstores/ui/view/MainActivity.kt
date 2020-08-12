package com.devmmurray.rocketstores.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.ui.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel.checkForUpdate()
        mainActivityViewModel.storesUpToDate.observe(this, storesUpToDateObserver)
        mainActivityViewModel.storeList.observe(this, storeListObserver)
    }

    private val storesUpToDateObserver = Observer<Boolean> {
        if (it) {
            mainActivityViewModel.getStores()
        }
    }

    private val storeListObserver = Observer<List<StoreObject>> {

    }
}