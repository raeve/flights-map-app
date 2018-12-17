package com.rubenexposito.flightsmap.di

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.LufthansaRepositoryImpl
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.data.network.LufthansaApi
import com.rubenexposito.flightsmap.data.network.RetrofitAdapter
import com.rubenexposito.flightsmap.domain.OAuthInteractor
import com.rubenexposito.flightsmap.domain.OAuthInteractorImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideLufthansaRepository(sharedRepository: SharedRepository): LufthansaRepository =
        LufthansaRepositoryImpl(RetrofitAdapter(sharedRepository).retrofit.create(LufthansaApi::class.java))

    @Provides
    fun provideOAuthInteractor(lufthansaRepository: LufthansaRepository, sharedRepository: SharedRepository) : OAuthInteractor
            = OAuthInteractorImpl(lufthansaRepository, sharedRepository)
}