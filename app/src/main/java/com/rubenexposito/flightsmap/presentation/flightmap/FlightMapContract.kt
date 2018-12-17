package com.rubenexposito.flightsmap.presentation.flightmap

import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import com.rubenexposito.flightsmap.presentation.common.BasePresenter
import com.rubenexposito.flightsmap.presentation.common.BaseView

interface FlightMapContract {
    interface View : BaseView{
        fun drawLines(airports: List<Airport>)
     }

    interface Presenter : BasePresenter{
        fun addSchedule(schedule: Schedule)
    }
}