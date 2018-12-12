package com.rubenexposito.flightsmap.presentation.flightlist

import android.support.annotation.StringRes
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule

interface FlightListContract {
    interface  View {
        fun onPrepared()
        fun showAirports(airportList: List<Airport>, from: Boolean)
        fun addAirports(airportList: List<Airport>)
        fun showError(@StringRes resId: Int)
        fun updateAirportTo(text: String)
        fun updateAirportFrom(text: String)
        fun showSchedules(scheduleList: List<Schedule>)
    }

    interface Presenter : ItemListener {
        fun onCreate()
        fun onPause()
        fun requestAirports(from: Boolean, reset: Boolean)
        fun requestMoreAirports()
        fun requestSchedules()
    }
}