package com.devmmurray.rocketstores.ui.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmmurray.rocketstores.R
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitButton.setOnClickListener {
            val page: Uri = Uri.parse("https://github.com/devmichealmurray")
            val intent = Intent(Intent.ACTION_VIEW, page)
                startActivity(intent)
        }

        callMeButton.setOnClickListener {
            val callIntentUri = Uri.parse("tel:214-736-9162")
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                data = callIntentUri
            }
            startActivity(callIntent)
        }

        emailMe.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "*/*"
                putExtra(Intent.EXTRA_EMAIL, "michealmurray16@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Rocket Stores App")
            }
            startActivity(intent)
        }
    }

}