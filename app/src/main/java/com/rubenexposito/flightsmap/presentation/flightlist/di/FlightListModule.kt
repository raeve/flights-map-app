package com.rubenexposito.flightsmap.presentation.flightlist.di

import android.app.Activity
import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.domain.*
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
        fun provideAirportsInteractor(lufthansaRepository: LufthansaRepository) : AirportsInteractor
                = AirportsInteractorImpl(lufthansaRepository, AirportMapper())

        @Provides
        @PerActivity
        @JvmStatic
        fun provideSchedulesInteractor(lufthansaRepository: LufthansaRepository) : SchedulesInteractor
                = SchedulesInteractorImpl(lufthansaRepository, AirportMapper())

        @Provides
        @PerActivity
        @JvmStatic
        fun providePresenter(
            view: FlightListContract.View,
            oAuthInteractor: OAuthInteractor,
            airportsInteractor: AirportsInteractor,
            schedulesInteractor: SchedulesInteractor,
            navigator: Navigator, @Named("observeOn") observeOn: Scheduler, @Named("subscribeOn") subscribeOn: Scheduler
        ): FlightListContract.Presenter = FlightListPresenter(
                view, oAuthInteractor, airportsInteractor, schedulesInteractor, navigator, observeOn, subscribeOn
        )
    }
}