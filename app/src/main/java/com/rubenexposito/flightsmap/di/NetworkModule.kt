package com.rubenexposito.flightsmap.di

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.LufthansaRepositoryImpl
import com.rubenexposito.flightsmap.data.network.LufthansaApi
import com.rubenexposito.flightsmap.data.network.RetrofitAdapter
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideLufthansaRepository(): LufthansaRepository =
        LufthansaRepositoryImpl(RetrofitAdapter.retrofit.create(LufthansaApi::class.java))
}