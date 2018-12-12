package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.domain.mapper.AirportMapper
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single

interface SchedulesInteractor {
    fun getSchedules(fromCode: String, toCode: String, date: String): Single<List<Schedule>>
}

class SchedulesInteractorImpl(
    private val lufthansaRepository: LufthansaRepository,
    private val airportMapper: AirportMapper
) : SchedulesInteractor {
    override fun getSchedules(fromCode: String, toCode: String, date: String) = lufthansaRepository.operationsSchedules(fromCode, toCode, date)
            .map { airportMapper.convertOperationsScheduleToScheduleList(it) }
}