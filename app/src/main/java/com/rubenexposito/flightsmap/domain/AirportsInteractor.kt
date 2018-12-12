package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.domain.mapper.AirportMapper
import com.rubenexposito.flightsmap.domain.model.Airport
import io.reactivex.Single

interface AirportsInteractor {
    fun getAirports(limit: Int, offset: Int): Single<List<Airport>>
}

class AirportsInteractorImpl(
    private val lufthansaRepository: LufthansaRepository,
    private val airportMapper: AirportMapper
) : AirportsInteractor {
    override fun getAirports(limit: Int, offset: Int) = lufthansaRepository.referencesAirport(
        limit,
        offset
    ).map { airportMapper.convertReferenceAirportDtoToAirportList(it) }
}