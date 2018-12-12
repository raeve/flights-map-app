package com.rubenexposito.flightsmap.presentation.flightlist.di

import android.app.Activity
import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.domain.AirportsInteractor
import com.rubenexposito.flightsmap.domain.AirportsInteractorImpl
import com.rubenexposito.flightsmap.domain.mapper.AirportMapper
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListActivity
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListContract
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
abstract class FlightListModule {

    @Binds
    @PerActivity
    abstract fun provideView(activity: FlightListActivity): FlightListContract.View

    @Binds
    @PerActivity
    abstract fun provideActivity(activity: FlightListActivity): Activity

    @Module
    companion object {

        @Provides
        @PerActivity
        @JvmStatic
        fun provideAirportsInteractor(lufthansaRepository: LufthansaRepository,
                                      sharedRepository: SharedRepository) : AirportsInteractor
                = AirportsInteractorImpl(lufthansaRepository, sharedRepository, AirportMapper())

        @Provides
        @PerActivity
        @JvmStatic
        fun providePresenter(
                view: FlightListContract.View,
                interactor: AirportsInteractor,
                navigator: Navigator, @Named("observeOn") observeOn: Scheduler, @Named("subscribeOn") subscribeOn: Scheduler
        ): FlightListContract.Presenter = FlightListPresenter(
                view, interactor, navigator, observeOn, subscribeOn
        )
    }
}