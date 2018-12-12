package com.rubenexposito.flightsmap.presentation.flightlist.di

import com.rubenexposito.flightsmap.di.ActivityModule
import com.rubenexposito.flightsmap.di.PerActivity
import com.rubenexposito.flightsmap.presentation.flightlist.FlightListActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [ActivityModule::class, FlightListModule::class])
interface FlightListSubComponent : AndroidInjector<FlightListActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FlightListActivity>()
}