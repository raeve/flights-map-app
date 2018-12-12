package com.rubenexposito.flightsmap.di

import android.content.Context
import com.rubenexposito.flightsmap.BaseApplication
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.data.SharedRepositoryImpl
import com.rubenexposito.flightsmap.presentation.flightlist.di.FlightListSubComponent
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Mockable
@Module(
    subcomponents = [FlightListSubComponent::class]
)
class AppModule {

    @Provides
    fun context(application: BaseApplication): Context = application.applicationContext

    @Provides
    fun provideSharedRepository(context: Context) : SharedRepository = SharedRepositoryImpl(context)

    @Provides
    @Named("observeOn")
    fun provideObserveOnScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named("subscribeOn")
    fun provideSubscribeOnScheduler(): Scheduler = Schedulers.io()

}

