package com.devmmurray.rocketstores.ui.view.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.devmmurray.rocketstores.R
import com.devmmurray.rocketstores.ui.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setUpNavigation()

        /**
         *  checkForUpdates checks that database is not empty and the
         *  list is not over a month old. If database is empty or list
         *  needs to be refreshed, old data is deleted and a new
         *  network call is made.
         */
        mainActivityViewModel.checkForUpdate()

        // Live Data Observers for Exception Handling
        mainActivityViewModel.ioExceptionAlert.observe(this, ioAlertObserver)
        mainActivityViewModel.unknownException.observe(this, exceptionAlertObserver)
        mainActivityViewModel.error.observe(this, errorObserver)
    }

    // Function to set up Bottom Navigation Bar
    private fun setUpNavigation() {
        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottom_nav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavBar,
            navHostFragment!!.navController
        )
    }



    /**
     *  Observers for exception handling
     *  Put Nothing Below
     */

    private val ioAlertObserver = Observer<Boolean> { ioException ->
        if (ioException) {
            homeError.visibility = View.VISIBLE
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Internet Connection")
                .setMessage("UH OH!\nNo Internet Connection Found.\nWould you like to adjust your wifi settings?")
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
            homeError.visibility = View.VISIBLE
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
        }
    }
}