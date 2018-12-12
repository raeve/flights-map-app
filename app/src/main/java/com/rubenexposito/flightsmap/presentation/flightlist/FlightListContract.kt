package com.rubenexposito.flightsmap.presentation.flightlist

import android.support.annotation.StringRes

interface FlightListContract {
    interface  View {
        fun showAirports(reset: Boolean)
        fun showFlights(reset: Boolean)
        fun showError(@StringRes resId: Int)
    }

    interface Presenter {
        fun onCreate()
        fun onPause()
    }
}