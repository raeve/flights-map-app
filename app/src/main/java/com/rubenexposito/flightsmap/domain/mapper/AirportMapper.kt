package com.rubenexposito.flightsmap.domain.mapper

import com.rubenexposito.flightsmap.data.common.toHours
import com.rubenexposito.flightsmap.data.common.toHoursMinutes
import com.rubenexposito.flightsmap.data.dto.OperationsSchedulesDto
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Flight
import com.rubenexposito.flightsmap.domain.model.PlaceFlight
import com.rubenexposito.flightsmap.domain.model.Schedule

class AirportMapper {

    fun convertReferenceAirportDtoToAirportList(referencesAirportDto: ReferencesAirportDto): List<Airport> = referencesAirportDto.airportResource.airportsDto.listAirportDto.map {
        Airport(it.airportCode, it.namesDto.listNameDto[0].alias, it.countryCode)
    }

    fun convertOperationsScheduleToScheduleList(operationsSchedulesDto: OperationsSchedulesDto): List<Schedule> = operationsSchedulesDto.scheduleResource.scheduleList.map {
        Schedule(it.totalJourney.duration.toHoursMinutes(), it.flightList.map { flightDto -> Flight(PlaceFlight(flightDto.departure.airportCode, flightDto.departure.scheduledTimeLocal.dateTime.toHours()), PlaceFlight(flightDto.arrival.airportCode, flightDto.arrival.scheduledTimeLocal.dateTime.toHours()), "${flightDto.marketingCarrier.airlineId}-${flightDto.marketingCarrier.flightNumber}") })
    }
}