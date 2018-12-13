package com.rubenexposito.flightsmap.di

import android.app.Activity
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListActivity
import com.rubenexposito.flightsmap.presentation.flightlist.di.FlightListSubComponent
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapActivity
import com.rubenexposito.flightsmap.presentation.flightmap.di.FlightMapSubComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(FlightListActivity::class)
    abstract fun bindFlightListActivityInjectorFactory(builder: FlightListSubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(FlightMapActivity::class)
    abstract fun bindFlightMapActivityInjectorFactory(builder: FlightMapSubComponent.Builder): AndroidInjector.Factory<out Activity>
}