package com.rubenexposito.flightsmap.presentation.flightmap.di

import android.app.Activity
import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.domain.*
import com.rubenexposito.flightsmap.domain.mapper.AirportMapper
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListContract
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListPresenter
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapActivity
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapContract
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
abstract class FlightMapModule {

    @Binds
    @PerActivity
    abstract fun provideView(activity: FlightMapActivity): FlightMapContract.View

    @Binds
    @PerActivity
    abstract fun provideActivity(activity: FlightMapActivity): Activity

    @Module
    companion object {

        @Provides
        @PerActivity
        @JvmStatic
        fun providePresenter(
            view: FlightMapContract.View,
            @Named("observeOn") observeOn: Scheduler, @Named("subscribeOn") subscribeOn: Scheduler
        ): FlightMapContract.Presenter = FlightMapPresenter(
            view, observeOn, subscribeOn
        )
    }
}