package com.rubenexposito.flightsmap.presentation.flightlist

import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.domain.AirportsInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy

class FlightListPresenter(
    private val view: FlightListContract.View,
    private val interactor: AirportsInteractor,
    private val navigator: Navigator,
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) : FlightListContract.Presenter{

    private var subscription = Disposables.empty()

    private var airportsOffset = 0

    override fun onCreate() {
        requestAirports(true)
    }

    override fun onPause() {
        subscription.dispose()
    }

    private fun requestAirports(reset: Boolean) {
        interactor.getAirports(PARAM_LIMIT, airportsOffset)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
            .subscribeBy({view.showError()}, {view.showAirports(reset)})
    }

    companion object {
        const val PARAM_LIMIT = 20
    }
}