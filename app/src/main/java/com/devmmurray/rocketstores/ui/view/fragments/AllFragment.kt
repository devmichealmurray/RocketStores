package com.devmmurray.rocketstores.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.data.model.domain.StoreObject
import com.devmmurray.rocketstores.ui.adapter.StoreListRecyclerAdapter
import com.devmmurray.rocketstores.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_all.*

class AllFragment : Fragment() {

    private val viewModel: MainActivityViewModel by viewModels()
    private var recyclerAdapter = StoreListRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE
        viewModel.getStoreList("All")

//        viewModel.storesUpToDate.observe(viewLifecycleOwner, upToDateObserver)
        viewModel.storeList.observe(viewLifecycleOwner, storeListObserver)
    }

//    private val upToDateObserver = Observer<Boolean> {
//        if (it) {
//            viewModel.getStoreList("All")
//        }
//    }

    private val storeListObserver = Observer<ArrayList<StoreObject>> {
        it?.let {
            storeListRecycler.apply {
                layoutManager =
                    GridLayoutManager(context, 2)
                adapter = StoreListRecyclerAdapter(it)
            }
            recyclerAdapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
            storeListRecycler.visibility = View.VISIBLE
        }
    }
}