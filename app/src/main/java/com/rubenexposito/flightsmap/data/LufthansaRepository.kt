package com.rubenexposito.flightsmap.data

import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.data.network.LufthansaApi
import io.reactivex.Single

interface LufthansaRepository {
    fun referencesAirport(limit: Int, offset: Int) : Single<ReferencesAirportDto>
}

class LufthansaRepositoryImpl(private val lufthansaApi: LufthansaApi) : LufthansaRepository {
    override fun referencesAirport(limit: Int, offset: Int): Single<ReferencesAirportDto> = lufthansaApi.referencesAirports(limit, offset)
}