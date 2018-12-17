package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.common.toHours
import com.rubenexposito.flightsmap.common.toHoursMinutes
import com.rubenexposito.flightsmap.data.dto.OperationsSchedulesDto
import com.rubenexposito.flightsmap.domain.model.Flight
import com.rubenexposito.flightsmap.domain.model.PlaceFlight
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single

interface SchedulesInteractor {
    fun getSchedules(fromCode: String, toCode: String, date: String): Single<List<Schedule>>
}

class SchedulesInteractorImpl(
    private val lufthansaRepository: LufthansaRepository
) : SchedulesInteractor {
    override fun getSchedules(fromCode: String, toCode: String, date: String) = createSingle(fromCode, toCode, date)

    private fun createSingle(fromCode: String, toCode: String, date: String): Single<List<Schedule>> =
        lufthansaRepository.operationsSchedules(fromCode, toCode, date)
            .map {
                convertOperationsScheduleDtoToScheduleList(it)
            }

    private fun convertOperationsScheduleDtoToScheduleList(operationsSchedulesDto: OperationsSchedulesDto): List<Schedule> =
        operationsSchedulesDto.scheduleResource.scheduleList.map {
            Schedule(it.totalJourney.duration.toHoursMinutes(), it.flightList.map { flightDto ->
                Flight(
                    PlaceFlight(
                        flightDto.departure.airportCode,
                        flightDto.departure.scheduledTimeLocal.dateTime.toHours()
                    ),
                    PlaceFlight(flightDto.arrival.airportCode, flightDto.arrival.scheduledTimeLocal.dateTime.toHours()),
                    "${flightDto.marketingCarrier.airlineId}-${flightDto.marketingCarrier.flightNumber}"
                )
            })
        }
}