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
import com.devmmurray.rocketstores.ui.adapter.StoreListRecyclerAdapter
import com.devmmurray.rocketstores.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_tampa.*

class TampaFragment : Fragment() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tampa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStoreList("tampa")
        viewModel.tampaList.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                tampaListRecycler.apply {
                    layoutManager =
                        GridLayoutManager(context, 2)
                    adapter = StoreListRecyclerAdapter(it)
                }
            }
        })
    }
}