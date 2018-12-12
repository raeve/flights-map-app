package com.rubenexposito.flightsmap.presentation.flightlist

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.rubenexposito.flightsmap.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class FlightListActivity : AppCompatActivity(), FlightListContract.View {

    @Inject
    lateinit var presenter: FlightListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)
        AndroidInjection.inject(this)
        presenter.onCreate()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun showAirports(reset: Boolean) {
        Toast.makeText(this, "We got airports", Toast.LENGTH_SHORT).show()
    }

    override fun showFlights(reset: Boolean) {

    }

    override fun showError(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}