package com.rubenexposito.flightsmap.presentation.flightlist

interface FlightListContract {
    interface  View {
        fun showAirports(reset: Boolean)
        fun showFlights(reset: Boolean)
        fun showError()
    }

    interface Presenter {
        fun onCreate()
        fun onPause()
    }
}