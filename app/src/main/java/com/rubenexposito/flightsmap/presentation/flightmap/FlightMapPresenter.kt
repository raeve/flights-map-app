package com.rubenexposito.flightsmap.presentation.flightmap

import io.reactivex.Scheduler

class FlightMapPresenter(
    private val view: FlightMapContract.View,
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) : FlightMapContract.Presenter {
}