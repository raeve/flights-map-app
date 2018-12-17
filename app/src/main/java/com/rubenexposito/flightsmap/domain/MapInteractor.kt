package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4

interface MapInteractor {
    fun getAirports(schedule: Schedule): Single<List<Airport>>
}

class MapInteractorImpl(
    private val lufthansaRepository: LufthansaRepository
) : MapInteractor {
    override fun getAirports(schedule: Schedule) =
        when (schedule.flights.size) {
            1 -> directFlight(schedule)
            2 -> oneStop(schedule)
            else -> twoStops(schedule)
        }

    private fun directFlight(schedule: Schedule) = Single.zip(
        createSingle(schedule, schedule.flights[0].departure.airportCode),
        createSingle(schedule, schedule.flights[0].arrival.airportCode),
        BiFunction<List<Airport>, List<Airport>, List<Airport>> { t1, t2 -> listOf(t1[0], t2[0]) })

    private fun oneStop(schedule: Schedule) = Single.zip(
        createSingle(schedule, schedule.flights[0].departure.airportCode),
        createSingle(schedule, schedule.flights[0].arrival.airportCode),
        createSingle(schedule, schedule.flights[1].arrival.airportCode),
        Function3<List<Airport>, List<Airport>, List<Airport>, List<Airport>> { t1, t2, t3 ->
            listOf(
                t1[0],
                t2[0],
                t3[0]
            )
        })


    private fun twoStops(schedule: Schedule) = Single.zip(
        createSingle(schedule, schedule.flights[0].departure.airportCode),
        createSingle(schedule, schedule.flights[0].arrival.airportCode),
        createSingle(schedule, schedule.flights[1].arrival.airportCode),
        createSingle(schedule, schedule.flights[2].arrival.airportCode),
        Function4<List<Airport>, List<Airport>, List<Airport>, List<Airport>, List<Airport>> { t1, t2, t3, t4 ->
            listOf(
                t1[0],
                t2[0],
                t3[0],
                t4[0]
            )
        })

    private fun createSingle(schedule: Schedule, airportCode: String) =
        lufthansaRepository.referencesAirport(airportCode).map {
            convertReferenceAirportDtoToAirportList(it)
        }

    private fun convertReferenceAirportDtoToAirportList(referencesAirportDto: ReferencesAirportDto): List<Airport> =
        referencesAirportDto.airportResource.airportsDto.listAirportDto.map {
            Airport(
                it.airportCode,
                it.namesDto.listNameDto[0].alias,
                it.countryCode,
                it.positionDto.coordinateDto?.latitude,
                it.positionDto.coordinateDto?.longitude
            )
        }

}