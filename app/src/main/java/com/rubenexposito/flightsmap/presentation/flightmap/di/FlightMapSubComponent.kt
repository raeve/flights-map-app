package com.rubenexposito.flightsmap.presentation.flightmap.di

import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [FlightMapModule::class])
interface FlightMapSubComponent : AndroidInjector<FlightMapActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FlightMapActivity>()
}