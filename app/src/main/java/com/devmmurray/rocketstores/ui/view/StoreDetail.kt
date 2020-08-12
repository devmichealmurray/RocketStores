package com.devmmurray.rocketstores.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.ui.adapter.STORE_ID
import com.devmmurray.rocketstores.ui.viewmodel.StoreDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_store_detail.*


class StoreDetail : AppCompatActivity() {

    private val storeDetailViewModel: StoreDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        val store = intent.extras?.getInt(STORE_ID)?.toLong()
        storeDetailViewModel.store.observe(this, storeObserver)
    }

    private val storeObserver = Observer<StoreObject> {
        Picasso.get()
            .load(it.logo)
            .error(R.drawable.ic_baseline_broken_image)
            .placeholder(R.drawable.placeholder)
            .fit()
            .into(detailLogo)


    }
}