package com.rubenexposito.flightsmap.presentation.flightlist

import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.domain.AirportsInteractor
import com.rubenexposito.flightsmap.domain.OAuthInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy

class FlightListPresenter(
    private val view: FlightListContract.View,
    private val oAuthInteractor: OAuthInteractor,
    private val airportsInteractor: AirportsInteractor,
    private val navigator: Navigator,
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) : FlightListContract.Presenter{

    private var subscription = Disposables.empty()

    private var airportsOffset = 0

    override fun onCreate() {
        requestToken()
    }

    override fun onPause() {
        subscription.dispose()
    }

    private fun requestToken() {
        subscription = oAuthInteractor.getAuthToken()
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
            .subscribeBy({view.showError(R.string.error_token_not_updated)}, {if (it) requestAirports(false) else view.showError(R.string.error_token_not_updated)})
    }

    private fun requestAirports(reset: Boolean) {
        subscription = airportsInteractor.getAirports(PARAM_LIMIT, airportsOffset)
            .observeOn(observeOn)
            .subscribeOn(subscribeOn)
            .subscribeBy({view.showError(R.string.error_airports_not_retrieved)}, {view.showAirports(reset)})
    }

    companion object {
        const val PARAM_LIMIT = 20
    }
}