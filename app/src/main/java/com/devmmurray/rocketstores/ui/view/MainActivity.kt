package com.devmmurray.rocketstores.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.ui.adapter.StoreListRecyclerAdapter
import com.devmmurray.rocketstores.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel.checkForUpdate()
        mainActivityViewModel.storesUpToDate.observe(this, storesUpToDateObserver)
        mainActivityViewModel.storeList.observe(this, storeListObserver)

        storeListRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }



    private val storesUpToDateObserver = Observer<Boolean> {
        if (it) {
            mainActivityViewModel.getStores()
        }
    }

    private val storeListObserver = Observer<ArrayList<StoreObject>> {
        it?.let {
            storeListRecycler.adapter = StoreListRecyclerAdapter(it)
        }
    }
}