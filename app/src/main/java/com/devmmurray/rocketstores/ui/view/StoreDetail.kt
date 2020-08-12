package com.devmmurray.rocketstores.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.entity.StoreEntity
import com.devmmurray.rocketstores.ui.adapter.STORE_ID
import com.devmmurray.rocketstores.ui.viewmodel.StoreDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_store_detail.*


class StoreDetail : AppCompatActivity() {

    private val storeDetailViewModel: StoreDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        val store = intent.extras?.getLong(STORE_ID)
        storeDetailViewModel.getStore(store)
        storeDetailViewModel.store.observe(this, storeObserver)
    }

    private val storeObserver = Observer<StoreEntity> {
        Picasso.get()
            .load(it.logo)
            .error(R.drawable.ic_baseline_broken_image)
            .placeholder(R.drawable.placeholder)
            .fit()
            .into(detailLogo)

        detailName.text = it.name
        storeNumber.text = it.storeId
        streetAddress.text = it.address
        cityStateZip.text = "${it.city}, ${it.state} ${it.zipcode}"
        detailPhone.text = it.phone

        val lat = it.latitude
        val long = it.longitude
        mapButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:$lat, $long")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val number = it.phone
        callButton.setOnClickListener {
            val callIntentUri = Uri.parse("tel:$number")
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = callIntentUri
            }
            startActivity(callIntent)
        }
    }


}