package com.devmmurray.rocketstores.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
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
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

//        mainActivityViewModel.deleteData()

        progressBar.visibility = View.VISIBLE
        mainActivityViewModel.checkForUpdate()
        mainActivityViewModel.storesUpToDate.observe(this, storesUpToDateObserver)
        mainActivityViewModel.storeList.observe(this, storeListObserver)

        // Exception Handling
        mainActivityViewModel.ioExceptionAlert.observe(this, ioAlertObserver)
        mainActivityViewModel.unknownException.observe(this, exceptionAlertObserver)
        mainActivityViewModel.error.observe(this, errorObserver)

        storeListRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    private val storesUpToDateObserver = Observer<Boolean> {
        if (it) {
            mainActivityViewModel.getStoreList()
        }
    }

    private val storeListObserver = Observer<ArrayList<StoreObject>> {
        it?.let {
            storeListRecycler.adapter = StoreListRecyclerAdapter(it)
            progressBar.visibility = View.GONE
        }
    }


    /**
     *  Observers for exception handling
     */

    private val ioAlertObserver = Observer<Boolean> { ioException ->
        if (ioException) {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Internet Connection")
                .setMessage("UH OH!\nNo Internet Connection Found.\nWould you like to adjust your settings?")
                .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .show()
        }
    }

    private val exceptionAlertObserver = Observer<String> { message ->
        if (message.isNotEmpty()) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.unknown_error))
                .setMessage(message)
                .setPositiveButton(getString(R.string.okay)) { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .show()
        }
    }

    private val errorObserver = Observer<Boolean> {
        if (it) {
            homeError.visibility = View.VISIBLE
            storeListRecycler.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }
}