package com.rubenexposito.flightsmap.di

import android.app.Activity
import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.NavigatorImpl
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: Activity): Navigator = NavigatorImpl(activity)
    }
}