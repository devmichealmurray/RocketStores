package com.devmmurray.rocketstores.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.ui.view.StoreDetail
import com.squareup.picasso.Picasso


const val STORE_ID = "store_id"

class StoreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindStore(store: StoreObject) {

        view.setOnClickListener {
            Log.d("Click Listener", "******* UID: ${store.uid} ********")
            moveToDetailActivity(view, store.uid)
        }

        val imageHolder: ImageView = view.findViewById(R.id.storeLogo)
        val storeName: TextView = view.findViewById(R.id.storeName)
        val storeAddress: TextView = view.findViewById(R.id.storeAddress)
        val storeCityState: TextView = view.findViewById(R.id.storeCityState)
        val storePhone: TextView = view.findViewById(R.id.storePhone)

        Picasso.get()
            .load(store.logo)
            .error(R.drawable.ic_baseline_broken_image)
            .placeholder(R.drawable.placeholder)
            .fit()
            .into(imageHolder)

        storeName.text = store.name
        storeAddress.text = store.address
        storeCityState.text = "${store.city}, ${store.state} ${store.zipcode}"
        storePhone.text = store.phone
    }

    private fun moveToDetailActivity(view: View, id: Long) {
        val viewContext = view.context
        val intent = Intent(viewContext, StoreDetail::class.java)
        intent.putExtra(STORE_ID, id)
        viewContext.startActivity(intent)
    }
}

class StoreListRecyclerAdapter(private val list: ArrayList<StoreObject>) :
    RecyclerView.Adapter<StoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.store_recycler_item, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bindStore(list[position])
    }
}