package com.rubenexposito.flightsmap.presentation.flightlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.rubenexposito.flightsmap.R
import dagger.android.AndroidInjection

class FlightListActivity : AppCompatActivity(), FlightListContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)
        AndroidInjection.inject(this)
    }

    override fun showAirports(reset: Boolean) {

    }

    override fun showFlights(reset: Boolean) {

    }

    override fun showError() {
        Toast.makeText(this, R.string.error_unexpected, Toast.LENGTH_SHORT).show()
    }
}