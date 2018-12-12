package com.rubenexposito.flightsmap.data

import com.rubenexposito.flightsmap.data.dto.OAuthTokenDto
import com.rubenexposito.flightsmap.data.dto.OperationsSchedulesDto
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.data.network.LufthansaApi
import io.reactivex.Single

interface LufthansaRepository {
    fun oauthToken() : Single<OAuthTokenDto>
    fun referencesAirport(limit: Int, offset: Int) : Single<ReferencesAirportDto>
    fun operationsSchedules(fromCode: String, toCode: String, date: String) : Single<OperationsSchedulesDto>
}

class LufthansaRepositoryImpl(private val lufthansaApi: LufthansaApi) : LufthansaRepository {
    override fun oauthToken(): Single<OAuthTokenDto> = lufthansaApi.oauthToken()
    override fun referencesAirport(limit: Int, offset: Int): Single<ReferencesAirportDto> = lufthansaApi.referencesAirports(limit, offset)
    override fun operationsSchedules(fromCode: String, toCode: String, date: String) = lufthansaApi.operationsSchedules(fromCode, toCode, date)
}