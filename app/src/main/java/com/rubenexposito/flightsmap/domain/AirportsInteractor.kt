package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.model.Airport
import io.reactivex.Single

interface AirportsInteractor {
    fun getAirports(limit: Int, offset: Int): Single<List<Airport>>
}

class AirportsInteractorImpl(
    private val lufthansaRepository: LufthansaRepository
) : AirportsInteractor {
    override fun getAirports(limit: Int, offset: Int) = createSingle(limit, offset)

    private fun createSingle(limit: Int, offset: Int): Single<List<Airport>> {
        return lufthansaRepository.referencesAirport(limit, offset)
            .map { convertReferenceAirportDtoToAirportList(it) }
    }

    private fun convertReferenceAirportDtoToAirportList(referencesAirportDto: ReferencesAirportDto): List<Airport> {
        return referencesAirportDto.airportResource.airportsDto.listAirportDto.map {
            Airport(
                it.airportCode,
                it.namesDto.listNameDto[0].alias,
                it.countryCode,
                it.positionDto.coordinateDto?.latitude,
                it.positionDto.coordinateDto?.longitude
            )
        }
    }
}