package com.rubenexposito.flightsmap.presentation.flightlist

import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.data.common.toDateTime
import com.rubenexposito.flightsmap.domain.AirportsInteractor
import com.rubenexposito.flightsmap.domain.OAuthInteractor
import com.rubenexposito.flightsmap.domain.SchedulesInteractor
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

class FlightListPresenter(
        private val view: FlightListContract.View,
        private val oAuthInteractor: OAuthInteractor,
        private val airportsInteractor: AirportsInteractor,
        private val schedulesInteractor: SchedulesInteractor,
        private val navigator: Navigator,
        private val observeOn: Scheduler,
        private val subscribeOn: Scheduler
) : FlightListContract.Presenter {
    private var subscription = Disposables.empty()

    private var airportCodeFrom = ""
    private var airportCodeTo = ""
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
                .subscribeBy(
                        {
                            view.showError(R.string.error_token_not_updated)
                        },
                        {
                            if (!it) view.showError(R.string.error_token_not_updated) else view.onPrepared()
                        })
    }

    override fun requestAirports(from: Boolean, reset: Boolean) {
        if(reset) airportsOffset = 0

        subscription = airportsInteractor.getAirports(PARAM_LIMIT, airportsOffset)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy(
                        { view.showError(R.string.error_airports_not_retrieved) },
                        {
                            airportsOffset += PARAM_LIMIT
                            view.showAirports(it, from)
                        })
    }

    override fun requestMoreAirports() {
        subscription = airportsInteractor.getAirports(PARAM_LIMIT, airportsOffset)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy(
                        {
                            view.showError(R.string.error_airports_not_retrieved)
                        },
                        {
                            airportsOffset += PARAM_LIMIT
                            view.addAirports(it)
                        })
    }

    override fun requestSchedules() {
        subscription = schedulesInteractor.getSchedules(airportCodeFrom, airportCodeTo, Date().toDateTime())
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy (
                        {
                            view.showError(R.string.error_schedules_not_found)
                        },
                        {
                            view.showSchedules(it)
                        })
    }

    override fun onAirportSelected(airport: Airport, from: Boolean) {
        val text = "${airport.airportCode} - ${airport.city}(${airport.countryCode})"
        if (from) {
            airportCodeFrom = airport.airportCode
            view.updateAirportFrom(text)
        } else {
            airportCodeTo = airport.airportCode
            view.updateAirportTo(text)
        }

        if(airportCodeFrom.isNotEmpty() && airportCodeTo.isNotEmpty()) {
            requestSchedules()
        }
    }

    override fun onScheduleSelected(schedule: Schedule) {
        navigator.showMap(schedule)
    }

    companion object {
        const val PARAM_LIMIT = 20
    }
}