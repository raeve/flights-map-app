package com.rubenexposito.flightsmap.presentation.flightmap.di

import android.app.Activity
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.domain.MapInteractor
import com.rubenexposito.flightsmap.domain.MapInteractorImpl
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
        fun provideMapInteractor(repository: LufthansaRepository): MapInteractor = MapInteractorImpl(repository)

        @Provides
        @PerActivity
        @JvmStatic
        fun providePresenter(
            view: FlightMapContract.View,
            mapInteractor: MapInteractor,
            @Named("observeOn") observeOn: Scheduler, @Named("subscribeOn") subscribeOn: Scheduler
        ): FlightMapContract.Presenter = FlightMapPresenter(
            view, mapInteractor, observeOn, subscribeOn
        )
    }
}