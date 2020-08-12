package com.devmmurray.rocketstores.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.squareup.picasso.Picasso


class StoreViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bindStore(store: StoreObject) {
        val imageHolder: ImageView = view.findViewById(R.id.storeLogo)
        val storeName: TextView = view.findViewById(R.id.storeName)
        val storeAddress: TextView = view.findViewById(R.id.storeAddress)
        val storeCityState: TextView = view.findViewById(R.id.storeCityState)
        val storePhone: TextView = view.findViewById(R.id.storePhone)

        Picasso.get()
            .load(store.logo)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .fit()
            .into(imageHolder)

        storeName.text = store.name
        storeAddress.text = store.address
        storeCityState.text = "${store.city}, ${store.state} ${store.zipcode}"
        storePhone.text = store.phone
    }
}

class StoreListRecyclerAdapter(private val list: ArrayList<StoreObject>): RecyclerView.Adapter<StoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.store_recycler_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bindStore(list[position])
    }

    fun updateList(newList: List<StoreObject>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}