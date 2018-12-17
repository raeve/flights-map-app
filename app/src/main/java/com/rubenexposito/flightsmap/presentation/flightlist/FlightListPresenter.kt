package com.rubenexposito.flightsmap.presentation.flightlist

import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.common.toDateTime
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
    internal var subscription = Disposables.empty()

    internal var airportCodeFrom = ""
    internal var airportCodeTo = ""
    private var airportsOffset = 0

    private var isLoading = false

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

    override fun requestAirports(from: Boolean) {
        airportsOffset = 0

        view.showLoading()
        subscription = airportsInteractor.getAirports(PARAM_LIMIT, airportsOffset)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy(
                        {
                            view.showEmpty()
                            view.hideLoading()
                        },
                        {
                            airportsOffset += PARAM_LIMIT
                            if(it.isEmpty()) view.showEmpty() else view.showAirports(it, from)
                            view.hideLoading()
                        })
    }

    override fun requestMoreAirports() {
        if(!isLoading) {
            isLoading = true
            subscription = airportsInteractor.getAirports(PARAM_LIMIT, airportsOffset)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy(
                    {
                        isLoading = false
                        view.showError(R.string.error_unexpected)
                    },
                    {
                        isLoading = false
                        airportsOffset += PARAM_LIMIT
                        view.addAirports(it)
                    })
        }
    }

    private fun requestSchedules() {
        view.showLoading()
        view.clearAirports()

        subscription = schedulesInteractor.getSchedules(airportCodeFrom, airportCodeTo, Date().toDateTime())
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribeBy (
                        {
                            view.showEmpty()
                            view.hideLoading()
                        },
                        {
                            if(it.isEmpty()) view.showEmpty() else view.showSchedules(it)
                            view.hideLoading()
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

        when {
            airportCodeTo.isEmpty() -> view.selectAirportTo()
            airportCodeFrom.isEmpty() -> view.selectAirportFrom()
            else -> requestSchedules()
        }
    }

    override fun onScheduleSelected(schedule: Schedule) {
        navigator.showMap(schedule)
    }

    companion object {
        const val PARAM_LIMIT = 20
    }
}