package com.rubenexposito.flightsmap.presentation.flightmap

import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.domain.MapInteractor
import com.rubenexposito.flightsmap.domain.model.Schedule
import com.rubenexposito.flightsmap.presentation.common.RxPresenter
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

class FlightMapPresenter(
    private val view: FlightMapContract.View,
    private val mapInteractor: MapInteractor,
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) : RxPresenter(), FlightMapContract.Presenter {

    override fun addSchedule(schedule: Schedule) {
        view.showLoading()
        subscription = mapInteractor.getAirports(schedule)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
            .subscribeBy(
                {
                    view.showError(R.string.error_airports_not_retrieved)
                    view.hideLoading()
                }, {
                    view.drawLines(it)
                    view.hideLoading()
                }
            )
    }
}