package com.rubenexposito.flightsmap.domain.mapper

import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.model.Airport

class AirportMapper {

    fun convertReferenceAirportDtoToAirportList(referencesAirportDto: ReferencesAirportDto) : List<Airport> = referencesAirportDto.airportResource.airportsDto.listAirportDto.map {
        Airport(it.airportCode, it.namesDto.listNameDto[0].alias, it.countryCode)
    }
}